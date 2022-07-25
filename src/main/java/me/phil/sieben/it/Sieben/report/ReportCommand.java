package me.phil.sieben.it.Sieben.report;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

import static me.phil.sieben.it.Sieben.It.StaffPrefix;

public class ReportCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage(StaffPrefix + "Only Player can use this command!");
        assert sender instanceof Player;
        Player player = (Player) sender;


        ArrayList<Player> onlinePlayerList = new ArrayList<>();
        for (Player arrayplayer : Bukkit.getOnlinePlayers()) {
            if (player != arrayplayer) {
                onlinePlayerList.add(arrayplayer);
            }
        }



        Inventory inv = Bukkit.createInventory(null, 6*9, "Select a Player to Report");
        if (Bukkit.getOnlinePlayers().size() > 54) {
            //More then one chest needed

            int t = 0;
            for (Player p : onlinePlayerList) {

                if (t > 43) {

                }else {
                    ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                    assert skullMeta != null;
                    skullMeta.setOwningPlayer(p);
                    itemStack.setItemMeta(skullMeta);

                    inv.setItem(t, itemStack);

                    onlinePlayerList.remove(p);

                    t++;
                }

            }


        }else {
            //One chest needed


            int t = 0;
             for (Player p : onlinePlayerList) {

                 ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                 SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                 assert skullMeta != null;
                 skullMeta.setOwningPlayer(p);
                 itemStack.setItemMeta(skullMeta);

                 inv.setItem(t, itemStack);


                 t++;

             }
        }



        return false;
    }
}
