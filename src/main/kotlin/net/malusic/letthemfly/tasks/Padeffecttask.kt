package net.malusic.letthemfly

class PadEffectTask(
    private val configuration: Configuration,
    private val storage: PadStorage,
    private val effects: PadEffects
) : Runnable {

    override fun run() {
        val stale = mutableListOf<String>()
        for (key in storage.pads.toList()) {
            val block = configuration.blockOf(key) ?: continue
            if (block.type != configuration.padMaterial) {
                stale.add(key)
                continue
            }
            effects.idle(block)
        }
        stale.forEach(storage::remove)
    }
}