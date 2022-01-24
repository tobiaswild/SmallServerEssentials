package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public FlyCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "fly")) {
            Main.noPermission(player);
            return false;
        }
        if (args.length == 0) {
            if(player.getGameMode() != GameMode.SURVIVAL) {
                Main.wrongGamemode(player, GameMode.SURVIVAL);
                return false;
            }
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage(Main.ERROR + "You are no longer able to fly");
            } else {
                player.setAllowFlight(true);
                player.sendMessage(Main.SUCCESS + "You are now able to fly");
            }
            return true;
        }
        if (args.length == 1) {
            if (!sender.hasPermission(Main.PERMISSION + "fly.other")) {
                Main.noPermission(sender);
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(Main.ERROR + "The player " + target.getDisplayName() + " is offline");
                return false;
            }
            if (target.getName().equals(sender.getName())) {
                player.sendMessage(Main.ERROR + "Just use /fly if you want to fly");
                return false;
            }
            if(target.getGameMode() != GameMode.SURVIVAL) {
                Main.wrongTargetGamemode(sender, args[0], GameMode.SURVIVAL);
                return false;
            }
            if (target.getAllowFlight()) {
                target.setAllowFlight(false);
                target.sendMessage(Main.SUCCESS + "Du kannst jetzt nicht mehr fliegen");
                sender.sendMessage(Main.INFO + target.getDisplayName() + " kann jetzt nicht mehr fliegen");
            } else {
                target.setAllowFlight(true);
                target.sendMessage(Main.SUCCESS + "Du kannst jetzt fliegen");
                sender.sendMessage(Main.INFO + target.getDisplayName() + " kann jetzt fliegen");
            }
            return true;
        }
        player.sendMessage(Main.INFO + "Usage: /fly [<player>]");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (sender.hasPermission(Main.PERMISSION + "fly.other")) {
            if (args.length == 1) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    list.add(player.getName());
                }
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length-1].toLowerCase();
        for (String s1 : list) {
            if (s1.startsWith(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }
}
