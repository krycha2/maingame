package org.naingame.maingame;

import org.bukkit.plugin.java.JavaPlugin;
import org.naingame.maingame.command.user.MainGcommand;
import org.naingame.maingame.system.guisystem.MenuListener;
import org.naingame.maingame.system.playerdata.PlayerDataManager;

public final class Maingame extends JavaPlugin {

    private PlayerDataManager dataPlayerMenager;

    private static Maingame instance;

    public static Maingame getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.dataPlayerMenager = new PlayerDataManager(this);

        getServer().getPluginManager().registerEvents(new MenuListener(), this);


        // Rejestracja komendy z osobnym Executorem
        this.getCommand("minegame").setExecutor(new MainGcommand());

        getLogger().info("CowCannon został włączony!");
    }
    public PlayerDataManager getDataManager() {
        return dataPlayerMenager;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
