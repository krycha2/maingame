package org.naingame.maingame.gui.undergui.playerprofile;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.naingame.maingame.gui.MainGui;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

public class MainPlayerProfile extends Menu {
    public MainPlayerProfile() {
        this.setTitle(plugin.getLangManager().get("messages.gui.mainplayerprofile.title"));
        this.setSize(9*4);

        this.addButton(new Button(10) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
                ItemMeta meta = item.getItemMeta();
                if (meta!=null){
                    meta.setDisplayName(plugin.getLangManager().get("messages.gui.mainplayerprofile.upgrade"));
                    item.setItemMeta(meta);
                }
                return item;
            }

            @Override
            public void onClick(Player player) {
                new MainUpgradeGui().displayTo(player);
            }
        });
        // prycisk powrotu do main gui
        this.addButton(new Button(31) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.BARRIER);
                ItemMeta meta =  item.getItemMeta();
                if (meta!=null){
                    meta.setDisplayName(plugin.getLangManager().get("messages.basic.back"));
                    item.setItemMeta(meta);
                }
                return item;
            }
            @Override
            public void onClick(Player player) {
                new MainGui().displayTo(player);
            }
        });
    }
}
