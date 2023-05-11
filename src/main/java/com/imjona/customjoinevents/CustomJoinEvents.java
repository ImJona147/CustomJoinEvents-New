package com.imjona.customjoinevents;

import com.imjona.customjoinevents.manager.lang.LangManager;
import com.imjona.customjoinevents.utils.Checks;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

import static com.imjona.customjoinevents.utils.UtilsPlugin.sendMessageToConsole;

public final class CustomJoinEvents extends JavaPlugin {
    private static CustomJoinEvents instance;
    private final PluginDescriptionFile file = getDescription();
    private final String version = file.getVersion();

    //Archivos de configuración
    private LangManager langManager;

    @Override
    public void onEnable() {
        instance = this;
        sendMessageToConsole("&7Enabling &bCustomJoin&fEvents &7v&a" + this.version + "&7 by &6_ImJona");
        loadConfig();
        this.langManager = new LangManager(this, getConfig().getString("language"));
        new Checks(this);
        sendMessageToConsole("&7Get support at my discord: &b_ImJona#2065");
    }

    @Override
    public void onDisable() {
        sendMessageToConsole("&7Disabling &bCustomJoin&fEvents &7v&a" + this.version + "&7 by &6_ImJona");

        sendMessageToConsole("&7Get support at my discord: &b_ImJona#2065");
    }

    private void loadConfig() {
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveResource("config.yml", false);
        }
        try {
            getConfig().load(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static CustomJoinEvents get() {
        return instance;
    }

    public String getVersion() {
        return version;
    }

    public LangManager getLangManager() {
        return langManager;
    }
}
