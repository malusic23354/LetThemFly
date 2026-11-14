package net.malusic.letthemfly

import org.bukkit.plugin.java.JavaPlugin
import java.sql.Connection
import java.sql.DriverManager

class PadStorage(plugin: JavaPlugin) {

    val pads: MutableSet<String> = mutableSetOf()
    private val connection: Connection

    init {
        plugin.dataFolder.mkdirs()
        connection = DriverManager.getConnection("jdbc:sqlite:${plugin.dataFolder.resolve("pads.db").absolutePath}")
        connection.createStatement().use { it.executeUpdate("CREATE TABLE IF NOT EXISTS pads (location TEXT PRIMARY KEY)") }
        connection.createStatement().use { statement ->
            statement.executeQuery("SELECT location FROM pads").use { rs ->
                while (rs.next()) pads.add(rs.getString("location"))
            }
        }
    }

    fun add(key: String) {
        if (!pads.add(key)) return
        connection.prepareStatement("INSERT OR IGNORE INTO pads (location) VALUES (?)").use {
            it.setString(1, key)
            it.executeUpdate()
        }
    }

    fun remove(key: String) {
        if (!pads.remove(key)) return
        connection.prepareStatement("DELETE FROM pads WHERE location = ?").use {
            it.setString(1, key)
            it.executeUpdate()
        }
    }

    fun close() = connection.close()
}