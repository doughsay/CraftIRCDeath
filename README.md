# CraftIRCDeath
This is a remix of [DeathLog by DjDCH](https://github.com/DjDCH/DeathLog).
See who's dying from your IRC channels!

## What does this do?
This plugin will relay death messages from in-game to CraftIRC.  More
specifically, it creates a new tag and endpoint visible to CraftIRC 3.  With
this plugin loaded, you can specify communication paths in your CraftIRC 3
config.yml to specify the tag "death" as a source for death messages.  Your
death messages can be sent to any endpoint tag known to CraftIRC, which works
really well with my other plugin,
[Dynmap2CraftIRC3](http://dev.bukkit.org/server-mods/dynmap2craftirc3/). This
allows you to see death messages on dynmap as well!

## How do I use it?

### Dependencies
First, you need CraftIRC of at least version 3.1.  It definitely won't work
with CraftIRC 2.

### Setup
The only configuration required is in CraftIRC's config.yml.  If you have:

    setting:
		    ...
				auto-paths: true
				...

Then skip this section, it should automatically connect your death endpoint
with IRC chat.  I Don't recommend this way, as auto-paths seems to always
create double messages for me.  If it's false, and you're manually specifying
your communication paths (my preferred method), then you will need to set up
a new path for this to work:

    paths:
        ...
      - source: 'death'
        target: 'yourirctag'

### Formatting and Attributes configuration
The death endpoint is configured as a "minecraft" source, using the event type
"death".  So if you wanted to format how the messages appeared, you would add a
section under formatting called "from-game" with a "death" option, like so:

    formatting:
        from-game:
            ...
            death: '%red%%player% %message%'
        from-irc:
            ...

Also, in order for the death messages to actually be sent, you must add
"death: true" under the default-attributes section:

    default-attributes:
        ...
        attributes:
            chat: true
            action: true
            join: true
            ....
            death: true

### Using it with Dynmap2CraftIRC3

Like I mentioned earlier, if you're using my other plugin
[Dynmap2CraftIRC3](http://dev.bukkit.org/server-mods/dynmap2craftirc3/), you
can also use this to display death messages on dynmap's webchat as well.  Just
add another path like this:

    paths:
        ...
      - source: 'death'
        target: 'dynmap'
