package com.jeff_media.papistringreplace;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Parses a string into a {@link ReplaceArguments} object.
 */
public interface Parser {

    char BACKTICK = '`';
    char ESCAPE = '\\';
    char UNDERSCORE = '_';
    String TWO_ESCAPES = ESCAPE + "" + ESCAPE;


    /**
     * Parses a string into a {@link ReplaceArguments} object.
     * @param input the string to parse
     * @return the parsed arguments, or null if the input was invalid
     */
    @Nullable ReplaceArguments parse(@NotNull String input);

}
