package me.phil.sieben.it.Sieben;

import me.phil.sieben.it.Sieben.ban.commands.*;
import me.phil.sieben.it.Sieben.ban.listeners.BanListener;
import me.phil.sieben.it.Sieben.ban.listeners.MuteListener;
import me.phil.sieben.it.Sieben.ban.listeners.ProfileListener;
import me.phil.sieben.it.Sieben.database.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

public final class It extends JavaPlugin {

    static It instance;

    public static File noteFile, banFile;
    public static FileConfiguration noteCfg, banCfg;

    public static String StaffPrefix = "§7[§1§lAtmos§9§lStaff§7]§r ";

    @Override
    public void onEnable() {
        // Plugin startup logic

        try {
            MySQL mySQL = new MySQL("localhost", "root", "", "AtmosStaff", 3306);
            mySQL.openConnection();
            mySQL.createReport(UUID.randomUUID(), UUID.randomUUID(), "Hacker");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        instance = this;

        noteFile = new File("plugins/AtmosStaff", "notes.yml");
        noteCfg = YamlConfiguration.loadConfiguration(noteFile);

        banFile = new File("plugins/AtmosStaff", "bans.yml");
        banCfg = YamlConfiguration.loadConfiguration(banFile);

        if (!noteFile.exists() || !banFile.exists()) {
            try {
                noteCfg.save(noteFile);
                banCfg.save(banFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        saveDefaultConfig();
        StaffPrefix = Objects.requireNonNull(getConfig().getString("prefix")).replace("&", "§");

        Objects.requireNonNull(getCommand("staff")).setExecutor(new StaffCommand());
        Objects.requireNonNull(getCommand("ban")).setTabCompleter(new BanCommand());
        Objects.requireNonNull(getCommand("ban")).setExecutor(new BanCommand());
        Objects.requireNonNull(getCommand("clearchat")).setExecutor(new ChatClearCommand());
        Objects.requireNonNull(getCommand("createban")).setExecutor(new CreateBanCommand());
        Objects.requireNonNull(getCommand("mute")).setExecutor(new MuteCommand());

        Bukkit.getPluginManager().registerEvents(new BanListener(), this);

        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);

        Bukkit.getPluginManager().registerEvents(new MuteListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static It getInstance() {
        return instance;
    }

    public static void addBan() {

    }

    public static void removeBan() {
        
    }
}
