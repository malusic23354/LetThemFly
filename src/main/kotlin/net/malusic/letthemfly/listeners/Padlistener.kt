package net.malusic.letthemfly

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

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
}