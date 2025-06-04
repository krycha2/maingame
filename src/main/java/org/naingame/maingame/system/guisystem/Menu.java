package org.naingame.maingame.system.guisystem;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.naingame.maingame.Maingame;
import org.naingame.maingame.system.guisystem.othercomponents.EmptyButton;
import org.naingame.maingame.system.playerdata.PlayerDataManager;

/**
 * Klasa reprezentująca menu GUI dla gracza
 */
public class Menu {

    private final List<Button> buttons = new ArrayList<>();

    protected final Maingame plugin = Maingame.getInstance();

    protected final PlayerDataManager data = Maingame.getInstance().getDataManager();

    private int size = 9 * 3;
    private String title = "Custom Menu";

    private final Menu parent;
    private boolean extraButtonsRegistered = false;

    public Menu() {
        this(null);
    }

    public Menu(@Nullable Menu parent) {
        this.parent = parent;
    }

    public final List<Button> getButtons() {
        return buttons;
    }

    protected final void addButton(Button button) {
        this.buttons.add(button);
    }

    protected final void setSize(int size) {
        this.size = size;
    }

    protected final void setTitle(String title) {
        this.title = title;
    }

    /**
     * Wypełnia puste sloty menu podanym przedmiotem (np. szklanym panelem jako ozdoba).
     * Przyciskami się nie przejmuje – tylko wolne sloty.
     */
    /**
     * Wypełnia podane sloty dekoracyjnym przedmiotem (np. szkłem).
     * @param slots Lista indeksów slotów do wypełnienia
     * @param fillerItem Przedmiot używany jako dekoracja
     */
    protected final void fillSlots(List<Integer> slots, ItemStack fillerItem) {
        for (int slot : slots) {
            boolean slotTaken = false;
            for (Button button : buttons) {
                if (button.getSlot() == slot) {
                    slotTaken = true;
                    break;
                }
            }
            if (!slotTaken)
                this.addButton(new EmptyButton(slot, fillerItem));
        }
    }

    /**
     * Wyświetla GUI graczowi
     */
    public final void displayTo(Player player) {
        final Inventory inventory = Bukkit.createInventory(player, this.size,
                ChatColor.translateAlternateColorCodes('&', this.title));

        // Umieszcza wszystkie przyciski w odpowiednich slotach
        for (final Button button : this.buttons)
            inventory.setItem(button.getSlot(), button.getItem());

        // Tworzy przycisk "Powrót" (np. ostatni slot)
        if (this.parent != null && !this.extraButtonsRegistered) {
            this.extraButtonsRegistered = true;

            final Button returnBackButton = new Button(this.size - 1) {
                @Override
                public ItemStack getItem() {
                    final ItemStack item = new ItemStack(Material.OAK_DOOR);
                    final ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.WHITE + "Return Back");
                    item.setItemMeta(meta);
                    return item;
                }

                @Override
                public void onClick(Player player) {
                    try {
                        final Menu newMenuInstance = parent.getClass().getConstructor().newInstance();
                        newMenuInstance.displayTo(player);
                    } catch (final ReflectiveOperationException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            this.buttons.add(returnBackButton);
            inventory.setItem(returnBackButton.getSlot(), returnBackButton.getItem());
        }

        // Zamknij stare GUI jeśli było
        if (player.hasMetadata("CoolMenuPlugin"))
            player.closeInventory();

        // Oznacza gracza metadanymi, że ma otwarte menu
        player.setMetadata("CoolMenuPlugin", new FixedMetadataValue(Maingame.getInstance(), this));

        System.out.println("Opening inventory");
        player.openInventory(inventory);
    }

    /**
     * Prywatna klasa reprezentująca dekoracyjny przycisk (np. szkło),
     * który nic nie robi po kliknięciu.
     */
    private static class DecorativeButton extends Button {
        private final ItemStack item;

        public DecorativeButton(int slot, ItemStack item) {
            super(slot);
            this.item = item;
        }

        @Override
        public ItemStack getItem() {
            return item;
        }

        @Override
        public void onClick(Player player) {
            // Nic nie robi – tylko dekoracja
        }
    }
}
