package org.naingame.maingame;

import org.bukkit.plugin.java.JavaPlugin;
import org.naingame.maingame.command.user.MenuCommand;
import org.naingame.maingame.system.guisystem.MenuListener;

public final class Maingame extends JavaPlugin {


    private static Maingame instance;

    public static Maingame getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new MenuListener(), this);

        // Rejestracja komendy z osobnym Executorem
        this.getCommand("menu").setExecutor(new MenuCommand());

        getLogger().info("CowCannon został włączony!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
