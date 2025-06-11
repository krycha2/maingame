package org.naingame.maingame.system.upgrade;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.naingame.maingame.Maingame;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CastofFisingItem implements Listener {
    // Metoda, która zostanie wywołana, gdy gracz coś złowi
    protected final Maingame plugin = Maingame.getInstance();

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        // Sprawdzamy, czy coś zostało złowione
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();

            if (event.getCaught() instanceof Item) {
                Item itemEntity = (Item) event.getCaught(); // to jest byt w świecie (item leci do gracza)
                ItemStack itemStack = itemEntity.getItemStack(); // faktyczny przedmiot
                Material caughtMaterial = itemStack.getType(); // typ złowionego itemu
                //COD – dorsz SALMON – łosoś TROPICAL_FISH – ryba tropikalna PUFFERFISH – rozdymka
                if (caughtMaterial == Material.SALMON) {
                    // Szansa na to, że w ogóle coś specjalnego dropnie – np. 5%
                    double dropChance = plugin.getDataManager().getTotalPerkBonus(uuid, "fish");
                    //double dropChance = 0.05;

                    if (Math.random() < dropChance) {
                        // Ustalmy szansę na podwojony drop – np. 20%
                        double doubleDropChance = plugin.getDataManager().getTotalPerkBonus(uuid, "fishdable");

                        // Domyślnie dajemy 1 Blaze Roda
                        int amount = 1;

                        ItemStack blaze = plugin.getItemLoader().get("blaze_rod_flame");
                        // Jeśli trafimy na podwójny drop – dajemy 2
                        if (Math.random() < doubleDropChance) {
                            // losuje liczby 1, 2, 3, 4, 5
                            amount = ThreadLocalRandom.current().nextInt(1, 6);
                            //amount = 2;
                        }

                        blaze.setAmount(amount);
                        // Dodajemy graczowi do EQ
                        player.getInventory().addItem(blaze);

                        // Info dla gracza
                        if (amount == 2) {
                            player.sendMessage(plugin.getLangManager().get("message.info.fishinfoaumnt", amount, blaze));
                        } else {
                            player.sendMessage(plugin.getLangManager().get("message.info.fishifo", amount));
                        }
                    } else if (caughtMaterial == Material.PUFFERFISH) {

                        // Szansa na to, że w ogóle coś specjalnego dropnie – np. 5%
                        dropChance =+ 0.02;
                        //double dropChance = 0.05;

                        if (Math.random() < dropChance) {
                            // Ustalmy szansę na podwojony drop – np. 20%
                            double doubleDropChance = plugin.getDataManager().getTotalPerkBonus(uuid, "fishdable");

                            // Domyślnie dajemy 1 Blaze Roda
                            int amount = 1;

                            ItemStack blaze = plugin.getItemLoader().get("blaze_rod_flame");
                            // Jeśli trafimy na podwójny drop – dajemy 2
                            if (Math.random() < doubleDropChance) {
                                // losuje liczby 1, 2, 3, 4, 5
                                amount = ThreadLocalRandom.current().nextInt(1, 6);
                                //amount = 2;
                            }

                            blaze.setAmount(amount);
                            // Dodajemy graczowi do EQ
                            player.getInventory().addItem(blaze);

                            // Info dla gracza
                            if (amount == 2) {
                                player.sendMessage(plugin.getLangManager().get("message.info.fishinfoaumnt", amount, blaze));
                            } else {
                                player.sendMessage(plugin.getLangManager().get("message.info.fishifo", amount));
                            }
                        }
                    }
                }
            }
        }
    }
}