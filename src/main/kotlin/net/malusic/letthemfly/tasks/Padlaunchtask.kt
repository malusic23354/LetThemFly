package net.malusic.letthemfly

import org.bukkit.Bukkit
import org.bukkit.block.BlockFace
import org.bukkit.util.Vector

class PadLaunchTask(
    private val configuration: Configuration,
    private val storage: PadStorage,
    private val effects: PadEffects
) : Runnable {

    override fun run() {
        if (storage.pads.isEmpty()) return
        for (player in Bukkit.getOnlinePlayers()) {
            if (!player.isOnGround) continue
            val pad = player.location.block.getRelative(BlockFace.DOWN)
            if (configuration.keyOf(pad) !in storage.pads) continue
            if (!player.hasPermission("letthemfly.pad.use")) continue
            player.velocity = Vector(player.velocity.x, configuration.launchVelocity, player.velocity.z)
            effects.launch(pad)
        }
    }
}