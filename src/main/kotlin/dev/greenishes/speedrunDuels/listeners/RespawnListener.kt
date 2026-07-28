package dev.greenishes.speedrunDuels.listeners

import dev.greenishes.speedrunDuels.BoxManager
import dev.greenishes.speedrunDuels.GameManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class RespawnListener(
    private val boxManager: BoxManager,
    private val gameManager: GameManager
) : Listener {

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        if (gameManager.gameStatus(null) == false) {
            event.respawnLocation = boxManager.getLobbyLocation()
        }
    }
}