package com.imjona.customjoinevents.menus;

import com.imjona.customjoinevents.CustomJoinEvents;
import com.imjona.customjoinevents.manager.menu.Menu;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class MainMenu implements Listener {
    private final CustomJoinEvents plugin;
    private final Map<Player, Menu> menus = new HashMap<>();

    public MainMenu(CustomJoinEvents plugin) {
        this.plugin = plugin;
    }

    public void mainMenu(Player player) {
        FileConfiguration config = plugin.getConfig();
        Menu mainMenu = new Menu(config.getString("Menu.title_menu"), 4);

        menus.put(player, mainMenu);
        mainMenu.open(player);
    }
}
