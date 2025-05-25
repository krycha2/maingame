package org.naingame.maingame.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.naingame.maingame.gui.eq.MainEq;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

public class MainGui extends Menu {
    public MainGui(){
        this.setTitle("Test");
        this.setSize(9*4);

        this.addButton(new Button(11) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.GOLD_INGOT);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GOLD + "Sklep");
                item.setItemMeta(meta);
                return item;
            }

            @Override
            public void onClick(Player player) {
                System.out.println("SKLEP");
            }
        });

        this.addButton(new Button(13) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.WHITE_BUNDLE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.WHITE + "Ekwipunek");
                item.setItemMeta(meta);
                return item;
            }

            @Override
            public void onClick(Player player) {
                new MainEq().displayTo(player);
            }
        });
    }
}
