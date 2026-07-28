package dev.greenishes.speedrunDuels

import org.bukkit.entity.Player

class TeamStatuses(
    val name: String,
    var status: Boolean = false
) {
    val players = mutableListOf<Player>()
    val readyPlayers = mutableSetOf<Player>()

    fun isReady(): Boolean {
        return players.isNotEmpty() &&
                players.all { readyPlayers.contains(it) }
    }
}