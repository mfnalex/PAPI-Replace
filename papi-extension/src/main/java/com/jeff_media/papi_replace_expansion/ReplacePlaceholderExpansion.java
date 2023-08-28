package com.jeff_media.papi_replace_expansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

/**
 * PlaceholderAPI expansion for to replace strings
 */
public class ReplacePlaceholderExpansion extends PlaceholderExpansion {

    //private static final Pattern INNER_PLACEHOLDER = Pattern.compile("^.*(?<inner>\\{[^}]*}).*$");
   // private static final Pattern INNER_PLACEHOLDER = Pattern.compile("\\{[^{]*?}");
    private static final int MAX_NESTED_PLACEHOLDERS = 20;
    private static final String version;
    private static final List<String> PLACEHOLDERS = Collections.unmodifiableList(Arrays.asList(
            "%replace_<search>_<replace>_<text>%",
            "%replace_`<search>`_`<replace>`_<text>%"
    ));

    static {
        String tmpVersion = "<unknown>";
        try (InputStream stream = ReplacePlaceholderExpansion.class.getResourceAsStream("/papi-replace-extension.version");
             InputStreamReader reader = new InputStreamReader(stream);
             BufferedReader bufferedReader = new BufferedReader(reader);
        ) {
            tmpVersion = bufferedReader.readLine();
        } catch (IOException ignored) {

        }

        version = tmpVersion;
    }

    private final Parser replacer = new NaiveParser();

    @Override
    public @NotNull String getIdentifier() {
        return "replace";
    }

    @Override
    public @NotNull String getAuthor() {
        return "mfnalex";
    }

    @Override
    public @NotNull String getVersion() {
        return version;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return parseAndReplace(player, params);
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return parseAndReplace(player, params);
    }

    private String parseAndReplace(OfflinePlayer player, final String input) {
        return replacer.parseAndReplace(replaceInnerPlaceholders(player, input));
    }

    private static String replaceInnerPlaceholders(OfflinePlayer player, String input) {
        int nestedPlaceholders = 0;
        while(PlaceholderAPI.containsBracketPlaceholders(input)) {
            //System.out.println("Found inner placeholder, replacing...");
            //System.out.println("Input: " + input);
            String output = PlaceholderAPI.setBracketPlaceholders(player, input);
            //System.out.println("Output: " + output);
            if(output.equals(input)) {
                //System.out.println("Although there is an inner place, it hasn't changed, so we're breaking out of the loop");
                break;
            }
            input = output;
            nestedPlaceholders++;
            if(nestedPlaceholders >= MAX_NESTED_PLACEHOLDERS) {
                //System.out.println("Reached max nested placeholders, breaking out of the loop");
                break;
            }
        }
        //System.out.println("No more placeholders found, or broke out because see above ^");
        return input;
    }

    @Override
    public @NotNull List<String> getPlaceholders() {
        return PLACEHOLDERS;
    }
}
