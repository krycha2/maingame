package org.naingame.maingame.gui.undergui.playerprofile;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.naingame.maingame.system.guisystem.Button;
import org.naingame.maingame.system.guisystem.Menu;

public class MainUpgradeGui extends Menu {
    public MainUpgradeGui(){
        this.setTitle("upgrade");
        this.setSize(9*4);

        this.addButton(new Button(10) {
            @Override
            public ItemStack getItem() {
                ItemStack item = new ItemStack(Material.FISHING_ROD);

                return null;
            }

            @Override
            public void onClick(Player player) {

            }
        });
    }
}
