package me.phil.sieben.it.Sieben.commands.ban;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatClearCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        for (int i = 0; i < 400; i++) {
            for (Player player: Bukkit.getOnlinePlayers()) {
                player.sendMessage("");
            }
        }

        return false;
    }
}
