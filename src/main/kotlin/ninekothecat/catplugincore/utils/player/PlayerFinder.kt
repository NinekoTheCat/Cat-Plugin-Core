package ninekothecat.catplugincore.utils.player

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

    fun getPlayerFromName(playerName: String): Player? {
        var player: Player? = Bukkit.getServer().onlinePlayers.lastOrNull { it.displayName.equals(playerName, ignoreCase = true) }
        if (player == null) {
            Bukkit.getServer().offlinePlayers
                .filter {
                    Objects.requireNonNull(it.name)
                        .equals(playerName, ignoreCase = true)
                }
                .forEach { player = it.player }
        }
        return player
    }
