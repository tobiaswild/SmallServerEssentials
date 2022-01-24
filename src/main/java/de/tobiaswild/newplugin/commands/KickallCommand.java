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

public class KickallCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Main.PERMISSION + "kickall")) {
            Main.noPermission(sender);
            return false;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <= args.length-1; i++) {
            builder.append(args[i]).append(" ");
        }
        String reason = builder.toString().trim();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(ChatColor.YELLOW + "Du wurdest von " + ChatColor.GOLD + sender.getName() +
                    ChatColor.YELLOW + " gekickt: " + ChatColor.RESET + reason);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Alle Spieler" + ChatColor.YELLOW + " wurden von " +
                ChatColor.GOLD + sender.getName() + ChatColor.YELLOW + " gekickt: " + ChatColor.RESET + reason);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            list.add("<grund>");
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

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(Main.PREFIX + ChatColor.BLUE + "Verwendung: /kickall <grund>");
    }
}
