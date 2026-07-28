package dev.greenishes.speedrunDuels.commands

import dev.greenishes.speedrunDuels.GameManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GameStatusCommand(
    private val gameManager: GameManager
) : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {

        if (gameManager.gameStatus(null)) {
            sender.sendMessage("true")
        } else {
            sender.sendMessage("false")
        }

        return true
    }
}