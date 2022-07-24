package me.phil.sieben.it.Sieben.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Date;

import static me.phil.sieben.it.Sieben.It.StaffPrefix;
import static me.phil.sieben.it.Sieben.ban.inventorys.commands.MuteCommand.mutedPlayer;

public class MuteListener implements Listener {
    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Date now = new Date(System.currentTimeMillis());
        Date muteUntil = mutedPlayer.get(player);
        if (mutedPlayer.containsKey(player)) {
            if (now.before(muteUntil)) {
                event.setCancelled(true);
                player.sendMessage(StaffPrefix + "You are muted until " + muteUntil.getTime());
            }else mutedPlayer.remove(player);
        }

    }
}
