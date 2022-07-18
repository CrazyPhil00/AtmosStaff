package me.phil.sieben.it.Sieben.inventorys;

import me.phil.sieben.it.Sieben.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class PlayerProfile {

    public static void openPlayerProfile(Player player, Player profileFrom) {
        String PlayerName = profileFrom.getName();

        Inventory inv = Bukkit.createInventory(null, 3*9, "Player Profile");

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwnerProfile(profileFrom.getPlayerProfile());
        skullMeta.setLocalizedName(PlayerName);
        playerHead.setItemMeta(skullMeta);

        inv.setItem(4, playerHead); // Player Head

        inv.setItem(10, new ItemBuilder(Material.BARRIER).setDisplayname("§4Ban").setLocalizedName(PlayerName).build()); // Ban
        inv.setItem(11, new ItemBuilder(Material.ORANGE_DYE).setDisplayname("§6Kick").setLocalizedName(PlayerName).build()); // Kick

        inv.setItem(13, new ItemBuilder(Material.BOOK).setDisplayname("§9Notes").setLocalizedName(PlayerName).setLore("§7LeftClick to add note", "§7RightClick to read note").build()); // Player Notes

        inv.setItem(16, new ItemBuilder(Material.ENDER_PEARL).setDisplayname("§5Tp").setLocalizedName(PlayerName).build()); // TP to Player


        player.openInventory(inv);
    }
}
