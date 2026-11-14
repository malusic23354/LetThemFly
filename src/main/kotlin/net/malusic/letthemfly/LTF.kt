package net.malusic.letthemfly

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin

class LTF : JavaPlugin() {

    private lateinit var configuration: Configuration
    private lateinit var storage: PadStorage

    override fun onEnable() {
        configuration = Configuration(this)
        storage = PadStorage(this)
        server.pluginManager.registerEvents(PadListener(configuration, storage), this)
        server.scheduler.runTaskTimer(this, PadEffectTask(configuration, storage), 0L, configuration.idleIntervalTicks)
        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            event.registrar().register(CommandHandler(configuration).build())
        }
        logger.info("LetThemFly ${pluginMeta.version} enabled")
    }

    override fun onDisable() {
        if (::storage.isInitialized) storage.close()
    }
}