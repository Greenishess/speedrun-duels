package dev.greenishes.speedrunDuels

class GameManager {
    var status = false
    fun gameStatus(change: Boolean?): Boolean {
        if (change != null) {
            status = change
        }

        return status
    }

    //gameStatus(true)   sets game running
    //gameStatus(false)  sets game stopped
    //gameStatus(null)   just checks status
}