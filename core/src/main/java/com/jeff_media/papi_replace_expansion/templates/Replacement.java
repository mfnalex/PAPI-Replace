package com.jeff_media.papi_replace_expansion.templates;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a single replacement
 */
public class Replacement {
    /**
     * The string to search for
     */
    @NotNull
    private final String search;
    /**
     * The string to replace it with
     */
    @NotNull
    private final String replace;

    /**
     * Creates a new replacement
     * @param search The string to search for
     * @param replace The string to replace it with
     */
    private Replacement(@NotNull String search, @NotNull String replace) {
        this.search = search;
        this.replace = replace;
    }

    /**
     * Creates a new replacement from a map
     * @param map The map, containing the keys "search" and "replace"
     * @return The replacement
     */
    public static Replacement of(Map<?,?> map) {
        String search = String.valueOf(map.getOrDefault("search", null));
        String replace = String.valueOf(map.getOrDefault("replace", null));
        Objects.requireNonNull(search, "search cannot be null");
        Objects.requireNonNull(replace, "replace cannot be null");
        return new Replacement(search, replace);
    }

    /**
     * Applies this replacement to a string
     * @param text The string to apply the replacement to
     * @return The replaced string
     */
    @Contract(pure = false)
    @NotNull
    public String apply(@NotNull String text) {
        return text.replace(search, replace);
    }

}
