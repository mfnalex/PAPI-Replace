package com.jeff_media.papi_replace_expansion;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;

//@BenchmarkMode(Mode.AverageTime)
//@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class BenchReplacer {

    private final RegexParser regexParser = new RegexParser();
    private final NaiveParser naiveParser = new NaiveParser();

    private final List<String> strings = new ArrayList<>();

    {
        strings.add("cat_dog_My cat is a fancy cat!");
        strings.add("cat_dog_My dog is a fancy dog!");
        strings.add("`Egypt`_`Turkey`_Last summer I was in Egypt! It was hot!");
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void benchmarkRegexReplacer(Blackhole blackhole) {
        for(String string : strings) {
            blackhole.consume(regexParser.parseAndReplace(string));
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void benchmarkNaiveReplacer(Blackhole blackhole) {
        for(String string : strings) {
            blackhole.consume(naiveParser.parseAndReplace(string));
        }
    }



}
