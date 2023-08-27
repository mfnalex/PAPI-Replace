package com.jeff_media.papi_replace_expansion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestReplacer {

    private static final Parser REGEX_REPLACER = new RegexParser();
    private static final Parser NAIVE_REPLACER = new NaiveParser();

    private static void assertReplaceEquals(String expected, String placeholder) {
        assertEquals(expected, REGEX_REPLACER.parseAndReplace(placeholder));
        assertEquals(expected, NAIVE_REPLACER.parseAndReplace(placeholder));
    }

    private static void assertReplaceNull(String placeholder) {
        assertNull(REGEX_REPLACER.parseAndReplace(placeholder));
        assertNull(NAIVE_REPLACER.parseAndReplace(placeholder));
    }

    @Test
    public void replace_NotMatching_Return_Null() {
        assertReplaceNull("test");
    }

    @Test
    public void replace_Unescaped_Backtick_Return_Null() {
        assertReplaceNull("f`o`o_bar_foo");
    }

    @Test
    public void replace_EmptySearch_AlwaysReturn_Null() {
        assertReplaceNull("__");
        assertReplaceNull("_replace_");
        assertReplaceNull("_replace_text");
        assertReplaceNull("__text");
    }

    @Test
    public void replace_EmptyText_Return_Empty() {
        assertReplaceEquals("", "search__");
        assertReplaceEquals("", "search_replace_");
    }

    @Test
    public void replace_Whole_Text_With_Empty_Replace_Return_Empty() {
        assertReplaceEquals("", "search__search");
        assertReplaceEquals("", "search__searchsearch");
    }

    @Test
    public void replace_Part_Of_Text_With_Empty_Replace() {
        assertReplaceEquals("cat", "dog__dogcatdog");
    }

    @Test
    public void replace_Escaped_Backtick() {
        // search     : `
        // replace    : "
        // text       : `hello`
        // result     : "hello"
        // placeholder: \`_"_`hello`
        assertReplaceEquals("\"hello\"", "\\`_\"_`hello`");
    }

    @Test
    public void replace_Escaped_Backtick_In_Backticks() {
        // search     : `
        // replace    : "
        // text       : `hello`
        // result     : "hello"
        // placeholder: `\``_"_`hello`
        assertReplaceEquals("\"hello\"", "`\\``_\"_`hello`");
    }

    @Test
    public void replace_Foo_With_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: foo_bar_foo
        assertReplaceEquals("bar", "foo_bar_foo");
    }

    @Test
    public void replace_Backticked_Foo_With_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: `foo`_bar_foo
        assertReplaceEquals("bar", "`foo`_bar_foo");
    }

    @Test
    public void replace_Foo_With_Backticked_Bar() {
        // search     : foo
        // replace    : bar
        // text       : foo
        // result     : bar
        // placeholder: foo_`bar`_foo
        assertReplaceEquals("bar", "foo_`bar`_foo");
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

    @Test
    public void replace_Backslash_With_Slash() {
        // search     : \
        // replace    : /
        // text       : C:\Users\Jeff
        // result     : C:/Users/Jeff
        // placeholder: \_/_C:\Users\Jeff
        assertReplaceEquals("C:/Users/Jeff", "\\_/_C:\\Users\\Jeff");
    }

    @Test
    public void replace_Slash_With_Backslash() {
        // search     : /
        // replace    : \
        // text       : /
        // result     : \
        // placeholder: /_\_/
        assertReplaceEquals("\\", "/_\\_/");
    }

    @Test
    public void replace_Backslash_In_Backticks_With_Slash() {
        // search     : \
        // replace    : /
        // text       : C:\Users\Jeff
        // result     : C:/Users/Jeff
        // placeholder: `\\`_/_C:\Users\Jeff
        assertReplaceEquals("C:/Users/Jeff", "`\\\\`_/_C:\\Users\\Jeff");
    }

    @Test
    public void replace_Slash_With_Backslash_In_Backticks() {
        // search     : /
        // replace    : \
        // text       : /
        // result     : \
        // placeholder: /_`\\`_/
        assertReplaceEquals("\\", "/_`\\\\`_/");
    }

    @Test
    public void replace_NamespacedKey_With_Backslash() {
        // search    : minecraft:sound/soundname
        // replace   : minecraft:sound\soundname
        // text      : The sound is called minecraft:sound/soundname!
        // result    : The sound is called minecraft:sound\soundname!
        // placeholder: minecraft:sound/soundname_minecraft:sound\soundname_The sound is called minecraft:sound/soundname!
        assertReplaceEquals("The sound is called minecraft:sound\\soundname!", "minecraft:sound/soundname_minecraft:sound\\soundname_The sound is called minecraft:sound/soundname!");
    }

    @Test
    public void replace_NamespacedKey_With_Backslash_Search_In_Backticks() {
        // search    : minecraft:sound/soundname
        // replace   : minecraft:sound\soundname
        // text      : The sound is called minecraft:sound/soundname!
        // result    : The sound is called minecraft:sound\soundname!
        // placeholder: `minecraft:sound/soundname`_minecraft:sound\soundname_The sound is called minecraft:sound/soundname!
        assertReplaceEquals("The sound is called minecraft:sound\\soundname!", "`minecraft:sound/soundname`_minecraft:sound\\soundname_The sound is called minecraft:sound/soundname!");
    }

    @Test
    public void replace_NamespacedKey_With_Backslash_Replace_In_Backticks() {
        // search    : minecraft:sound/soundname
        // replace   : minecraft:sound\soundname
        // text      : The sound is called minecraft:sound/soundname!
        // result    : The sound is called minecraft:sound\soundname!
        // placeholder: minecraft:sound/soundname_`minecraft:sound\\soundname`_The sound is called minecraft:sound/soundname!
        assertReplaceEquals("The sound is called minecraft:sound\\soundname!", "minecraft:sound/soundname_`minecraft:sound\\\\soundname`_The sound is called minecraft:sound/soundname!");
    }

    @Test
    public void replace_DoubleSlash_With_SingleSlash() {
        // search     : //
        // replace    : /
        // text       : //
        // result     : /
        // placeholder: //_/_//
        assertReplaceEquals("/", "//_/_//");
    }

    @Test
    public void replace_DoubleBackSlash_With_SingleBackSlash() {
        // search     : \\
        // replace    : \
        // text       : \\
        // result     : \
        // placeholder: \\_\_\\
        assertReplaceEquals("\\", "\\\\_\\_\\\\");
    }

    @Test
    public void replace_DoubleBackSlash_With_SingleBackSlash_SearchInBackticks() {
        // search     : \\
        // replace    : \
        // text       : \\
        // result     : \
        // placeholder: `\\\\`_\_\\
        assertReplaceEquals("\\", "`\\\\\\\\`_\\_\\\\");
    }

    @Test
    public void replace_DoubleBackSlash_With_SingleBackSlash_ReplaceInBackticks() {
        // search     : \\
        // replace    : \
        // text       : \\
        // result     : \
        // placeholder: \\_`\\`_\\
        assertReplaceEquals("\\", "\\\\_`\\\\`_\\\\");
    }


}
