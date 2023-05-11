package com.imjona.customjoinevents.manager.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.imjona.customjoinevents.utils.UtilsPlugin.fixColorMessages;

public class Menu {
    private final String title;
    private final int rows;
    private final List<MenuItem> items;
    private int page;
    private Inventory inventory;
    private final int itemsPerPage = 14;
    private final ItemStack PREVIOUS_PAGE_ITEM = new ItemStack(Material.ARROW);
    private final ItemStack NEXT_PAGE_ITEM = new ItemStack(Material.ARROW);

    public Menu(String title, int rows) {
        this.title = title;
        this.rows = rows;
        this.items = new ArrayList<>();
        this.page = 0;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void open(Player player) {
        String titleWithColors = fixColorMessages(title);
        inventory = Bukkit.createInventory(null, rows * 9, titleWithColors + " - Página " + (page + 1));

        int startIndex = page * (rows * 9);
        int endIndex = Math.min((page + 1) * (rows * 9), items.size());

        for (int i = startIndex; i < endIndex; i++) {
            MenuItem item = items.get(i);
            inventory.setItem(i - startIndex, item.getItemStack());
        }
        player.openInventory(inventory);
    }

    public void updateInventory() {
        inventory.clear();

        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, items.size());

        for (int i = startIndex; i < endIndex; i++) {
            MenuItem item = items.get(i);
            inventory.setItem(i - startIndex, item.getItemStack());
        }

        // Add pagination controls to the bottom of the menu
        int pages = 10;
        if (page > 0) {
            inventory.setItem(rows * 9 - 9, PREVIOUS_PAGE_ITEM);
        }
        if (page < pages - 1) {
            inventory.setItem(rows * 9 - 1, NEXT_PAGE_ITEM);
        }
    }


    public void handleClick(int slot, Player player) {
        int index = (page * (rows * 9)) + slot;

        if (index >= 0 && index < items.size()) {
            MenuItem item = items.get(index);
            item.onClick(player);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public int getRows() {
        return rows;
    }

    public int getPage() {
        return page;
    }

    public String getTitle() {
        return title;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }
}

