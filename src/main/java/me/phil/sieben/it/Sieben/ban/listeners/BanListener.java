package me.phil.sieben.it.Sieben.ban.listeners;

import me.phil.sieben.it.Sieben.It;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class BanListener implements Listener {

    @EventHandler
    public void onPreLogin(PlayerLoginEvent event) {

        if (event.getResult().equals(PlayerLoginEvent.Result.KICK_BANNED)) {
            Player player = event.getPlayer();


            String first_line,sec_line,thr_line, fou_line, BanString;
            first_line = It.getInstance().getConfig().getString("ban.screen.line_1");
            sec_line = It.getInstance().getConfig().getString("ban.screen.line_2");
            thr_line = It.getInstance().getConfig().getString("ban.screen.line_3");
            fou_line = It.getInstance().getConfig().getString("ban.screen.line_4");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            String duration = formatter.format(Objects.requireNonNull(Bukkit.getServer().getBanList(BanList.Type.NAME).getBanEntry(player.getName())).getExpiration());
            String reason = Objects.requireNonNull(Bukkit.getServer().getBanList(BanList.Type.NAME).getBanEntry(player.getName())).getReason();
            String source = Objects.requireNonNull(Bukkit.getServer().getBanList(BanList.Type.NAME).getBanEntry(player.getName())).getSource();
            BanString = (first_line + "&7\n" + sec_line + "&7\n" + thr_line + "&7\n" + fou_line).replaceAll("%duration%", duration).replaceAll("%reason%", reason).replaceAll("%source%", source).replaceAll("&", "§");

            event.setKickMessage(BanString);
        }
    }
}
