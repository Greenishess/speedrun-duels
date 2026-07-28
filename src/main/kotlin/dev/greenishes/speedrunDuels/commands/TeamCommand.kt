package dev.greenishes.speedrunDuels.commands

import dev.greenishes.speedrunDuels.BoxManager
import dev.greenishes.speedrunDuels.GameManager
import dev.greenishes.speedrunDuels.TeamManager
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class TeamCommand(
    private val boxManager: BoxManager,
    private val teamManager: TeamManager,
    private val gameManager: GameManager,
) : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if(sender !is Player)
            return true

        val prefix = "${ChatColor.GOLD}${ChatColor.BOLD}SpeedrunDuels ${ChatColor.DARK_GRAY}» ${ChatColor.RESET}"
        if(args.isEmpty()){
            sender.sendMessage("${ChatColor.YELLOW}Team Commands")
            sender.sendMessage("${ChatColor.GOLD}/team create ${ChatColor.YELLOW}<name> ${ChatColor.GRAY}- Create a team")
            sender.sendMessage("${ChatColor.GOLD}/team join ${ChatColor.YELLOW}<name> ${ChatColor.GRAY}- Join a team")
            sender.sendMessage("${ChatColor.GOLD}/team ready ${ChatColor.GRAY}- Ready up")
            return true
        }


        when(args[0].lowercase()){

            "create" -> {
                if(args.size < 2){
                    sender.sendMessage("${ChatColor.GOLD}/team create ${ChatColor.YELLOW}<name> ${ChatColor.GRAY}- Create a team")
                    return true
                }

                if(teamManager.createTeam(args[1], sender)){
                    sender.sendMessage(prefix + "${ChatColor.GREEN}Created team ${ChatColor.YELLOW}${args[1]}${ChatColor.GREEN}.")
                } else {
                    sender.sendMessage(prefix + "${ChatColor.RED}Team already exists.")
                }
            }


            "join" -> {
                if(args.size < 2){
                    sender.sendMessage("${ChatColor.GOLD}/team join ${ChatColor.YELLOW}<name> ${ChatColor.GRAY}- Join a team")
                    return true
                }

                if(teamManager.joinTeam(args[1], sender)){
                    sender.sendMessage(prefix + "${ChatColor.GREEN}Joined ${ChatColor.YELLOW}${args[1]}${ChatColor.GREEN}.")
                } else {
                    sender.sendMessage(prefix + "${ChatColor.RED}Could not join that team.")
                }
            }


            "ready" -> {

                if(teamManager.ready(sender)){
                    sender.sendMessage(prefix + "${ChatColor.GREEN}You are now ready!")

                    if(teamManager.allReady()){
                        sender.server.broadcastMessage(
                            "${ChatColor.GOLD}${ChatColor.BOLD}SpeedrunDuels ${ChatColor.DARK_GRAY}» " +
                                    "${ChatColor.YELLOW}Everyone is ready! ${ChatColor.GREEN}Starting game..."
                        )

                        boxManager.deleteBox()

                        Bukkit.getOnlinePlayers().forEach { player ->
                            player.teleport(player.world.spawnLocation)
                        }
                        gameManager.gameStatus(true)
                    }

                } else {
                    sender.sendMessage(prefix + "${ChatColor.RED}You are not in a team.")
                }
            }
        }

        return true
    }
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {

        if (args.size == 1) {
            return listOf("create", "join", "ready")
                .filter { it.startsWith(args[0], ignoreCase = true) }
                .toMutableList()
        }

        if (args.size == 2 && args[0].equals("join", ignoreCase = true)) {
            return teamManager.getTeamNames()
                .filter { it.startsWith(args[1], ignoreCase = true) }
                .toMutableList()
        }

        return mutableListOf()
    }
}