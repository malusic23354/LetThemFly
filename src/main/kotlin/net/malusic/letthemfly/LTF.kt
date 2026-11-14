package net.malusic.daclans

import org.bukkit.plugin.java.JavaPlugin

class LTF : JavaPlugin() {

    override fun onEnable() {
        logger.info("LetThemFly ${pluginMeta.version} enabled")
    }

    override fun onDisable() {

    }

}
