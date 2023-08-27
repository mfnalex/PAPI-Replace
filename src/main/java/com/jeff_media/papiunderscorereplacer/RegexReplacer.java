package com.jeff_media.papiunderscorereplacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexReplacer {

    private static final Pattern PATTERN = Pattern.compile("^\"(?<search>([^\"]|\\\\\")+)\"_\"(?<replace>([^\"]|\\\\\")+)\"_(?<text>.*)$");

    public static String replace(String input) {
        Matcher matcher = PATTERN.matcher(input);
        if(!matcher.matches()) return null;
        String search = matcher.group("search");
        String replace = matcher.group("replace");
        String text = matcher.group("text");
        return text.replace(search, replace);
    }

}
