package org.naingame.maingame.system;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;

public class LangManager {
    private final JavaPlugin plugin;
    private FileConfiguration langConfig;
    private File langFile;

    public LangManager(JavaPlugin plugin, String langCode) {
        this.plugin = plugin;
        loadLang(langCode);
    }

    /**
     * Ładuje plik tłumaczeń o podanym kodzie języka (np. "pl_PL")
     */
    public void loadLang(String langCode) {
        langFile = new File(plugin.getDataFolder(), "lang/" + langCode + ".yml");

        if (!langFile.exists()) {
            // Kopiuje domyślny plik z JARa jeśli nie istnieje
            plugin.saveResource("lang/" + langCode + ".yml", false);
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    /**
     * Podstawowa wersja get() — bez placeholderów.
     * Pobiera tekst z pliku tłumaczeń i koloruje go.
     */
    public String get(String path) {
        return get(path, new HashMap<>()); // Wywołuje wersję z pustą mapą
    }

    /**
     * Wersja z mapą placeholderów, np. {player}, {amount}, itp.
     */
    public String get(String path, Map<String, String> placeholders) {
        if (langConfig == null) {
            plugin.getLogger().log(Level.WARNING, "The lang file has not been loaded!");
            return "Lang not loaded!";
        }

        String raw = langConfig.getString(path, path);
        raw = raw.replace("\\n", "\n"); // Zamienia tekstowy \n na prawdziwy enter

        // Podmiana placeholderów {key} => value
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            raw = raw.replace("{" + entry.getKey() + "}", entry.getValue());
        }

        return ChatColor.translateAlternateColorCodes('&', raw); // Kolorki &a, &b itd.
    }

    /**
     * Wersja z wygodnym skrótem: get("path", "key1", "val1", "key2", "val2", ...)
     * Nie musisz tworzyć mapy ręcznie.
     */
    public String get(String path, Object... placeholders) {
        if (placeholders == null || placeholders.length == 0) {
            return get(path); // Brak placeholderów — użyj podstawowej wersji
        }

        if (placeholders.length % 2 != 0) {
            plugin.getLogger().warning("Invalid number of arguments for placeholders in path: " + path);
            return "Invalid placeholders!";
        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < placeholders.length; i += 2) {
            Object key = placeholders[i];
            Object value = placeholders[i + 1];
            if (key != null && value != null) {
                map.put(key.toString(), value.toString());
            }
        }

        return get(path, map); // Przekazuje dalej do głównej wersji z mapą
    }
}
