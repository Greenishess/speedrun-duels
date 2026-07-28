package dev.greenishes.speedrunDuels.listeners

import dev.greenishes.speedrunDuels.GameManager
import dev.greenishes.speedrunDuels.TeamManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.UUID

class BackCooldownListener(
    private val plugin: JavaPlugin,
    private val gameManager: GameManager,
    private val teamManager: TeamManager
) : Listener {

    private val cooldowns = mutableMapOf<UUID, Long>()

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {

        if (!gameManager.gameStatus(null)) {
            return
        }

        val victim = event.entity
        val killer = victim.killer ?: return

        if (teamManager.getTeam(victim) == teamManager.getTeam(killer)) {
            return
        }

        val cooldownSeconds = plugin.config.getInt("back-cooldown-on-enemy-kill")

        if (cooldownSeconds <= 0) {
            return
        }

        cooldowns[victim.uniqueId] =
            System.currentTimeMillis() + (cooldownSeconds * 1000)

        victim.sendMessage(
            "§c/back disabled for ${cooldownSeconds}s because you were killed by an opponent."
        )
    }


    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {

        if (!event.message.equals("/back", ignoreCase = true)) {
            return
        }

        val player = event.player

        val cooldown = cooldowns[player.uniqueId] ?: return

        val remaining = cooldown - System.currentTimeMillis()

        if (remaining > 0) {
            event.isCancelled = true

            val seconds = remaining / 1000

            player.sendMessage(
                "§cYou cannot use /back for ${seconds}s."
            )
        } else {
            cooldowns.remove(player.uniqueId)
        }
    }
}