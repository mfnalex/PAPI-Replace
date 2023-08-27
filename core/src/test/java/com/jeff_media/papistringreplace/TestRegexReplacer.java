package com.jeff_media.papistringreplace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestRegexReplacer {

    // I wanna run all these tests for NaiveReplacer too!
    private final Parser parser = new RegexReplacer();

    @Test
    public void replace_RegexNotMatching_Return_Null() {
        assertNull(replace("test"));
    }

    @Test
    public void replace_Foo_With_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: foo_bar_foo
        assertEquals("bar",replace("foo_bar_foo"));
    }

    @Test
    public void replace_Backticked_Foo_With_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: `foo`_bar_foo
        assertEquals("bar",replace("`foo`_bar_foo"));
    }

    @Test
    public void replace_Foo_With_Backticked_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: foo_`bar`_foo
        assertEquals("bar",replace("foo_`bar`_foo"));
    }

    @Test
    public void replace_Backticked_Foo_With_Backticked_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: `foo`_`bar`_foo
        assertEquals("bar", replace("`foo`_`bar`_foo"));
    }

    @Test
    public void replace_Backticked_Underscore_With_Backticked_Space() {
        // search     : _
        // replace    : <space>
        // text       : foo_bar_foo
        // result     : foo bar foo
        // placeholder: `_`_` `_foo_bar_foo
        assertEquals("foo bar foo", replace("`_`_` `_foo_bar_foo"));
    }

    @Test
    public void replace_Backticked_Cat_With_Backticked_Dog_Twice() {
        // search     : cat
        // replace    : dog
        // text       : The cat stared at the other cat from across the room.
        // result     : The dog stared at the other dog from across the room.
        // placeholder: `cat`_`dog`_The cat stared at the other cat from across the room.
        assertEquals("The dog stared at the other dog from across the room.", replace("`cat`_`dog`_The cat stared at the other cat from across the room."));
    }

    @Test
    public void replace_Backticked_FooWithQuotes_With_Backticked_Bar() {
        // search     : "foo"
        // replace    : bar
        // text       : His name was "foo".
        // result     : His name was bar.
        // placeholder: `"foo"`_`bar`_His name was "foo".
        assertEquals("His name was bar.", replace("`\"foo\"`_`bar`_His name was \"foo\"."));
    }

    @Test
    public void replace_Backticked_FooWithBackticks_With_Backticked_Bar() {
        // search     : `foo`
        // replace    : bar
        // text       : His name was `foo`.
        // result     : His name was bar.
        // placeholder: `\`foo\``_`bar`_His name was `foo`.
        assertEquals("His name was bar.", replace("`\\`foo\\``_`bar`_His name was `foo`."));
    }

    @Test
    public void replace_FooWithBackticks_With_BarWithBackticks() {
        // search     : `foo`
        // replace    : `bar`
        // text       : His name was `foo`.
        // result     : His name was `bar`.
        // placeholder: \`foo\`_\`bar\`_His name was `foo`.
        assertEquals("His name was `bar`.", replace("\\`foo\\`_\\`bar\\`_His name was `foo`."));
    }



    private String replace(String input) {
        ReplaceArguments args = parser.parse(input);
        if(args == null) return null;
        return Replacer.replace(args);
    }

}
