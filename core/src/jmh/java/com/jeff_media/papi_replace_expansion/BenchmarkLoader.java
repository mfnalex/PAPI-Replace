package com.jeff_media.papi_replace_expansion;

import com.jeff_media.papi_replace_expansion.parsing.NaiveParser;
import com.jeff_media.papi_replace_expansion.parsing.Parser;
import com.jeff_media.papi_replace_expansion.parsing.RegexParser;
import lombok.Data;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@State(Scope.Benchmark)
public class BenchmarkLoader {

    private RegexParser regex;
    private NaiveParser naive;
    private List<SinglePlaceholderTestData> shortTestData;
    private List<SinglePlaceholderTestData> longTestData;

    @Setup //(Level.Trial)
    public void init() {
        regex = new RegexParser();
        naive = new NaiveParser();
        shortTestData = new ArrayList<>();
        longTestData = new ArrayList<>();
        loadTestData();
        //System.out.println("Loaded " + (shortTestData.size()+longTestData.size()) + " test cases");
    }

    public void loadTestData() {
        try (InputStream stream = BenchmarkLoader.class.getResourceAsStream("/benchmark-placeholders.txt");
             InputStreamReader reader = new InputStreamReader(stream);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split("\\|\\|");
                if (split.length != 2) {
                    throw new IllegalArgumentException("Invalid line: " + line);
                }
                SinglePlaceholderTestData data = new SinglePlaceholderTestData(split[0], split[1]);
                String regexResult = Objects.requireNonNull(regex.parseAndReplace(data.getPlaceholder()));
                String naiveResult = Objects.requireNonNull(naive.parseAndReplace(data.getPlaceholder()));
                if (!regexResult.equals(naiveResult)) {
                    throw new IllegalStateException("Regex and naive parser returned different results for " + data.getPlaceholder() + ": " + regexResult + " vs " + naiveResult);
                }
                if(!regexResult.equals(data.getExpectedResult())) {
                    throw new IllegalStateException("Parsers returned wrong result for " + data.getPlaceholder() + ":" +
                            " " + regexResult + " vs " + data.getExpectedResult());
                }

                //System.out.println("Loaded test case: " + data.getPlaceholder() + " -> " + data.getExpectedResult());

                if(data.expectedResult.length() > 100) {
                    longTestData.add(data);
                } else {
                    shortTestData.add(data);
                }
            }
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private void runReplacements(Parser parser, List<SinglePlaceholderTestData> list, Blackhole hole) {
        for(int i = 0; i < 5; i++) {
            for (SinglePlaceholderTestData data : list) {
                String result = parser.parseAndReplace(data.getPlaceholder());
                Objects.requireNonNull(result);
                if (!result.equals(data.getExpectedResult())) {
                    throw new IllegalStateException("Expected " + data.getExpectedResult() + " but got " + result);
                }
                hole.consume(result);
            }
        }
    }

    @Benchmark
    public void benchmarkRegexShort(Blackhole hole) {
        runReplacements(regex, shortTestData, hole);
    }

    @Benchmark
    public void benchmarkRegexLong(Blackhole hole) {
        runReplacements(regex, longTestData, hole);
    }

    @Benchmark
    public void benchmarkNaiveShort(Blackhole hole) {
        runReplacements(naive, shortTestData, hole);
    }

    @Benchmark
    public void benchmarkNaiveLong(Blackhole hole) {
        runReplacements(naive, longTestData, hole);
    }

    @Data
    public static class SinglePlaceholderTestData {
        private final String expectedResult;
        private final String placeholder;
    }

}
