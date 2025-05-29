package org.naingame.maingame.command.user;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.naingame.maingame.Maingame;
import org.naingame.maingame.gui.MainGui;
import org.naingame.maingame.gui.undergui.playerprofile.MainPlayerProfile;

public class MainGcommand implements CommandExecutor {

    /*private final Maingame plugin;

    public ChangeMessageCommand(Maingame plugin) {
        this.plugin = plugin;
    }*/

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        // sprawdzanie czy graczy wpsuje tą komende
        if (!(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;
        // sprawdzanie czy grac
        if (p.hasPermission("MG.meingu")) {
            if (arg.length == 0) {
                new MainGui().displayTo(p); // otwarcie menu głwonego po wpisaniu /minegame
            } else if (arg[0].equalsIgnoreCase("help")) {

            } else if (arg[0].equalsIgnoreCase("profil")) {
                new MainPlayerProfile().displayTo(p); // otwarcie GUI profilu

                return true;
            }
        }else {
            p.sendMessage(ChatColor.RED + "Nie posiadasz" + ChatColor.BOLD + "UPRAWNIEŃ!!");
            p.playSound(p.getLocation(), Sound.BLOCK_CHEST_LOCKED, 0.8f, 1.2f);
        }
        return true;
    }
}
