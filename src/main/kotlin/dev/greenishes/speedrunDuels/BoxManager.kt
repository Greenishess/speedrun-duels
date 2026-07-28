package dev.greenishes.speedrunDuels

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.plugin.java.JavaPlugin

class BoxManager(private val plugin: JavaPlugin) {

    fun buildBox() {
        val world: World = plugin.server.worlds.first()

        val spawn = world.spawnLocation

        val centerX = spawn.blockX
        val centerY = 100
        val centerZ = spawn.blockZ

        val width = 25
        val length = 25
        val height = 12

        val halfWidth = width / 2
        val halfLength = length / 2

        for (x in -halfWidth..halfWidth) {
            for (y in 0 until height) {
                for (z in -halfLength..halfLength) {

                    val block = world.getBlockAt(
                        centerX + x,
                        centerY + y,
                        centerZ + z
                    )

                    val material = when {
                        y == height - 1 -> Material.BARRIER

                        x == -halfWidth ||
                                x == halfWidth ||
                                z == -halfLength ||
                                z == halfLength ||
                                y == 0 -> Material.BEDROCK

                        else -> Material.AIR
                    }

                    block.type = material
                }
            }
        }

        plugin.logger.info("Built lobby box at $centerX, $centerY, $centerZ")
    }

    fun getLobbyLocation(): Location {
        val world = plugin.server.worlds.first()
        val spawn = world.spawnLocation

        return Location(
            world,
            spawn.blockX + 0.5,
            101.0,
            spawn.blockZ + 0.5
        )
    }


    fun deleteBox() {
        val world: World = plugin.server.worlds.first()

        val spawn = world.spawnLocation

        val centerX = spawn.blockX
        val centerY = 100
        val centerZ = spawn.blockZ

        val width = 25
        val length = 25
        val height = 12

        val halfWidth = width / 2
        val halfLength = length / 2

        for (x in -halfWidth..halfWidth) {
            for (y in 0 until height) {
                for (z in -halfLength..halfLength) {

                    val block = world.getBlockAt(
                        centerX + x,
                        centerY + y,
                        centerZ + z
                    )

                    block.type = Material.AIR
                }
            }
        }

        plugin.logger.info("Deleted lobby box")
    }
}