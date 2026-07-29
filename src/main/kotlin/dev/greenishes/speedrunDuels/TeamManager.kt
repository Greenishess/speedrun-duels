package dev.greenishes.speedrunDuels

import org.bukkit.Bukkit
import org.bukkit.entity.Player

class TeamManager {

    val teams = mutableListOf<TeamStatuses>()

    fun createTeam(name: String, player: Player): Boolean {
        if (teams.any { it.name.equals(name, true) }) {
            return false
        }

        val team = TeamStatuses(name)
        team.players.add(player.uniqueId)

        teams.add(team)

        return true
    }


    fun joinTeam(name: String, player: Player): Boolean {
        if (getTeam(player) != null) {
            return false
        }

        val team = teams.find {
            it.name.equals(name, true)
        } ?: return false

        team.players.add(player.uniqueId)

        return true
    }


    fun ready(player: Player): Boolean {
        val team = getTeam(player) ?: return false

        team.readyPlayers.add(player.uniqueId)

        return true
    }


    fun leaveTeam(player: Player): Boolean {
        val team = getTeam(player) ?: return false

        team.players.remove(player.uniqueId)
        team.readyPlayers.remove(player.uniqueId)

        if (team.players.isEmpty()) {
            teams.remove(team)
        }

        return true
    }


    fun getTeam(player: Player): TeamStatuses? {
        return teams.find {
            it.players.contains(player.uniqueId)
        }
    }


    fun allReady(): Boolean {
        return teams.isNotEmpty() &&
                teams.all { it.isReady() }
    }


    fun getTeamNames(): List<String> {
        return teams.map { it.name }
    }


    fun clearTeams() {
        teams.clear()
    }


    fun getPlayers(team: TeamStatuses): List<Player> {
        return team.players.mapNotNull {
            Bukkit.getPlayer(it)
        }
    }
}