# Mo' Structures (26.x port)

> **Unofficial port to Minecraft 1.21.9 and 1.21.10 (Fabric).**
> This is a modified version of [Mo' Structures](https://github.com/frqnny/mostructures) by **Frqnny**,
> ported by **Samouxx** in October 2026. The original mod stopped at Minecraft 1.21.8.
> Please report problems with this port [here](https://github.com/Samouxx/mostructures/issues), not to the original author.
>
> Changes from the original 1.6.0:
> - ported to Minecraft 1.21.9 - 1.21.10, Fabric only (the NeoForge build was dropped), single Gradle module;
> - no longer requires Architectury API or Omega Config (Fabric API is still required);
> - config: same file (`config/mostructures-config-v5.json5`), but only `activated` is used. The `spacing` and `separation`
>   values never changed where structures generate (the game reads the `worldgen/structure_set` files), and they made
>   `/locate` search with the wrong grid. Change them with a data pack instead.
>
> The commit history of the `port-26.x` branch explains each change.
>
> License: LGPL-3.0, like the original ([LICENSE](LICENSE), which builds on the GPL-3.0 in [COPYING](COPYING)).

The original README follows.

![GitHub issues](https://img.shields.io/github/issues/frqnny/mostructures.svg)
![GitHub license](https://img.shields.io/github/license/frqnny/mostructures.svg)
[![](http://cf.way2muchnoise.eu/378266.svg)](https://www.curseforge.com/minecraft/mc-mods/mo-structures)
[![](http://cf.way2muchnoise.eu/versions/378266.svg)](https://www.curseforge.com/minecraft/mc-mods/mo-structures)

Discover exciting structures with fun mini-dungeons and integrated lore!

## Contributing

Just contact me beforehand and then make a pr!

## Setup

For setup instructions please see the [fabric wiki page](https://fabricmc.net/wiki/tutorial:setup) that relates to the
IDE that you are using.
