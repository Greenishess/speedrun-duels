package dev.greenishes.speedrunDuels

import com.google.gson.GsonBuilder
import java.io.File

class ScoreManager(
    private val plugin: SpeedrunDuels
) {

    private val file = File(plugin.dataFolder, "scores.json")
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private var scores = Scores()

    init {
        load()
    }


    fun recordWin(team: TeamStatuses) {

        team.players.forEach { player ->
            scores.individualWins[player.name] =
                scores.individualWins.getOrDefault(player.name, 0) + 1
        }


        val members = team.players
            .map { it.name }
            .sorted()


        val existingTeam = scores.teamWins.find {
            it.members == members
        }


        if(existingTeam != null) {
            existingTeam.wins++
        } else {
            scores.teamWins.add(
                TeamScore(
                    members,
                    1
                )
            )
        }


        save()
    }


    fun save() {
        if(!plugin.dataFolder.exists())
            plugin.dataFolder.mkdirs()

        file.writeText(
            gson.toJson(scores)
        )
    }


    private fun load() {
        if(!file.exists()) {
            save()
            return
        }

        scores = gson.fromJson(
            file.readText(),
            Scores::class.java
        ) ?: Scores()
    }

    fun getScores(): Scores {
        return scores
    }
}



class Scores {

    var individualWins: MutableMap<String, Int> = mutableMapOf()

    var teamWins: MutableList<TeamScore> = mutableListOf()
}



class TeamScore(
    val members: List<String>,
    var wins: Int
)