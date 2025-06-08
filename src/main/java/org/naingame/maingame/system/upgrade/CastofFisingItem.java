package org.naingame.maingame.system.upgrade;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.naingame.maingame.Maingame;

public class CastofFisingItem implements Listener {
    // Metoda, która zostanie wywołana, gdy gracz coś złowi
    protected final Maingame plugin = Maingame.getInstance();

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        // Sprawdzamy, czy coś zostało złowione
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();

            // Szansa na to, że w ogóle coś specjalnego dropnie – np. 5%
            double dropChance = 0.05;

            if (Math.random() < dropChance) {
                // Ustalmy szansę na podwojony drop – np. 20%
                double doubleDropChance = 0.20;

                // Domyślnie dajemy 1 Blaze Roda
                int amount = 1;

                ItemStack blaze = plugin.getItemLoader().get("blaze_rod_flame");
                // Jeśli trafimy na podwójny drop – dajemy 2
                if (Math.random() < doubleDropChance) {
                    amount = 2;
                }

                blaze.setAmount(amount);
                // Dodajemy graczowi do EQ
                player.getInventory().addItem(blaze);

                // Info dla gracza
                if (amount == 2) {
                    player.sendMessage("💥 Co za fart! Złowiłeś aż *dwie* pałki Blaze'a!");
                } else {
                    player.sendMessage("🔥 Złowiłeś pałkę Blaze'a!");
                }
            }
        }

    }
}
/*
COD – dorsz
SALMON – łosoś
TROPICAL_FISH – ryba tropikalna
PUFFERFISH – rozdymka
@EventHandler
public void onFish(PlayerFishEvent event) {
    if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
        Player player = event.getPlayer();

        // Pobieramy co zostało złowione
        if (event.getCaught() instanceof Item) {
            Item itemEntity = (Item) event.getCaught(); // to jest byt w świecie (item leci do gracza)
            ItemStack itemStack = itemEntity.getItemStack(); // faktyczny przedmiot
            Material caughtMaterial = itemStack.getType(); // typ złowionego itemu

            // Sprawdzamy czy to SALMON
            if (caughtMaterial == Material.SALMON) {
                // Szansa na drop specjalny (np. 5%)
                double dropChance = 0.05;
                if (Math.random() < dropChance) {
                    double doubleDropChance = 0.20;
                    int amount = (Math.random() < doubleDropChance) ? 2 : 1;

                    ItemStack blazeRod = new ItemStack(Material.BLAZE_ROD, amount);
                    player.getInventory().addItem(blazeRod);

                    if (amount == 2) {
                        player.sendMessage("💥 Co za fart! Z łososia wypadły *dwie* pałki Blaze'a!");
                    } else {
                        player.sendMessage("🔥 Z łososia wypadła pałka Blaze'a!");
                    }
                }
            }
        }
    }
}
*/
