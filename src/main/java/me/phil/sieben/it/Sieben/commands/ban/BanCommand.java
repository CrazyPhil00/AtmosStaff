package me.phil.sieben.it.Sieben.commands.ban;

import me.phil.sieben.it.Sieben.It;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static me.phil.sieben.it.Sieben.It.StaffPrefix;

public class BanCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // ARGS: /ban [PLAYER] [REASON_ID] [KICK_MSG]
        if (!sender.hasPermission("staff.ban")) {
            sender.sendMessage(StaffPrefix + "§4You don't have the permission to do that!");
            return false;
        }
        if (args.length < 2) {
            String helpMenu = "help";
            sender.sendMessage(helpMenu);
            return false;
        }
        String kickMsg;
        Player target = Bukkit.getPlayer(args[0]);
        int id = Integer.parseInt(args[1]);
        if (!It.getInstance().getConfig().isSet("ban.reason.ban_id_" + id)) {
            sender.sendMessage(StaffPrefix + "§4Ban id doesn't exist!");
            return false;
        }


        try {
            kickMsg = args[2];
        }catch (ArrayIndexOutOfBoundsException e) {
            kickMsg = getBan(id)[1];
        }


        if (target == null) {
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            banPlayer(player.getName(), target, kickMsg, id);

        }else banPlayer("Console", target, kickMsg, id);



        return false;
    }

    public void banPlayer(String banPerformedBy, Player playerToBan, String message, int id) {
        Bukkit.getServer().getBanList(BanList.Type.NAME).addBan(playerToBan.getName(), getBan(id)[1], stringToDate(Integer.parseInt(getBan(id)[2]), getBan(id)[3]), banPerformedBy);
        playerToBan.kickPlayer(message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return null;
    }
    public String[] getBan(int id) {

        String reason = It.getInstance().getConfig().getString("ban.reason.ban_id_" + id + ".reason");
        String name = It.getInstance().getConfig().getString("ban.reason.ban_id_" + id + ".name");
        int duration = It.getInstance().getConfig().getInt("ban.reason.ban_id_" + id + ".duration");
        String unit = It.getInstance().getConfig().getString("ban.reason.ban_id_" + id + ".time_unit");
        return new String[]{name, reason, String.valueOf(duration), unit};
    }

    public Date stringToDate(int time, String unit) {
        long multi = 0;
        if (unit.equalsIgnoreCase("h")) multi = (1000L * 60 * 60 * time);
        if (unit.equalsIgnoreCase("d")) multi = (1000L * 60 * 60 * 24 * time);
        if (unit.equalsIgnoreCase("w")) multi = (1000L * 60 * 60 * 24 * 7 * time);
        if (unit.equalsIgnoreCase("m")) multi = (1000L * 60 * 60 * 24 * 7 * 4 * time);
        if (unit.equalsIgnoreCase("y")) multi = (1000L * 60 * 60 * 24 * 7 * 4 * 12 * time);

        return new Date(System.currentTimeMillis() + (multi));
    }
}
