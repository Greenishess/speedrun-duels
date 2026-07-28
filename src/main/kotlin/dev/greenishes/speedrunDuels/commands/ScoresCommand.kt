package dev.greenishes.speedrunDuels.commands

import dev.greenishes.speedrunDuels.ScoreManager
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ScoresCommand(
    private val scoreManager: ScoreManager
) : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        val scores = scoreManager.getScores()

        sender.sendMessage(
            "${ChatColor.GOLD}${ChatColor.BOLD}SpeedrunDuels Scores"
        )

        sender.sendMessage(
            "${ChatColor.DARK_GRAY}--------------------"
        )


        sender.sendMessage(
            "${ChatColor.YELLOW}${ChatColor.BOLD}Individual Wins"
        )

        if(scores.individualWins.isEmpty()) {
            sender.sendMessage(
                "${ChatColor.GRAY}No individual wins yet."
            )
        } else {
            scores.individualWins.forEach { (player, wins) ->
                sender.sendMessage(
                    "${ChatColor.WHITE}$player ${ChatColor.GRAY}- ${ChatColor.GREEN}$wins wins"
                )
            }
        }


        sender.sendMessage("")

        sender.sendMessage(
            "${ChatColor.YELLOW}${ChatColor.BOLD}Team Wins"
        )

        if(scores.teamWins.isEmpty()) {
            sender.sendMessage(
                "${ChatColor.GRAY}No team wins yet."
            )
        } else {
            scores.teamWins.forEach { team ->
                sender.sendMessage(
                    "${ChatColor.AQUA}${team.members.joinToString(", ")} " +
                            "${ChatColor.GRAY}- ${ChatColor.GREEN}${team.wins} wins"
                )
            }
        }

        return true
    }
}