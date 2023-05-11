package com.imjona.customjoinevents.manager.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem {
    private final ItemStack itemStack;
    private final String[] actions;

    public MenuItem(ItemStack itemStack, String[] actions) {
        this.itemStack = itemStack;
        this.actions = actions;
    }

    public MenuItem(ItemStack item) {
        this.itemStack = item;
        this.actions = null;
    }

    public abstract void onClick(Player player);

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String[] getActions() {
        return actions;
    }

    public void executeActions(Player player) {
        if (actions != null) {
            for (String action : actions) {
                String[] parts = action.split(":");
                String actionType = parts[0];
                String actionData = parts.length > 1 ? parts[1] : "";

                switch (actionType) {
                    case "message":
                        player.sendMessage(actionData);
                        break;
                    case "command":
                        if (actionData.startsWith("/")) {
                            player.performCommand(actionData.substring(1));
                        } else {
                            player.performCommand(actionData);
                        }
                        break;
                    case "console":
                        player.getServer().dispatchCommand(player.getServer().getConsoleSender(), actionData);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
