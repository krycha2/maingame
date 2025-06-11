package org.naingame.maingame.gui.undergui.playerprofile;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.naingame.maingame.gui.MainGui;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

import java.util.Arrays;
import java.util.List;


public class MainUpgradeGui extends Menu {

    public MainUpgradeGui(){
        this.setTitle(plugin.getLangManager().get("messages.gui.mianupgradegui.title"));
        this.setSize(9*4);

        this.addButton(new Button(10) {
            private int level = data.getPerkLevel(uuid, "fish");
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.FISHING_ROD);
                ItemMeta meta = item.getItemMeta();
                if (meta != null){
                    meta.setDisplayName(plugin.getLangManager().get("messages.messages.gui.mianupgradegui.fish_upgrade", level));
                    double bonus = data.getTotalPerkBonus(uuid, "fish");
                    double newbonus = bonus + 0.005;
                    List<String> lore = Arrays.asList(plugin.getLangManager().get("messages.gui.mianupgradegui.fish_upgrade_lore", bonus, newbonus).split("\n"));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
                return item;
            }

            @Override
            public void onClick(Player player) {
                // Dodajemy +0.02 do mozlwosci złownia przedmiotu
                data.addPerkBonus(uuid, "fish", 0.005, "upgrade");
                data.addPerkLevel(uuid, "fish", 1);
                player.sendMessage("Zwiększyłeś poziom ulepszenia wędki do " + level);
                // Odświeżenie GUI (czyli otwarcie nowego egzemplarza)
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
