This is a reloaded version of plugin "ScLoad"

Original creator : https://dev.bukkit.org/members/fromgate

Original that is no longer supported for versions 1.12 or above.

https://dev.bukkit.org/projects/schematic

Spigot page

https://www.spigotmc.org/resources/scload-reloaded.88674/


ScLoad
ScLoad (or SchematicLoad) is a simple plugin that provide alternative way to load and place 
structures stored as schematic files at you WorldEdit (or ScLoad) directory. ScLoad is using 
configurable queue and main goal is ability to load schematics without any lags and freezes at server.

Features
Load structures from schematic file and build it in player location
Load structures from schematic file and build it in location defined by coordinates
Allows plugin's command console — you start loading schematics using the scripts or any quest engine.
Why do I want it?
If you need to load schematics without any lag and kicking off your players you need this plugin.

Commands
/scload help — shows help page
/scload load <filename> [<world> <x> <y> <z>] — load structure from the schematic file and build it at coords
/scload load <filename> — load structure from the schematic file and build it where you are standing
/scload list — display list of schematic files at directory
/scload cfg — shows configuration
/scload reload — reload settings from the config file
P.S. Alias for /scload command is /scl.
— load structure from the schematic file and build it where you are standing

Permissions
schematic.config - Allows to use all configuration commands and receive update notifications
schematic.load - Allows to use commands /scload load and /scload list
schematic.file - Allows to load all files using command /scload load
schematic.file.<filename> - Allows to load file filename using command /scload load

ScLoad and WorldEdit
ScLoad requires a WorldEdit plugin installed at your server. It uses API provided by 
WorldEdit to load schematic and build it. But it creates optimized queue process to build 
structures: group it by chunks, configurable delays and blocks per ticks parameters. 
And yes: ScLoad will not work if you don't have WorldEdit installed at your server.
