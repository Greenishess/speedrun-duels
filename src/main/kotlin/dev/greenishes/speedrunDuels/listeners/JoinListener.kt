package dev.greenishes.speedrunDuels.listeners

import dev.greenishes.speedrunDuels.BoxManager
import dev.greenishes.speedrunDuels.GameManager
import dev.greenishes.speedrunDuels.SpeedrunDuels
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinListener(
    private val plugin: SpeedrunDuels,
    private val gameManager: GameManager
) : Listener {

    private val boxManager = BoxManager(plugin)

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        if (gameManager.gameStatus(null) == false) {
            player.teleport(boxManager.getLobbyLocation())
        }
        else{
            player.gameMode = GameMode.SPECTATOR
        }
    }
}