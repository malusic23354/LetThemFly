package net.malusic.letthemfly

import org.bukkit.block.BlockFace
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector

class PadListener(private val configuration: Configuration, private val storage: PadStorage) : Listener {

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        if (event.blockPlaced.type != configuration.padMaterial) return
        val player = event.player
        if (!player.hasPermission("letthemfly.pad.set")) {
            event.isCancelled = true
            player.sendRichMessage(configuration.noSetMessage)
            return
        }
        storage.add(configuration.keyOf(event.blockPlaced))
    }

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        val key = configuration.keyOf(event.block)
        if (key !in storage.pads) return
        val player = event.player
        if (!player.hasPermission("letthemfly.pad.remove")) {
            event.isCancelled = true
            player.sendRichMessage(configuration.noRemoveMessage)
            return
        }
        storage.remove(key)
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        if (!event.hasChangedBlock()) return
        val player = event.player
        if (!player.isOnGround || !player.hasPermission("letthemfly.pad.use")) return
        val pad = event.to.block.getRelative(BlockFace.DOWN)
        if (configuration.keyOf(pad) !in storage.pads) return

        player.velocity = Vector(player.velocity.x, configuration.launchVelocity, player.velocity.z)

        val center = pad.location.add(0.5, 1.0, 0.5)
        configuration.activateParticle?.let { pad.world.spawnParticle(it, center, configuration.activateParticleCount) }
        configuration.activateSound?.let { pad.world.playSound(center, it, configuration.soundVolume, configuration.soundPitch) }
    }
}