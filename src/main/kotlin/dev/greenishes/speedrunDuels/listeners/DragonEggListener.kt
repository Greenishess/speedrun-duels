package dev.greenishes.speedrunDuels.listeners

import dev.greenishes.speedrunDuels.BoxManager
import dev.greenishes.speedrunDuels.GameManager
import dev.greenishes.speedrunDuels.ScoreManager
import dev.greenishes.speedrunDuels.TeamManager
import dev.greenishes.speedrunDuels.WinEffects
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent

class DragonEggListener(private val gameManager: GameManager, private val teamManager: TeamManager, private val winEffects: WinEffects, private val boxManager: BoxManager, private val scoreManager: ScoreManager) : Listener {

    @EventHandler
    fun onDragonEgg(event: PlayerAdvancementDoneEvent) {

        val advancement = event.advancement.key.key


        if (advancement == "end/dragon_egg") {
            //make sure we dont bug out if another person gets the egg for say the egg holder drops it
            if (!gameManager.gameStatus(null)) {
                return
            }

            //game over logic
            val player = event.player
            player.sendMessage("You got the dragon egg!")
            gameManager.gameStatus(false)
            boxManager.buildBox()



            //fireworks around the players, gold for team member who got the egg, green for the rest of the team memebers.
            //and obviously NO FIREWORKS FOR THE LOOOOOSERRRRRSSSSSSS

            val winningTeam = teamManager.getTeam(player)

            //update scores
            if(winningTeam != null) {
                scoreManager.recordWin(winningTeam)
            }

            winningTeam?.players?.forEach { member ->

                if(member == player) {
                    winEffects.goldFireworks(member)
                } else {
                    winEffects.greenFireworks(member)
                }

            }
            teamManager.teams
                .filter { it != winningTeam }
                .forEach { team ->
                    team.players.forEach { loser ->
                    }
                }
            teamManager.clearTeams()
        }
    }
}