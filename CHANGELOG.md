# Changelog

## 0.1.0

- Initial release
- Pads are set by placing the configured pad block (`letthemfly.pad.set`) and removed by breaking it (`letthemfly.pad.remove`)
- Stepping on a pad launches the player upward, and players standing still on it keep being launched (`letthemfly.pad.use`, granted to everyone by default)
- Launch height configurable with `pad.launch-velocity`
- Each pad shows a nail-shaped idle particle effect rising half a block above it, with particle type and interval configurable
- Launching shows two expanding rings above the pad, with particle type configurable
- Pad block material configurable with `pad.material`
- Activation sound played when a player is launched, with sound volume and pitch configurable
- Denied placement or removal is cancelled and the player receives a configurable message
- Pad locations saved to SQLite (`plugins/LetThemFly/pads.db`) so they persist across restarts
- Stale pads, such as ones removed by explosions, are cleaned up automatically
- `/letthemfly reload` re-reads `config.yml` without a restart (requires `letthemfly.reload`, op by default)