package org.naingame.maingame.system;

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

    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder(), "player_data");

        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdir();
        }
    }

    public void createDefaultDataIfNotExists(UUID uuid) {
        File file = getPlayerFile(uuid);
        if (!file.exists()) {
            FileConfiguration config = new YamlConfiguration();

            // Domyślne wartości
            config.set("perki.fish.level", 1);
            config.set("perki.fish.upgrade", 0.02);
            config.set("perki.fish.item", 0.0);

            config.set("perki.fishdable.level", 1);
            config.set("perki.fishdable.upgrade", 0.005);
            config.set("perki.fishdable.item", 0.0);

            saveConfig(config, file);
            plugin.getLogger().info("Utworzono plik danych gracza: " + uuid.toString());
        }
    }

    public double getPerkUpgrade(UUID uuid, String perkName) {
        File file = getPlayerFile(uuid);
        if (!file.exists()) return 0.0;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getDouble("perki." + perkName + ".upgrade", 0.0);
    }

    public double getPerkItemBonus(UUID uuid, String perkName) {
        File file = getPlayerFile(uuid);
        if (!file.exists()) return 0.0;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getDouble("perki." + perkName + ".item", 0.0);
    }

    public void setPerkUpgrade(UUID uuid, String perkName, double value) {
        setPerkSource(uuid, perkName, value, "upgrade");
    }

    public void setPerkItemBonus(UUID uuid, String perkName, double value) {
        setPerkSource(uuid, perkName, value, "item");
    }

    public void addPerkBonus(UUID uuid, String perkName, double value, String source) {
        List<String> allowedSources = Arrays.asList("upgrade", "item");

        if (!allowedSources.contains(source.toLowerCase())) {
            throw new IllegalArgumentException("Nieznane źródło bonusu: " + source);
        }

        File file = getPlayerFile(uuid);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        String path = "perki." + perkName + "." + source.toLowerCase();
        double current = config.getDouble(path, 0.0);
        config.set(path, current + value);

        saveConfig(config, file);
    }

    public double getTotalPerkBonus(UUID uuid, String perkName) {
        return getPerkUpgrade(uuid, perkName) + getPerkItemBonus(uuid, perkName);
    }

    // ===== NOWOŚĆ: Obsługa poziomów (level) =====

    /**
     * Pobiera poziom danego perka gracza.
     * Domyślnie zwraca 1, jeśli nic nie ma.
     */
    public int getPerkLevel(UUID uuid, String perkName) {
        File file = getPlayerFile(uuid);
        if (!file.exists()) return 1;

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config.getInt("perki." + perkName + ".level", 1);
    }

    /**
     * Ustawia nowy poziom danego perka gracza.
     */
    public void setPerkLevel(UUID uuid, String perkName, int level) {
        File file = getPlayerFile(uuid);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("perki." + perkName + ".level", level);
        saveConfig(config, file);
    }

    /**
     * Dodaje do poziomu gracza określoną ilość (np. +1).
     */
    public void addPerkLevel(UUID uuid, String perkName, int amount) {
        int currentLevel = getPerkLevel(uuid, perkName);
        setPerkLevel(uuid, perkName, currentLevel + amount);
    }

    // ===== Pomocnicze metody =====

    private void setPerkSource(UUID uuid, String perkName, double value, String source) {
        File file = getPlayerFile(uuid);
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("perki." + perkName + "." + source.toLowerCase(), value);
        saveConfig(config, file);
    }

    private File getPlayerFile(UUID uuid) {
        return new File(playerDataFolder, uuid.toString() + ".yml");
    }

    private void saveConfig(FileConfiguration config, File file) {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
 public class CustomPointsManager {
    private Map<UUID, Integer> points = new HashMap<>();

    public int getPoints(UUID playerId) {
        return points.getOrDefault(playerId, 0);
    }

    public void addPoints(UUID playerId, int amount) {
        points.put(playerId, getPoints(playerId) + amount);
    }

    public void removePoints(UUID playerId, int amount) {
        points.put(playerId, Math.max(0, getPoints(playerId) - amount));
    }

    // metody do ładowania i zapisywania z pliku/bazy
}
*/
