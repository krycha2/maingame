package org.naingame.maingame.gui.undergui.playerprofile;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

public class MainPlayerProfile extends Menu {
    public MainPlayerProfile() {
        this.setTitle("Profile");
        this.setSize(9*3);

        this.addButton(new Button(10) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "UPGRADE");
                return item;
            }

            @Override
            public void onClick(Player player) {
                new MainUpgradeGui().displayTo(player);
            }
        });
    }
}
