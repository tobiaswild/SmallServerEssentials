package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.backpack.Backpack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BackpackCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!player.hasPermission("backpack.command")) {
            Main.noPermission(player);
            return false;
        }
        if (args.length == 0) {
            Backpack backpack = Main.getInstance().getBackpackManager().getBackpack(player.getUniqueId());
            player.openInventory(backpack.getInventory());
            return true;
        }
        if (args.length == 1) {
            if (!player.hasPermission("backpack.command.other")) {
                Main.noPermission(player);
                return false;
            }
            Player target = Bukkit.getOfflinePlayer(args[0]).getPlayer();
            if (target != null) {
                Backpack backpack = Main.getInstance().getBackpackManager().getBackpack(target.getUniqueId());
                player.openInventory(backpack.getInventory());
                return true;
            }
            player.sendMessage(ChatColor.RED + "Der Spieler " + args[0] + " ist nicht online");
            return false;
        }
        sendUsage(sender);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getName());
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length-1].toLowerCase();
        for (String s1 : list) {
            if (s1.contains(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Verwendung: /backpack [<spieler>]");
    }
}
