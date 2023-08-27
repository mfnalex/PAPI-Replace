package com.jeff_media.papi_replace_expansion;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A naive implementation of {@link Parser}. It parses the input String by iterating over all characters, returning null
 * early if it's not a valid input.
 */
public class NaiveParser implements Parser {

    @Override
    public @Nullable ReplaceArguments parse(@NotNull String input) {

        StringBuilder builder = new StringBuilder();

        boolean inBackticks = false;
        boolean lastWasEscape = false;
        boolean isBeginOfPart = true;

        String search = null;
        String replace = null;

        for (final char current : input.toCharArray()) {

            //System.out.println("Current char: " + current);

            // If search and replace are already set, we're parsing the <text> section which does not require any special handling
            if (search != null && replace != null) {
                //System.out.println("    This is part of <text>");
                builder.append(current);
                continue;
            }

            // Check for backticks start
            if (isBeginOfPart) {
                if (current == Parser.BACKTICK) {
                    inBackticks = true;
                    continue;
                }
                isBeginOfPart = false;
            }

            // If the last char was an escape, check if the current char is a backtick or an escape
            if (lastWasEscape) {

                // Two escapes in a row are actually two escapes in a row unless when in backticks
                if (current == Parser.ESCAPE) {
                    lastWasEscape = false;
                    if(!inBackticks) {
                        builder.append(Parser.ESCAPE);
                    }
                    builder.append(Parser.ESCAPE);
                    continue;
                }
                // Escape + Backtick = Backtick
                else if (current == Parser.BACKTICK) {
                    lastWasEscape = false;
                    builder.append(Parser.BACKTICK);
                    continue;
                }
                // Escape + Underscore
                else if (current == Parser.UNDERSCORE) {
                    // If we're not in backticks, the underscore means "end of section"
                    if(!inBackticks) {
                        builder.append(Parser.ESCAPE);
                        lastWasEscape = false;
                        // Do NOT continue!
                    }
                } else {
                    lastWasEscape = false;
                    builder.append(Parser.ESCAPE);
                    builder.append(current);
                    continue;
                }
            }

            if (current == Parser.ESCAPE) {
                lastWasEscape = true;
                continue;
            }

            if (current == Parser.BACKTICK) {
                if (inBackticks) {
                    //i++; // Skip the next char
                    inBackticks = false;
                    continue;
                    //ended = true;
                } else {
                    //System.out.println("Return null");
                    return null;
                }
            }
            if (current == Parser.UNDERSCORE) {
                if (!inBackticks) {
                    if (search == null) {
                        search = builder.toString();
                        builder = new StringBuilder();
                        isBeginOfPart = true;
                    } else {
                        replace = builder.toString();
                        builder = new StringBuilder();
                    }
                    continue;
                }
            }

            builder.append(current);
        }


        if (replace == null) {
            //System.out.println("replace is null");
            return null;
        }
        if (search.isEmpty()) {
            //System.out.println("search is empty");
            return null;
        }

        String result = builder.toString();

        //System.out.println("Search: " + search);
        //System.out.println("Replace: " + replace);
        //System.out.println("Result: " + result);

        return new ReplaceArguments(search, replace, result);
    }

}
