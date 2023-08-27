package com.jeff_media.papi_replace_expansion;

import org.jetbrains.annotations.NotNull;

/**
 * Applies a replacement to a string
 */
public interface Replacer {

    /**
     * Replaces all occurrences of the search string with the replace string in a text
     * @param args Arguments
     * @return Replaced string
     */
    static @NotNull String replace(@NotNull ReplaceArguments args) {
        return args.getText().replace(args.getSearch(), args.getReplace());
    }
}
