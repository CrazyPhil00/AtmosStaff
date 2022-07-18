package me.phil.sieben.it.Sieben;

import me.phil.sieben.it.Sieben.commands.StaffCommand;
import me.phil.sieben.it.Sieben.listeners.ProfileListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class It extends JavaPlugin {

    static It instance;

    public static File noteFile;
    public static FileConfiguration noteCfg;

    public static String StaffPrefix = "§7[§1§lAtmos§9§lStaff§7]§r ";

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        noteFile = new File("plugins/AtmosStaff", "notes.yml");
        noteCfg = YamlConfiguration.loadConfiguration(noteFile);

        if (!noteFile.exists()) {
            try {
                noteCfg.save(noteFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        saveDefaultConfig();
        StaffPrefix = getConfig().getString("prefix").replace("&", "§");

        getCommand("staff").setExecutor(new StaffCommand());

        Bukkit.getPluginManager().registerEvents(new ProfileListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static It getInstance() {
        return instance;
    }
}
