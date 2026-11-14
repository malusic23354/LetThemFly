package net.malusic.letthemfly

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class PadEffects(private val plugin: JavaPlugin, private val configuration: Configuration) {

    fun idle(block: Block) {
        val particle = configuration.idleParticle ?: return
        val top = block.location.add(0.5, 1.0, 0.5)
        val steps = 10
        for (i in 0..steps) {
            val t = i.toDouble() / steps
            val height = 0.5 * t
            val radius = 0.12 * (1 - t)
            for (angle in listOf(0.0, PI / 2, PI, 3 * PI / 2)) {
                spawn(particle, top.clone().add(cos(angle) * radius, height, sin(angle) * radius))
            }
        }
    }

    fun launch(block: Block) {
        val top = block.location.add(0.5, 1.0, 0.5)
        configuration.activateSound?.let { block.world.playSound(top, it, configuration.soundVolume, configuration.soundPitch) }
        val particle = configuration.activateParticle ?: return
        sweep(particle, top.clone().add(0.0, 0.7, 0.0), 1.7 / 2, 0L)
        sweep(particle, top.clone().add(0.0, 1.5, 0.0), 1.2 / 2, 4L)
    }

    private fun sweep(particle: Particle, center: Location, radius: Double, delay: Long) {
        val frames = 8
        val pointsPerFrame = 6
        object : BukkitRunnable() {
            var frame = 0

            override fun run() {
                if (frame >= frames) {
                    cancel()
                    return
                }
                for (k in 0 until pointsPerFrame) {
                    val angle = 2 * PI * (frame + k.toDouble() / pointsPerFrame) / frames
                    spawn(particle, center.clone().add(cos(angle) * radius, 0.0, sin(angle) * radius))
                }
                frame++
            }
        }.runTaskTimer(plugin, delay, 2L)
    }

    private fun spawn(particle: Particle, location: Location) {
        location.world.spawnParticle(particle, location, 1)
    }
}