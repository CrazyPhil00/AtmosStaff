package me.phil.sieben.it.Sieben.commands.ban;

import org.bukkit.entity.Player;

public class KickCommand {

    public static void kickPlayer(Player player, String message) {
        player.kickPlayer(message);
    }
}
