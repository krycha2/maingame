package org.naingame.maingame.gui.undergui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.naingame.maingame.gui.MainGui;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

import java.util.Arrays;

public class MainEq extends Menu {
    public MainEq(){
        this.setTitle(plugin.getLangManager().get("messages.gui.maineq.title"));
        this.setSize(9*6);

        // wypełnienie pustych slotów
        ItemStack filler = new ItemStack(Material.CYAN_STAINED_GLASS_PANE);
        ItemMeta meta =  filler.getItemMeta();
        meta.setDisplayName(" ");
        filler.setItemMeta(meta);

        // sloty wepnione szkłem
        fillSlots(Arrays.asList(
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,50,51,52
        ), filler);

        // prycisk powrotu do main gui
        this.addButton(new Button(49) {
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
