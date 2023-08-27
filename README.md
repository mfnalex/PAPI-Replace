PlaceholderAPI extension to replace Strings. Works with ALL characters!

## Usage:

`<search>` is the String to search for.

`<replacement>` is the String to replace the search String with.
    
`<text>` is the String to search in.

If `<search>` or `<replacement>` contains an underscore, the whole `<search>` or `<replacement>` has to be surrounded by `"`!

`%replaceunderscores_<search>_<replacement>_<text>%`

#### Examples:

`%replaceunderscores_ _diamond_axe%` -> `diamond axe`

`%replaceunderscores_/_my name is mfn_alex%` -> `my name is mfn/alex`

`%replaceunderscores_123456789_test_message%` -> `test123456789message`

## Note
This is a standalone plugin that hooks into PAPI, not an ecloud extension. You'll have to put the .jar into your plugins/ folder and restart the server.

## Donate
https://paypal.me/mfnalex

## My other plugins
https://www.spigotmc.org/resources/authors/mfnalex.175238/