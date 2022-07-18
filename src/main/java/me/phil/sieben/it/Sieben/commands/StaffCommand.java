package me.phil.sieben.it.Sieben.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class StaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        Inventory inv = Bukkit.createInventory(null, 5*9, "       §1§lPlayer§9§lTeleporter");

        int i = 0;
        for (Player p: Bukkit.getOnlinePlayers()) {
            if (p != player) {

                ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                skullMeta.setOwnerProfile(p.getPlayerProfile());
                skullMeta.setLocalizedName(p.getName());
                itemStack.setItemMeta(skullMeta);

                inv.setItem(i, itemStack);
                i++;
            }
        }

        player.openInventory(inv);
        return false;
    }
}
