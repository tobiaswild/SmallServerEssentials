package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EnderchestCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!sender.hasPermission(Main.PERMISSION + "enderchest")) {
            Main.noPermission(sender);
            return false;
        }
        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
            player.sendMessage(Main.SUCCESS + "Opened you EnderChest");
            return true;
        }
        if (args.length == 1) {
            if (!player.hasPermission(Main.PERMISSION + "enderchest.other")) {
                Main.noPermission(player);
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                player.openInventory(target.getEnderChest());
                player.sendMessage(Main.SUCCESS + "Opened " + target.getDisplayName() + "'s EnderChest");
                return true;
            }
            player.sendMessage(Main.ERROR + "The Player " + args[0] + " is offline");
            return false;
        }
        player.sendMessage(Main.INFO + "Usage: /enderchest [<player>]");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (sender.hasPermission("essentials.enderchest.other")) {
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
