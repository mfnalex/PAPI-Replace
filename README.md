PlaceholderAPI extension to replace Strings. Works with ALL characters!

## Usage:

### General

The basic syntax is:

`%replace_<search>_<replacement>_<text>%`

- `<search>` is the String to search for.
- `<replacement>` is the String to replace the search String with. 
- `<text>` is the String to search in.

### Special characters
#### Underscores (`_`)

If the `<search>` or `<replacement>` part contains one or more underscores, you have to put that part into `` `backticks` ``:

`` %replace_`<search_with_underscores>`_<replacement>_<text>% ``

If both `<search>` and `<replacement>` contain underscores, you have to put both parts into backticks:

`` %replace_`<search_with_underscores>`_`<replacement_with_underscores>`_<text>% ``

The `<text>` part never requires backticks.

#### Backticks (`` ` ``)

If your `<search>` or `<replacement>` contains any backticks, you have to escape them with a backslash:

`` %replace_<search_with_\`_backticks\`>_<replacement_with_\`_backticks\`>_<text>% ``

### Basic Examples

- Replace `foo` with `bar` in `my name is foo`:
  - Placeholder: `%replace_foo_bar_my name is foo%`
  - Result: `my name is bar`
- Replace underscores with spaces in `my_name_is_foo`:
  - Placeholder: `` %replace_`_`_ _my_name_is_foo% ``
  - Result: `my name is foo`
- The `<text>` part never requires escaping:
  - Placeholder: `` %replace_foo_bar_My name is `foo`!% ``
  - Result: `` my name is `bar`! ``

    
## Note
This is a standalone plugin that hooks into PAPI, not an ecloud extension. You'll have to put the .jar into your plugins/ folder and restart the server.

[//]: # (## Todo)

[//]: # (Add configurable templates that can be used, for example:)

[//]: # ()
[//]: # (```yaml)

[//]: # (worldnames:)

[//]: # (  - search: world)

[//]: # (    replace: &aWorld)

[//]: # (  - search: world_nether)

[//]: # (    replace: &cNether)

[//]: # (  - search: world_the_end)

[//]: # (    replace: &9End)

[//]: # ( ```)

[//]: # (You could then use this template together with other placeholders: `%replace_template_worldnames_{player_world}%` would return `&cNether` for a player in world_nether.)

## Donate
https://paypal.me/mfnalex

## My other plugins
https://www.spigotmc.org/resources/authors/mfnalex.175238/