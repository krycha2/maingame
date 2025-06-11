package org.naingame.maingame.system;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.naingame.maingame.Maingame;

import java.io.File;
import java.util.*;

public class CustomItemLoader {

    // Odwołanie do głównej klasy pluginu (żeby mieć dostęp do folderu pluginu itd.)
    protected final Maingame plugin;

    // Mapa wszystkich załadowanych przedmiotów – klucz to ID z pliku YAML
    private final Map<String, ItemStack> items = new HashMap<>();

    public CustomItemLoader(Maingame plugin) {
        this.plugin = plugin;
        loadItems(); // Od razu ładujemy itemy przy tworzeniu
    }

    /**
     * Ładuje plik items.yml i parsuje przedmioty do pamięci
     */
    private void loadItems() {
        File file = new File(plugin.getDataFolder(), "items.yml");

        // Jeśli plik nie istnieje – kopiujemy go z JARa
        if (!file.exists()) {
            plugin.saveResource("items.yml", false);
        }

        // Ładujemy plik jako YAML
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Przechodzimy do sekcji "items" w pliku
        ConfigurationSection section = config.getConfigurationSection("items");
        if (section == null) return;

        // Dla każdego itemka w YAMLu (np. "blaze_rod_flame")
        for (String key : section.getKeys(false)) {
            ConfigurationSection itemSec = section.getConfigurationSection(key);
            if (itemSec == null) continue;

            // Pobieramy materiał (np. BLAZE_ROD)
            Material material = Material.valueOf(itemSec.getString("material"));
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                // Ustawiamy nazwę przedmiotu (np. "&6Zapieczona Pałka Blaze’a")
                meta.setDisplayName(itemSec.getString("name"));

                // Ustawiamy lore (opis pod nazwą)
                List<String> lore = itemSec.getStringList("lore");
                meta.setLore(lore);

                // Enchanty (np. FIRE_ASPECT: 2)
                ConfigurationSection enchSec = itemSec.getConfigurationSection("enchants");
                if (enchSec != null) {
                    for (String enchKey : enchSec.getKeys(false)) {
                        Enchantment ench = Enchantment.getByName(enchKey); // np. FIRE_ASPECT /// !!! przestarzała metoda
                        int level = enchSec.getInt(enchKey); // np. 2

                        if (ench != null) {
                            meta.addEnchant(ench, level, true); // Dodaj enchant
                        }
                    }
                }

                // Czy item ma być niezniszczalny
                meta.setUnbreakable(itemSec.getBoolean("unbreakable", false));

                // Ustawiamy meta do itemka
                item.setItemMeta(meta);
            }

            // Zapisujemy item pod jego ID (np. "blaze_rod_flame")
            items.put(key, item);
        }
    }

    /**
     * Pobiera item po jego ID z pliku YAML.
     * Zwraca kopię itema, więc można go spokojnie modyfikować.
     */
    public ItemStack get(String id) {
        ItemStack item = items.get(id);
        return item == null ? null : item.clone(); // clone() żeby nie zepsuć oryginału
    }

    /**
     * Zwraca wszystkie dostępne ID z pliku items.yml
     */
    public Set<String> getAllIds() {
        return items.keySet();
    }
}
