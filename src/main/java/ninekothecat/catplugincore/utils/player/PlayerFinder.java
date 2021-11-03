package ninekothecat.catplugincore.utils.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Objects;

public class PlayerFinder {
    public static Player getPlayerFromName(String playerName) {
        Player player = null;
        for (Player player1 : Bukkit.getServer().getOnlinePlayers())
            if (player1.getDisplayName().toUpperCase(Locale.ROOT).equals(playerName.toUpperCase(Locale.ROOT)))
                player = player1;
        if (player == null) {
            for (OfflinePlayer player1 : Bukkit.getServer().getOfflinePlayers())
                if (Objects.requireNonNull(player1.getName()).toUpperCase(Locale.ROOT).equals(playerName.toUpperCase(Locale.ROOT)))
                    player = player1.getPlayer();
        }
        return player;
    }
}
