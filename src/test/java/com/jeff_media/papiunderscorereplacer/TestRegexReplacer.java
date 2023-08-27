package com.jeff_media.papiunderscorereplacer;

import org.junit.jupiter.api.Test;

import static com.jeff_media.papiunderscorereplacer.RegexReplacer.replace;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestRegexReplacer {

    @Test
    public void replace_RegexNotMatching_Return_Null() {
        assertNull(replace("test"));
    }

    @Test
    public void replace_Foo_With_Bar_WithoutQuotes_Return_Null() {
        assertNull(replace("foo_bar_foo"));
    }

    @Test
    public void replace_Foo_With_Bar() {
        // "foo"_"bar"_foo
        assertEquals("bar", replace("\"foo\"_\"bar\"_foo"));
    }

    @Test
    public void replace_Underscore_With_Space() {
        // "_"_" "_foo_bar_foo
        assertEquals("foo bar foo", replace("\"_\"_\" \"_foo_bar_foo"));
    }

    @Test
    public void replace_Cat_With_Dog_Twice() {
        // "cat"_"dog"_The cat stared at the other cat from across the room.
        assertEquals("The dog stared at the other dog from across the room.", replace("\"cat\"_\"dog\"_The cat stared at the other cat from across the room."));
    }

    @Test
    public void replace_FooWithQuotes_With_Bar() {
        // "\"foo\""_"bar"_His name was \"foo\".
        assertEquals("His name was bar.", replace("\"\\\"foo\\\"\"_\"bar\"_His name was \\\"foo\\\"."));
    }

}
