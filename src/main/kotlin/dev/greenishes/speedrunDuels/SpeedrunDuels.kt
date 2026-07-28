package dev.greenishes.speedrunDuels

import dev.greenishes.speedrunDuels.commands.GameStatusCommand
import dev.greenishes.speedrunDuels.commands.ScoresCommand
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import dev.greenishes.speedrunDuels.commands.TeamCommand
import dev.greenishes.speedrunDuels.listeners.BackCooldownListener
import dev.greenishes.speedrunDuels.listeners.DragonEggListener
import dev.greenishes.speedrunDuels.listeners.JoinListener
import dev.greenishes.speedrunDuels.listeners.RespawnListener

class SpeedrunDuels : JavaPlugin() {
    private lateinit var boxManager: BoxManager
    private lateinit var teamManager: TeamManager
    private lateinit var gameManager: GameManager
    private lateinit var winEffects: WinEffects
    private lateinit var bossBarManager: BossBarManager
    private lateinit var scoreManager: ScoreManager

    override fun onEnable() {
        logger.info("Enabling Speedrun Duels")
        saveDefaultConfig()
        //run gamerule commands
        if(config.getBoolean("enable-locator-bar") == false){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:overworld run gamerule locator_bar false")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:the_nether run gamerule locator_bar false")
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:the_end run gamerule locator_bar false")
        }
        if(config.getBoolean("enable-one-player-sleep") == true){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:overworld run gamerule players_sleeping_percentage 0")
        }

        //build the box
        boxManager = BoxManager(this)
        boxManager.buildBox()

        //registers
        teamManager = TeamManager()
        gameManager = GameManager()
        winEffects = WinEffects(this)
        bossBarManager = BossBarManager(this, gameManager)
        scoreManager = ScoreManager(this)




        //enable listeners
        server.pluginManager.registerEvents(
            RespawnListener(boxManager, gameManager),
            this
        )
        server.pluginManager.registerEvents(
            JoinListener(this, gameManager),
            this
        )
        server.pluginManager.registerEvents(
            DragonEggListener(gameManager, teamManager, winEffects, boxManager, scoreManager),
            this
        )
        server.pluginManager.registerEvents(
            BackCooldownListener(this, gameManager, teamManager),
            this
        )


        //commands

        val teamCommand = TeamCommand(
            boxManager,
            teamManager,
            gameManager
        )

        getCommand("team")?.setExecutor(teamCommand)
        getCommand("team")?.tabCompleter = teamCommand
        getCommand("gamestatus")?.setExecutor(
            GameStatusCommand(gameManager)
        )
        getCommand("scores")?.setExecutor(
            ScoresCommand(scoreManager)
        )


    }


        override fun onDisable() {
            logger.info("Disabling Speedrun Duels")
            bossBarManager.destroy()
        }
}

