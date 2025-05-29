package org.naingame.maingame.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.naingame.maingame.gui.undergui.MainEq;
import org.naingame.maingame.gui.undergui.playerprofile.MainPlayerProfile;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

public class MainGui extends Menu {
    public MainGui(){
        this.setTitle("Test");
        this.setSize(9*4);

        this.addButton(new Button(10) {
            @Override
            // Profil uzytkownia
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Profil");
                return item;
            }

            @Override
            public void onClick(Player player) {
                new MainPlayerProfile().displayTo(player);
            }
        });

        this.addButton(new Button(12) {
            @Override
            // merchant mzolwsc kupienia  losowych przedmiotów
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.GOLD_INGOT);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.BOLD + "" + ChatColor.GOLD + "merchant");
                item.setItemMeta(meta);
                return item;
            }

            @Override
            public void onClick(Player player) {
                System.out.println("SKLEP");
            }
        });

        // ekwipunek gracza
        this.addButton(new Button(14) {
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

        this.addButton(new Button(16) {
            @Override
            public ItemStack getItem() {
                ItemStack item  = new ItemStack(Material.ANVIL);
                ItemMeta meta =  item.getItemMeta();
                meta.setDisplayName("CRAFTIGN");
                item.setItemMeta(meta);
                return item;
            }

            @Override
            public void onClick(Player player) {

            }
        });

    }
}
