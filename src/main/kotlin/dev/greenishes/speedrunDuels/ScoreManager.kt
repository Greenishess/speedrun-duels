package dev.greenishes.speedrunDuels

import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
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

    //should probably use uuids instead of usernames but in my personal case i want usernames.
    //eh, maybe one day ill make scores_usernames.json and scores_uuids.json

    fun recordWin(team: TeamStatuses) {

        team.players.forEach { uuid ->
            val player = Bukkit.getOfflinePlayer(uuid)

            scores.individualWins[player.name ?: uuid.toString()] =
                scores.individualWins.getOrDefault(player.name ?: uuid.toString(), 0) + 1
        }


        val members = team.players
            .map { uuid ->
                Bukkit.getOfflinePlayer(uuid).name ?: uuid.toString()
            }
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