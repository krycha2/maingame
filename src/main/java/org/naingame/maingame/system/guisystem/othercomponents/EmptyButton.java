package org.naingame.maingame.system.guisystem.othercomponents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.naingame.maingame.system.guisystem.Button;

public class EmptyButton extends Button {
    private final ItemStack item;

    public EmptyButton(int slot, ItemStack item) {
        super(slot);
        this.item = item;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void onClick(Player player) {
        // Nic nie rób
    }
}
