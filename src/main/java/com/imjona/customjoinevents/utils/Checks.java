package com.imjona.customjoinevents.utils;

import com.imjona.customjoinevents.CustomJoinEvents;
import com.imjona.customjoinevents.commands.HelpCommand;
import com.imjona.customjoinevents.manager.commands.CommandManager;
import com.imjona.customjoinevents.manager.update.UpdateChecker;
import com.imjona.customjoinevents.menus.MainMenu;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;

import java.util.List;

import static com.imjona.customjoinevents.utils.UtilsPlugin.*;

public class Checks {
    private final CustomJoinEvents plugin;

    public Checks(CustomJoinEvents plugin) {
        this.plugin = plugin;
        registerCommand();
        checkDependencyPlaceholderAPI();
        registerEvents();
        updateChecker();
    }

    private void registerCommand() {
        CommandManager.createCoreCommand(
                "customjoinevents",
                "The main command",
                "/customjoinevents",
                List.of("cj", "cje"),
                HelpCommand.class);
    }

    private void checkDependencyPlaceholderAPI() {
        if (getPlugin("PlaceholderAPI")) {
            sendMessageToConsole("&7PlaceholderAPI support: &atrue");
        } else {
            sendMessageToConsole("&7PlaceholderAPI support: &cfalse");
        }
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new MainMenu(plugin), plugin);
    }

    private void updateChecker() {
        FileConfiguration config = plugin.getConfig();
        if (config.getBoolean("check_updates")) {
            UpdateChecker uc = new UpdateChecker(plugin, 91288);
            if (uc.requestIsValid()) {
                if (!uc.isRunningLatestVersion()) {
                    sendMessageToConsole(prefix + "&7You are using version &b" + uc.getVersion() + "&7 and the latest version is &a" + uc.getLatestVersion());
                    sendMessageToConsole(prefix + "&7You can download the latest version at: &b" + uc.getSpigotResource().getDownloadUrl());
                }
            } else {
                sendMessageToConsole("&7Could not verify if you are using the latest version of &bCustomJoin&fEvents&7!");
            }
        }
    }
}
