package me.phil.sieben.it.Sieben.commands.ban;

import me.phil.sieben.it.Sieben.It;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

import static me.phil.sieben.it.Sieben.It.StaffPrefix;


public class CreateBan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("staff.ban.create")) {
            // ARGS: /createban [id] [name] [reason] [duration] [unit]

            if (args.length < 5) {
                sender.sendMessage("HELP");
                return false;
            }

            int id = Integer.parseInt(args[0]);
            String name = args[1];
            String reason = args[2];
            int duration = Integer.parseInt(args[3]);
            String unit = args[4];
            String config = "ban.reason.ban_id_" + id;

            It.getInstance().getConfig().set(config + ".name", name);
            It.getInstance().getConfig().set(config + ".reason", reason);
            It.getInstance().getConfig().set(config + ".duration", duration);
            It.getInstance().getConfig().set(config + ".time_unit", unit);

            try {
                It.getInstance().getConfig().save("plugins/AtmosStaff/config.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else sender.sendMessage(StaffPrefix + "§4You don't have permission to create a ban!");

        return false;
    }
}
