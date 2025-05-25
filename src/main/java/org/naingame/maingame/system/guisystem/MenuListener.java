package org.naingame.maingame.system.guisystem;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.naingame.maingame.Maingame;

/**
 * Nasłuchiwacz obsługujący zdarzenia związane z GUI menu
 */
public class MenuListener implements Listener {

    /**
     * Obsługa kliknięcia w GUI
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Player dude = (Player) event.getWhoClicked();
        final int slot = event.getSlot();

        //System.out.println("Clicking in menu! Has metadata ? " + dude.hasMetadata("CoolMenuPlugin"));

        // Jeśli gracz ma otwarte nasze menu
        if (dude.hasMetadata("CoolMenuPlugin")) {
            final Menu menu = (Menu) dude.getMetadata("CoolMenuPlugin").get(0).value();

            //System.out.println("Clicked " + slot + " in menu " + menu);

            // Szukamy przycisku przypisanego do slotu
            for (final Button button : menu.getButtons())
                if (button.getSlot() == slot) {
                    //System.out.println("Found clickable slot " + button.getSlot() + " with item " + button.getItem());

                    // Wykonaj akcję kliknięcia
                    button.onClick(dude);
                    event.setCancelled(true); // Zablokuj domyślną interakcję
                }
        }
    }

    /**
     * Obsługa zamknięcia GUI - usuwa metadane
     */
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        final Player dude = (Player) event.getPlayer();

        if (dude.hasMetadata("CoolMenuPlugin")) {
            System.out.println("Removing menu metadata.");
            dude.removeMetadata("CoolMenuPlugin", Maingame.getInstance());
        }
    }

    /**
     * Obsługa wyjścia gracza - TODO: zadanie domowe
     */
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        // Do zaimplementowania: można tu np. usuwać metadane, jeśli menu było otwarte
    }
}
