package dev.greenishes.speedrunDuels

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

class WinEffects(
    private val plugin: JavaPlugin
) {

    fun goldFireworks(player: Player) {
        repeat(100) { i ->
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                spawnFirework(
                    randomLocation(player.location),
                    Color.YELLOW
                )
            }, i * 10L)
        }
    }

    fun greenFireworks(player: Player) {
        repeat(100) { i ->
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                spawnFirework(
                    randomLocation(player.location),
                    Color.LIME
                )
            }, i * 10L)
        }
    }

    private fun randomLocation(center: Location): Location {
        val x = Random.nextDouble(-4.0, 4.0)
        val y = Random.nextDouble(1.0, 4.0)
        val z = Random.nextDouble(-4.0, 4.0)

        return center.clone().add(x, y, z)
    }

    private fun spawnFirework(location: Location, color: Color) {
        val firework = location.world?.spawn(location, Firework::class.java)

        val meta = firework?.fireworkMeta
        meta?.power = 0

        meta?.addEffect(
            FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(color)
                .trail(true)
                .flicker(true)
                .build()
        )

        if (meta != null) {
            firework?.fireworkMeta = meta
        }

        Bukkit.getScheduler().runTaskLater(plugin, Runnable {
            firework?.isDead?.let {
                if (!it) {
                    firework.detonate()
                }
            }
        }, 40L)
    }
}