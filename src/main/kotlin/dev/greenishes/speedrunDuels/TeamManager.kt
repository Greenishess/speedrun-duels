package dev.greenishes.speedrunDuels

import org.bukkit.entity.Player

class TeamManager {

    val teams = mutableListOf<TeamStatuses>()

    fun createTeam(name: String, player: Player): Boolean {
        if (teams.any { it.name.equals(name, true) }) {
            return false
        }

        val team = TeamStatuses(name)
        team.players.add(player)

        teams.add(team)

        return true
    }


    fun joinTeam(name: String, player: Player): Boolean {
        if (getTeam(player) != null) {
            return false // already in a team
        }

        val team = teams.find {
            it.name.equals(name, true)
        } ?: return false

        team.players.add(player)

        return true
    }


    fun ready(player: Player): Boolean {
        val team = getTeam(player) ?: return false

        team.readyPlayers.add(player)

        return true
    }


    fun getTeam(player: Player): TeamStatuses? {
        return teams.find {
            it.players.contains(player)
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
}