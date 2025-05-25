package org.naingame.maingame.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

public class MyCoolMenu extends Menu {

    public MyCoolMenu() {
        this.setTitle("Moje Super Menu");
        this.setSize(9 * 3); // 3 rzędy (27 slotów)

        // Przycisk 1 - Złote jabłko
        this.addButton(new Button(10) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD + "Złote Jabłko");
                item.setItemMeta(meta);
                return item;
            }

            @Override
            public void onClick(Player player) {
                player.sendMessage(ChatColor.GREEN + "Kliknąłeś Złote Jabłko!");
            }
        });

        // Przycisk 2 - TNT
        this.addButton(new Button(13) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.TNT);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "BOOM!");
                item.setItemMeta(meta);
                return item;
            }

            @Override
            public void onClick(Player player) {
                player.sendMessage(ChatColor.RED + "BOOM! Kliknąłeś TNT!");
            }
        });

        // Przycisk 3 - Diament
        this.addButton(new Button(16) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.DIAMOND);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA + "Diament");
                item.setItemMeta(meta);
                return item;
            }

            @Override
            public void onClick(Player player) {
                player.sendMessage(ChatColor.AQUA + "Kliknąłeś Diament!");
            }
        });
    }
}
