package org.naingame.maingame.command.admin;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.naingame.maingame.Maingame;

public class PerkMenage implements CommandExecutor {

    Maingame plugin = Maingame.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        Player player = (Player) sender;
        if (player.hasPermission("MG.admin")){
            if (arg.length == 0){
                player.sendMessage(plugin.getLangManager().get("messages.admin.notright"));
            } else if (arg[0].equalsIgnoreCase("add")) {
                
            }
        }else {
            // jezeli gracz nie poasiada uprawnień
            player.sendMessage(plugin.getLangManager().get("messages.basic.nopermision"));
            player.playSound(player.getLocation(), Sound.BLOCK_CHEST_LOCKED, 0.8f, 1.2f);
        }
        return true;
    }
}
