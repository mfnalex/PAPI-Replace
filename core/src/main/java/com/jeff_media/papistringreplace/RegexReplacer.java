package com.jeff_media.papistringreplace;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexReplacer implements Parser {

    private static final Pattern PATTERN =
            Pattern.compile("^((`(?<searchBt>([^`]|\\\\`)+)`)|(?<search>([^_`]|\\\\`)+))_((`(?<replaceBt>([^`]|\\\\`)+)`)|(?<replace>([^_`]|\\\\`)+))_(?<text>.*)$");

    private static String unescapeBackticks(String input) {
        return input.replace("\\`", "`");
    }

    @Override
    public @Nullable ReplaceArguments parse(@NotNull String input) {
        Matcher matcher = PATTERN.matcher(input);
        if (!matcher.matches()) return null;

        String search = Group.SEARCH.get(matcher);
        String replace = Group.REPLACE.get(matcher);
        String text = Group.TEXT.get(matcher);

        return new ReplaceArguments(search, replace, text);
    }

    private enum Group {
        SEARCH("search", "searchBt"),
        REPLACE("replace", "replaceBt"),
        TEXT() {
            @Override
            public String get(Matcher matcher) {
                return matcher.group("text");
            }
        };

        private final String groupName;
        private final String groupNameBackticked;

        Group(String groupName, String groupNameBackticked) {
            this.groupName = groupName;
            this.groupNameBackticked = groupNameBackticked;
        }

        Group() {
            this.groupName = "";
            this.groupNameBackticked = "";
        }

        public String get(Matcher matcher) {
            String result = matcher.group(groupName);
            if (result == null) {
                result = matcher.group(groupNameBackticked);
            }
            Objects.requireNonNull(result, "Couldn't find group " + name() + " even though it should exist");
            return unescapeBackticks(result);
        }
    }

}
