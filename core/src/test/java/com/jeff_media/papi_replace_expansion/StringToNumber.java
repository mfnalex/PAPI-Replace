package com.jeff_media.papi_replace_expansion;

import java.util.regex.Pattern;

public class StringToNumber {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?[0-9]+$");

    public static Number parse(String numberString) {
        try {
            if (!INTEGER_PATTERN.matcher(numberString).matches()) {
                return Double.parseDouble(numberString);
            }
            Long asLong = Long.parseLong(numberString);
            if(asLong >= Integer.MIN_VALUE && asLong <= Integer.MAX_VALUE) {
                return asLong.intValue();
            } else {
                return asLong;
            }
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
