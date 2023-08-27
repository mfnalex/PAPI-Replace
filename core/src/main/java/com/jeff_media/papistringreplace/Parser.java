package com.jeff_media.papistringreplace;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parses a placeholder String into a {@link ReplaceArguments} object.
 *
 * The syntax is as follows:
 * <pre>&lt;search&gt;_&lt;replace&gt;_&lt;text&gt;</pre>
 *
 * If the search or replace string contains underscores, the whole search or replace string must be enclosed in backticks.
 *
 * If the search or replace string contains backticks, it must be escaped with a backslash.
 */
public interface Parser {

    char BACKTICK = '`';
    char ESCAPE = '\\';
    char UNDERSCORE = '_';
    String TWO_ESCAPES = ESCAPE + "" + ESCAPE;


    /**
     * Parses a string into a {@link ReplaceArguments} object.
     *
     * @param input the string to parse
     * @return the parsed arguments, or null if the input was invalid
     */
    @Nullable ReplaceArguments parse(final @NotNull String input);

    /**
     * Parses a string into a {@link ReplaceArguments} object and replaces the search string with the replace string in the text.
     * @param input the string to parse
     * @return the replaced string, or null if the input was invalid
     */
    default @Nullable String parseAndReplace(final @NotNull String input) {
        ReplaceArguments args = parse(input);
        if (args == null) {
            return null;
        }
        return Replacer.replace(args);
    }

}
