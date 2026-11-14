package net.malusic.letthemfly


class PadEffectTask(private val configuration: Configuration, private val storage: PadStorage) : Runnable {

    override fun run() {
        val particle = configuration.idleParticle ?: return
        val stale = mutableListOf<String>()
        for (key in storage.pads.toList()) {
            val block = configuration.blockOf(key) ?: continue
            if (block.type != configuration.padMaterial) {
                stale.add(key)
                continue
            }
            block.world.spawnParticle(particle, block.location.add(0.5, 1.0, 0.5), configuration.idleParticleCount)
        }
        stale.forEach(storage::remove)
    }
}