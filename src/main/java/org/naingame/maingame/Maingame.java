package org.naingame.maingame;

import org.bukkit.plugin.java.JavaPlugin;
import org.naingame.maingame.command.user.MainGcommand;
import org.naingame.maingame.system.CustomItemLoader;
import org.naingame.maingame.system.LangManager;
import org.naingame.maingame.system.guisystem.MenuListener;
import org.naingame.maingame.system.playerdata.PlayerDataManager;
import org.naingame.maingame.system.upgrade.CastofFisingItem;

public final class Maingame extends JavaPlugin {

    private PlayerDataManager dataPlayerMenager;
    private static Maingame instance;
    private LangManager langManager;
    private CustomItemLoader itemLoader;
    public static Maingame getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.dataPlayerMenager = new PlayerDataManager(this);
        this.itemLoader = new CustomItemLoader(this);
        langManager = new LangManager(this, "en_EN");
        // rejestrowanie kliknięc w gui
        getServer().getPluginManager().registerEvents(new MenuListener(), this);
        getServer().getPluginManager().registerEvents(new CastofFisingItem(),this);

        // Rejestracja komendy z osobnym Executorem
        this.getCommand("minegame").setExecutor(new MainGcommand());
    }
    public PlayerDataManager getDataManager() {
        return dataPlayerMenager;
    }
    public LangManager getLangManager() {
        return langManager;
    }
    public CustomItemLoader getItemLoader() {
        return itemLoader;
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
