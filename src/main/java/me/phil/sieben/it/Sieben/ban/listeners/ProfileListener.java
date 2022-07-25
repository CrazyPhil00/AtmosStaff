package me.phil.sieben.it.Sieben.ban.listeners;

import me.phil.sieben.it.Sieben.ban.inventorys.PlayerProfile;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;

import java.io.IOException;
import java.util.HashMap;

import static me.phil.sieben.it.Sieben.It.*;


public class ProfileListener implements Listener {

    HashMap<Player, String[]> chatLock = new HashMap<>();

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;

        if (event.getView().getTitle().equalsIgnoreCase("       §1§lPlayer§9§lTeleporter")) {
            event.setCancelled(true);

            Player clickedPlayer = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getLocalizedName());

            PlayerProfile.openPlayerProfile(player, clickedPlayer);
            /*
            Location playerLoc = clickedPlayer.getLocation();

            player.teleport(playerLoc);
            */

        }


        if (event.getView().getTitle().equalsIgnoreCase("Player Profile")) {
            event.setCancelled(true);
            Player clickedPlayer = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getLocalizedName());

            if (event.getCurrentItem().getType() == Material.ENDER_PEARL) //TP
                player.teleport(clickedPlayer.getLocation());


            if (event.getCurrentItem().getType() == Material.ORANGE_DYE) //KICK
                chatLock.put(player, new String[] {clickedPlayer.getName(), "KICK"});


            if (event.getCurrentItem().getType() == Material.BARRIER) //BAN
                chatLock.put(player, new String[] {clickedPlayer.getName(), "BAN"});



            if (event.getCurrentItem().getType() == Material.BOOK) { //NOTE
                if (event.isLeftClick()) {
                    //Add note
                    String notes = noteCfg.getString(clickedPlayer.getUniqueId().toString());
                    if (notes != null) {
                        TextComponent override, add;
                        override = new TextComponent();
                        override.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "ovr"));
                        override.setText(" §7[§6§oovr§7]");

                        add = new TextComponent();
                        add.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "add"));
                        add.setText("§7[§6§oadd§r§7]");

                        chatLock.put(player, new String[]{clickedPlayer.getName(), "ASK_FOR_NOTE_OVERRIDE"});
                        player.sendMessage(StaffPrefix + "§4Player has already notes.\n" +
                                "Do you want to override or add your note to the already existed notes? ");
                        player.spigot().sendMessage(add, override);
                    }
                    else {
                        chatLock.put(player, new String[]{clickedPlayer.getName(), "NOTE"});
                        player.sendMessage(StaffPrefix + "§6Write note in chat.");
                    }
                }
                if (event.isRightClick()) {
                    //Read note

                    if (!noteCfg.isSet(clickedPlayer.getUniqueId().toString())) player.sendMessage(StaffPrefix + "§4Player has no Notes");
                    else {
                        String notes = noteCfg.getString(clickedPlayer.getUniqueId().toString());
                        player.sendMessage(StaffPrefix + notes);
                    }
                }
            }

            player.closeInventory();
        }
    }

    @EventHandler
    public void onChatEvent(PlayerChatEvent event) throws IOException {
        Player player = event.getPlayer();

        if (chatLock.containsKey(player)) {
            Player playerToAction = Bukkit.getPlayer(chatLock.get(player)[0]);
            String action = chatLock.get(player)[1];
            if (action.equalsIgnoreCase("BAN")) {
                String reason = event.getMessage();
                playerToAction.kickPlayer(reason);
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ban " + playerToAction.getName() + " " + reason);
                chatLock.remove(player);

            }else if (action.equalsIgnoreCase("KICK")) {
                String reason = event.getMessage();
                playerToAction.kickPlayer(reason);
                chatLock.remove(player);

            }else if (action.equalsIgnoreCase("NOTE")) {
                String note = event.getMessage();
                note = "§6by " + player.getName() + ": §r" + note;
                noteCfg.set(playerToAction.getUniqueId().toString(), note);
                noteCfg.save(noteFile);
                chatLock.remove(player);
                player.sendMessage(StaffPrefix + "§6Note set");

            }else if (action.equalsIgnoreCase("ASK_FOR_NOTE_OVERRIDE")) {
                String answer = event.getMessage();
                if (answer.equalsIgnoreCase("ovr")) {
                    chatLock.remove(player);
                    chatLock.put(player, new String[]{playerToAction.getName(), "NOTE"});
                    player.sendMessage(StaffPrefix + "§6Write note in chat.");

                }else if (answer.equalsIgnoreCase("add")) {
                    chatLock.remove(player);
                    chatLock.put(player, new String[]{playerToAction.getName(), "NOTE_ADD"});
                    player.sendMessage(StaffPrefix + "§6Write note in chat.");

                }else {
                    player.sendMessage(StaffPrefix + "§oWhat? §cDo you want to override or add your note to the already existed note.");
                    TextComponent override, add;
                    override = new TextComponent();
                    override.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "ovr"));
                    override.setText(" §7[§6§oovr§7]");

                    add = new TextComponent();
                    add.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "add"));
                    add.setText("§7[§6§oadd§r§7]");
                    player.spigot().sendMessage(add, override);

                    chatLock.put(player, new String[]{playerToAction.getName(), "ASK_FOR_NOTE_OVERRIDE"});
                }
            }else if (action.equalsIgnoreCase("NOTE_ADD")) {
                String oldNote = noteCfg.getString(playerToAction.getUniqueId().toString());
                String newNote = event.getMessage();

                String combinedNote = oldNote + " §r§7<|> §6by " + player.getName() + ": §r" + newNote;
                noteCfg.set(playerToAction.getUniqueId().toString(), combinedNote);
                noteCfg.save(noteFile);
                player.sendMessage(StaffPrefix + "§6Updated Note");

                chatLock.remove(player);

            }
            event.setCancelled(true);

        }
    }

}
