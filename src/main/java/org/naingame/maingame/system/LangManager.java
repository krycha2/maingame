package org.naingame.maingame.system;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.io.File;
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
            plugin.saveResource("lang/" + langCode + ".yml", false);  // Kopiuje domyślny plik z jar jeśli nie ma
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }
    /**
     * Pobiera tekst z pliku tłumaczeń pod kluczem (np. "messages.welcome")
     * Możesz użyć "{param}" w tekście i podmienić ręcznie po pobraniu.
     */
    public String get(String path){
        if (langConfig == null){
            plugin.getLogger().log(Level.WARNING, "The lang file has not been loaded!");
            return "Lang not loaded!";
        }
        String raw = langConfig.getString(path, path);
        raw = raw.replace("\\n", "\n"); // Zamień tekstowy "\n" na prawdziwy enter
        return ChatColor.translateAlternateColorCodes('&', raw);
    }
}
