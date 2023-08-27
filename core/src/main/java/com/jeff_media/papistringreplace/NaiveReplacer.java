package com.jeff_media.papistringreplace;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NaiveReplacer implements Parser {

    @Override
    public @Nullable ReplaceArguments parse(@NotNull String input) {

//        System.out.println("Start parsing");
//        System.out.println("Input: " + input);

        char[] chars = input.toCharArray();
        StringBuilder builder = new StringBuilder();

        boolean inBackticks = false;
        boolean lastWasEscape = false;
        boolean isBeginOfPart = true;

        String search = null;
        String replace = null;

        for (char current : chars) {
            if (search != null && replace != null) {
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

            if (lastWasEscape) {
                if (current == Parser.ESCAPE) {
                    lastWasEscape = false;
                    builder.append(Parser.TWO_ESCAPES);
                    continue;
                } else if (current == Parser.BACKTICK) {
                    lastWasEscape = false;
                    builder.append(Parser.BACKTICK);
                    continue;
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
            return null;
        }

        String text = builder.toString();

//        System.out.println("Done parsing:");
//        System.out.println("Search: " + search);
//        System.out.println("Replace: " + replace);
//        System.out.println("Text: " + text);

        return new ReplaceArguments(search, replace, text);
    }

}
