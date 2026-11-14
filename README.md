<div align="center">

# LetThemFly

**Simple launch pads for Paper servers.**

[![Version](https://img.shields.io/badge/version-0.1.0-blue)](https://modrinth.com/plugin/letthemfly)
[![Platform](https://img.shields.io/badge/platform-Paper-lightgrey)](https://papermc.io/)
[![Minecraft](https://img.shields.io/badge/minecraft-1.21.11%20%2B-brightgreen)](https://papermc.io/)
[![Language](https://img.shields.io/badge/language-Kotlin-7F52FF)](https://kotlinlang.org/)

</div>

Place a configured block to turn it into a launch pad. Anyone with permission who steps on it is thrown into the air, with particles and a sound to match.

## Features

- Pads are set by placing the configured pad block, and removed by breaking it
- Idle particles mark every pad
- Launch height is configurable
- Activation particles and sound play when a player is launched, with volume and pitch set in `config.yml`
- Pad locations are saved to `plugins/LetThemFly/pads.db` (SQLite), so they persist across restarts
- Stale pads, such as ones removed by explosions, are cleaned up automatically

## Requirements

| Requirement | Details |
|---|---|
| Server | Paper 1.21.11 or newer |
| Java | 21 or newer |

## Installation

1. Download the latest release and drop the jar into your server's `plugins` folder.
2. Start the server once to generate `plugins/LetThemFly/config.yml`.
3. Edit the config to taste, then restart the server.

## Permissions

| Permission | Description | Default |
|---|---|---|
| `letthemfly.pad.set` | Place the pad block to create a pad | false |
| `letthemfly.pad.remove` | Break a pad to remove it | false |
| `letthemfly.pad.use` | Step on a pad to be launched | true |

Without `letthemfly.pad.set` or `letthemfly.pad.remove`, placing or breaking a pad is cancelled and the player is told so.

## Configuration

```yaml
pad:
  material: SLIME_BLOCK
  launch-velocity: 1.5
  idle-particle:
    particle: END_ROD
    count: 2
    interval-ticks: 10
  activate-particle:
    particle: CLOUD
    count: 20
  activate-sound:
    sound: ENTITY_ENDER_DRAGON_FLAP
    volume: 1.0
    pitch: 1.0
messages:
  no-set-permission: "<red>You don't have permission to set pads."
  no-remove-permission: "<red>You don't have permission to remove pads."
```

| Option | Description |
|---|---|
| `pad.material` | Block material used for pads |
| `pad.launch-velocity` | Upward velocity applied when a player steps on a pad |
| `pad.idle-particle` | Particle shown on every pad, how many, and how often in ticks |
| `pad.activate-particle` | Particle shown when a player is launched |
| `pad.activate-sound` | Sound played when a player is launched, with volume and pitch |
| `messages` | Messages sent when placing or breaking a pad is denied. Supports MiniMessage |

Particle and sound names must match Minecraft's registry names, for example `END_ROD` or `ENTITY_ENDER_DRAGON_FLAP`.

## Building from source

The project targets Java 21 and uses the Gradle wrapper:

```bash
git clone https://github.com/malusic23354/letthemfly.git
cd letthemfly
./gradlew build
```

The plugin jar is written to `build/libs/LetThemFly-<version>.jar`.

## Links

- Source: [GitHub](https://github.com/malusic23354/letthemfly)