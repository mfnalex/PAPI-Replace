package com.jeff_media.papistringreplace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestReplacer {

    private static final Parser REGEX_REPLACER = new RegexReplacer();
    private static final Parser NAIVE_REPLACER = new NaiveReplacer();

    @Test
    public void replace_NotMatching_Return_Null() {
        assertReplaceNull("test");
    }

    @Test
    public void replace_Unespaced_Backtick_Return_Null() {
        assertReplaceNull("f`o`o_bar_foo");
    }

    @Test
    public void replace_Foo_With_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: foo_bar_foo
        assertReplaceEquals("bar","foo_bar_foo");
    }

    @Test
    public void replace_Backticked_Foo_With_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: `foo`_bar_foo
        assertReplaceEquals("bar","`foo`_bar_foo");
    }

    @Test
    public void replace_Foo_With_Backticked_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: foo_`bar`_foo
        assertReplaceEquals("bar","foo_`bar`_foo");
    }

    @Test
    public void replace_Backticked_Foo_With_Backticked_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: `foo`_`bar`_foo
        assertReplaceEquals("bar", "`foo`_`bar`_foo");
    }

    @Test
    public void replace_Backticked_Underscore_With_Backticked_Space() {
        // search     : _
        // replace    : <space>
        // text       : foo_bar_foo
        // result     : foo bar foo
        // placeholder: `_`_` `_foo_bar_foo
        assertReplaceEquals("foo bar foo", "`_`_` `_foo_bar_foo");
    }

    @Test
    public void replace_Backticked_Cat_With_Backticked_Dog_Twice() {
        // search     : cat
        // replace    : dog
        // text       : The cat stared at the other cat from across the room.
        // result     : The dog stared at the other dog from across the room.
        // placeholder: `cat`_`dog`_The cat stared at the other cat from across the room.
        assertReplaceEquals("The dog stared at the other dog from across the room.", "`cat`_`dog`_The cat stared at the other cat from across the room.");
    }

    @Test
    public void replace_Backticked_FooWithQuotes_With_Backticked_Bar() {
        // search     : "foo"
        // replace    : bar
        // text       : His name was "foo".
        // result     : His name was bar.
        // placeholder: `"foo"`_`bar`_His name was "foo".
        assertReplaceEquals("His name was bar.", "`\"foo\"`_`bar`_His name was \"foo\".");
    }

    @Test
    public void replace_Backticked_FooWithBackticks_With_Backticked_Bar() {
        // search     : `foo`
        // replace    : bar
        // text       : His name was `foo`.
        // result     : His name was bar.
        // placeholder: `\`foo\``_`bar`_His name was `foo`.
        assertReplaceEquals("His name was bar.", "`\\`foo\\``_`bar`_His name was `foo`.");
    }

    @Test
    public void replace_FooWithBackticks_With_BarWithBackticks() {
        // search     : `foo`
        // replace    : `bar`
        // text       : His name was `foo`.
        // result     : His name was `bar`.
        // placeholder: \`foo\`_\`bar\`_His name was `foo`.
        assertReplaceEquals("His name was `bar`.", "\\`foo\\`_\\`bar\\`_His name was `foo`.");
    }

    private static void assertReplaceEquals(String expected, String placeholder) {
        assertEquals(expected, REGEX_REPLACER.parseAndReplace(placeholder));
        assertEquals(expected, NAIVE_REPLACER.parseAndReplace(placeholder));
    }

    private static void assertReplaceNull(String placeholder) {
        assertNull(REGEX_REPLACER.parseAndReplace(placeholder));
        assertNull(NAIVE_REPLACER.parseAndReplace(placeholder));
    }


}
