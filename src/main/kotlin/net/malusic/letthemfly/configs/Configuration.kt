package net.malusic.letthemfly

import org.bukkit.Bukkit
import org.bukkit.Keyed
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Particle
import org.bukkit.Registry
import org.bukkit.Sound
import org.bukkit.block.Block
import org.bukkit.plugin.java.JavaPlugin

class Configuration(private val plugin: JavaPlugin) {

    var padMaterial: Material = Material.SLIME_BLOCK
        private set
    var launchVelocity: Double = 1.5
        private set
    var idleParticle: Particle? = null
        private set
    var idleIntervalTicks: Long = 10L
        private set
    var activateParticle: Particle? = null
        private set
    var activateSound: Sound? = null
        private set
    var soundVolume: Float = 1.0f
        private set
    var soundPitch: Float = 1.0f
        private set
    var noSetMessage: String = ""
        private set
    var noRemoveMessage: String = ""
        private set

    init {
        plugin.saveDefaultConfig()
        load()
    }

    fun reload() {
        plugin.reloadConfig()
        load()
    }

    private fun load() {
        val config = plugin.config
        padMaterial = Material.matchMaterial(config.getString("pad.material", "SLIME_BLOCK")!!) ?: Material.SLIME_BLOCK
        launchVelocity = config.getDouble("pad.launch-velocity", 1.5)
        idleParticle = registryValue(Registry.PARTICLE_TYPE, "pad.idle-particle.particle")
        idleIntervalTicks = config.getLong("pad.idle-particle.interval-ticks", 10L)
        activateParticle = registryValue(Registry.PARTICLE_TYPE, "pad.activate-particle.particle")
        activateSound = registryValue(Registry.SOUNDS, "pad.activate-sound.sound")
        soundVolume = config.getDouble("pad.activate-sound.volume", 1.0).toFloat()
        soundPitch = config.getDouble("pad.activate-sound.pitch", 1.0).toFloat()
        noSetMessage = config.getString("messages.no-set-permission", "<red>You don't have permission to set pads.")!!
        noRemoveMessage = config.getString("messages.no-remove-permission", "<red>You don't have permission to remove pads.")!!
    }

    fun keyOf(block: Block) = "${block.world.name},${block.x},${block.y},${block.z}"

    fun blockOf(key: String): Block? {
        val (world, x, y, z) = key.split(",")
        return Bukkit.getWorld(world)?.getBlockAt(x.toInt(), y.toInt(), z.toInt())
    }

    private fun <T : Keyed> registryValue(registry: Registry<T>, path: String): T? =
        plugin.config.getString(path)?.lowercase()?.let { registry.get(NamespacedKey.minecraft(it)) }
}