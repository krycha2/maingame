package org.naingame.maingame.system.playerdata;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PlayerDataManager {
    private final JavaPlugin plugin;
    private final File playerDataFolder;

    /**
     * Konstruktor tworzący menadżera danych gracza.
     * Tworzy folder "player_data" w folderze pluginu jeśli go nie ma.
     */
    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder(), "player_data");

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdir();  // Tworzymy folder jeśli nie istnieje
        }
    }

    /**
     * Pobiera wartość bonusu z upgrade'u dla danego perka i gracza.
     * @param uuid UUID gracza
     * @param perkName nazwa perka
     * @return bonus z upgrade'u, lub 0.0 jeśli brak danych
     */
    public double getPerkUpgrade(UUID uuid, String perkName) {
        File file = getPlayerFile(uuid);
        if (!file.exists()) return 0.0; // jeśli plik nie istnieje, zwracamy 0

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getDouble("perki." + perkName + ".upgrade", 0.0);
    }

    /**
     * Pobiera wartość bonusu z przedmiotów dla danego perka i gracza.
     * @param uuid UUID gracza
     * @param perkName nazwa perka
     * @return bonus z itemów, lub 0.0 jeśli brak danych
     */
    public double getPerkItemBonus(UUID uuid, String perkName) {
        File file = getPlayerFile(uuid);
        if (!file.exists()) return 0.0;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getDouble("perki." + perkName + ".item", 0.0);
    }

    /**
     * Ustawia wartość bonusu z upgrade'u dla danego perka i gracza.
     * @param uuid UUID gracza
     * @param perkName nazwa perka
     * @param value nowa wartość bonusu
     */
    public void setPerkUpgrade(UUID uuid, String perkName, double value) {
        setPerkSource(uuid, perkName, value, "upgrade");
    }

    /**
     * Ustawia wartość bonusu z itemów dla danego perka i gracza.
     * @param uuid UUID gracza
     * @param perkName nazwa perka
     * @param value nowa wartość bonusu
     */
    public void setPerkItemBonus(UUID uuid, String perkName, double value) {
        setPerkSource(uuid, perkName, value, "item");
    }

    /**
     * Dodaje wartość do istniejącego bonusu upgrade lub item.
     * @param uuid UUID gracza
     * @param perkName nazwa perka
     * @param value wartość do dodania (np. 5.0 oznacza +5%)
     * @param source źródło bonusu - "upgrade" lub "item"
     * @throws IllegalArgumentException gdy podane źródło jest nieprawidłowe
     */
    public void addPerkBonus(UUID uuid, String perkName, double value, String source) {
        // Lista dozwolonych źródeł
        List<String> allowedSources = Arrays.asList("upgrade", "item");

        // Sprawdzamy czy source jest poprawne
        if (!allowedSources.contains(source.toLowerCase())) {
            throw new IllegalArgumentException("Nieznane źródło bonusu: " + source);
        }

        File file = getPlayerFile(uuid);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        // Ścieżka do wartości w pliku YML
        String path = "perki." + perkName + "." + source.toLowerCase();

        // Pobieramy aktualną wartość (domyślnie 0.0) i dodajemy nową
        double current = config.getDouble(path, 0.0);
        config.set(path, current + value);

        // Zapisujemy zmiany do pliku
        saveConfig(config, file);
    }

    /**
     * Zwraca całkowity bonus (suma upgrade + item) dla danego perka i gracza.
     * @param uuid UUID gracza
     * @param perkName nazwa perka
     * @return suma bonusów
     */
    public double getTotalPerkBonus(UUID uuid, String perkName) {
        return getPerkUpgrade(uuid, perkName) + getPerkItemBonus(uuid, perkName);
    }

    // =================== Pomocnicze metody =====================

    /**
     * Ustawia wartość dla konkretnego źródła bonusu (upgrade lub item).
     * Metoda prywatna, wywoływana przez publiczne setPerkUpgrade i setPerkItemBonus.
     */
    private void setPerkSource(UUID uuid, String perkName, double value, String source) {
        File file = getPlayerFile(uuid);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("perki." + perkName + "." + source.toLowerCase(), value);
        saveConfig(config, file);
    }

    /**
     * Zwraca plik YML z danymi gracza po UUID.
     */
    private File getPlayerFile(UUID uuid) {
        return new File(playerDataFolder, uuid.toString() + ".yml");
    }

    /**
     * Zapisuje konfigurację do pliku.
     * @param config konfiguracja YAML
     * @param file plik do zapisu
     */
    private void saveConfig(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
