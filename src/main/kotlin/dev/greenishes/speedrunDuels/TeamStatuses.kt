package dev.greenishes.speedrunDuels

import org.bukkit.entity.Player
import java.util.UUID

class TeamStatuses(
    val name: String,
    var status: Boolean = false
) {
    val players = mutableListOf<UUID>()
    val readyPlayers = mutableSetOf<UUID>()

    fun isReady(): Boolean {
        return players.isNotEmpty() &&
                players.all { readyPlayers.contains(it) }
    }
}