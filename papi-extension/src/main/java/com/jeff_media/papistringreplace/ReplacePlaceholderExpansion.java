package com.jeff_media.papistringreplace;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * PlaceholderAPI expansion for to replace strings
 */
public class ReplacePlaceholderExpansion extends PlaceholderExpansion {

    private static final String version;

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

    private final Parser replacer = new NaiveReplacer();

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
        return parseAndReplace(params);
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        return parseAndReplace(params);
    }

    private String parseAndReplace(String input) {
        return replacer.parseAndReplace(input);
    }

}
