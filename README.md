# NewPlugin

A plugin with hopefully everything you need from a single plugin. Currently for Minecraft Version 1.18

## Requirements and Installation

-   [Spigot API 1.18](https://hub.spigotmc.org/javadocs/spigot/)

## Optional Dependencies

-   None

## Usage

### Commands

There are all the commands:

    -   /back
    -   /backpack
    -   /build
    -   /clearchat
    -   /enderchest
    -   /fly
    -   /freeze
    -   /gamemode
    -   /heal
    -   /ip
    -   /ping
    -   /position
    -   /see
    -   /timer
    -   /top
    -   /vanish

If the Usage of a command contains a word in these <> Brackets you have to input a Object/Nummer/String of this type.<br/>
If the contains a word in these [<>] brackets, it means that this input is not binding.

-   /back

    -   Go back to the position where you died.
    -   Usage: `/back`
    -   Aliases: none
    -   Permission: `newplugin.back`

-   /backpack

    -   Open your Backpack, that is saved in the plugins config file.
    -   Usage: `/backpack`
    -   Aliases: `/bp`
    -   Permission: `newplugin.backpack`

-   /build

    -   Switch to build mode.
    -   Usage: `/build`
    -   Permission: `newplugin.build`

-   /clearchat

    -   Clear the chat with as many blank lines as you want. Default is 20 lines.
    -   Usage: `/clearchat [<int>]`
    -   Aliases: `/cc`
    -   Permission: `newplugin.clearchat`

-   /enderchest

    -   See you enderchest on the go. Where ever you want.
    -   Usage: `/enderchest`
    -   Aliases: `/ec`
    -   Permission: `newplugin.enderchest`

-   /fly

    -   Fly throught the sky with this simple command.
    -   Usage: `/fly [<player>]`
    -   Condition: `gamemode = survival`
    -   Permission: `newplugin.fly`

-   /freeze

    -   Freeze a player on its position. Helpful if if destroys you server.
    -   Usage: `/freeze <player>`
    -   Permission: `newplugin.freeze`

-   /gamemode

    -   Gamemode. Brings back the old version where you can just input `1` to switch to creative mode.
    -   Usage: `/gamemode <gamemode> [<player>]`
    -   Aliases: `/gm`
    -   Permission: `newplugin.gamemode`

-   /heal

    -   Damage. Hunger. No problem. Fills you health and hunger bar.
    -   Usage: `/heal [<player>]`
    -   Permission: `newplugin.heal`

-   /ip

    -   Shows you ip.
    -   Usage: `/ip`
    -   Permission: `newplugin.ip`

-   /ping

    -   Shows you ping to the server.
    -   Usage: `/ping`
    -   Permission: `newplugin.ping`

-   /position

    -   Create, delete, show and list all you saved positions.
    -   Usage: `/position create/delete/show/list <positonName>`
    -   Aliases: `/pos`
    -   Permission: `newplugin.position`

-   /see

    -   See a players backpack, enderchest and inventory.
    -   Usage: `/see backpack/enderchest/inventory <player>`
    -   Permission: `newplugin.see`

-   /timer

    -   Controll the timer
    -   Usage: `/timer start/pause/reset/show/hide/set [<int>]`
    -   Permission: `newplugin.timer`

-   /top

    -   Teleport yourself out of a deep cave.
    -   Usage: `/top`
    -   Permission: `newplugin.top`

-   /vanish

    -   Vanish.
    -   Usage: `/vanish`
    -   Aliases: `/v`
    -   Permission: `newplugin.vanish`

### Configuration

After enabling the plugin you can configure it in the `config.yml` located in the `NewPlugin` folder in your plugins directory.

Documentation will follow.

## Other importent stuff

### Inspired and copied from

-   DerBankos [TutorialReloaded Plugin](https://github.com/DerBanko/TutorialReloaded)
-   GHG on [YouTube](https://www.youtube.com/c/GHG_TUTORIALS)
-   CoasterFreaks [SimpleLobby Plugin](https://github.com/CoasterFreakDE/SimpleLobby)
-   CoolePizza on [YouTube](https://github.com/CoasterFreakDE/SimpleLobby)
-   Many more

### CONTRIBUTING

Read the contributing instructions [here](https://github.com/tobiaswild/NewPlugin/blob/master/CONTRIBUTING.md)

### LICENSE

MIT License.

You can create your own Plugin for free without notifying me by forking this project under the following conditions:

-   Add a link to this project
