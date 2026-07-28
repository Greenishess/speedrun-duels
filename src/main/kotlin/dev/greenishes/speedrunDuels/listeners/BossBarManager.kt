package dev.greenishes.speedrunDuels

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class BossBarManager(private val plugin: JavaPlugin, private val gameManager: GameManager) {

    private val bossBar: BossBar = Bukkit.createBossBar(
        "§b§lSpeedrun Duels",
        BarColor.GREEN,
        BarStyle.SOLID
    )

    init {
        Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
            update()
        }, 0L, 20L)
    }

    private fun update() {
        if (plugin.server.onlinePlayers.isEmpty()) {
            return
        }

        if (gameManager.gameStatus(null)) {
            bossBar.removeAll()
            return
        }

        bossBar.progress = 1.0
        bossBar.setTitle(buildTitle())

        plugin.server.onlinePlayers.forEach { player ->
            if (!bossBar.players.contains(player)) {
                bossBar.addPlayer(player)
            }
        }
    }

    private fun buildTitle(): String {

        return "§b§lSpeedrun Duels §7• §fMade by Greenishes"
    }

    fun remove(player: Player) {
        bossBar.removePlayer(player)
    }

    fun destroy() {
        bossBar.removeAll()
    }
}