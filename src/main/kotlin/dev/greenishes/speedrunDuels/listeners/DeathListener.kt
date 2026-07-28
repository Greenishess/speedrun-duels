package dev.greenishes.speedrunDuels.listeners

import dev.greenishes.speedrunDuels.BoxManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent


class DeathListener : Listener {
    private lateinit var boxManager: BoxManager

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        val player = event.entity
    }
}