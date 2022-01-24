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

public class GamemodeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Main.PERMISSION + "gamemode")) {
            Main.noPermission(sender);
            return false;
        }
        if (args.length >= 1) {
            GameMode gm;
            switch (args[0]) {
                case "0", "survival", "Survival", "SURVIVAL" -> gm = GameMode.SURVIVAL;
                case "1", "creative", "Creative", "CREATIVE" -> gm = GameMode.CREATIVE;
                case "2", "adventure", "Adventure", "ADVENTURE" -> gm = GameMode.ADVENTURE;
                case "3", "spectator", "Spectator", "SPECTATOR" -> gm = GameMode.SPECTATOR;
                default -> {
                    sender.sendMessage(ChatColor.RED + "Gib bitte eine gültigen Spielmodus an");
                    return false;
                }
            }
            if (args.length == 1) {
                if(!(sender instanceof Player player)) {
                    Main.noPlayer(sender);
                    return false;
                }
                player.setGameMode(gm);
                player.sendMessage(ChatColor.GREEN + "Neuer Spielmodus: " + player.getGameMode());
                return true;
            }
            if (args.length == 2) {
                Player target = Bukkit.getOfflinePlayer(args[1]).getPlayer();
                if (target != null) {
                    target.setGameMode(gm);
                    target.sendMessage(ChatColor.GREEN + "Neuer Spielmodus: " + target.getGameMode());
                    sender.sendMessage(ChatColor.GREEN + "Neuer Spielmodus von "  + target.getName() + ": " + target.getGameMode());
                    return true;
                }
            }
            else {
                sendUsage(sender);
            }
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
            list.add("survival");
            list.add("creative");
            list.add("adventure");
            list.add("spectator");
        }
        if (args.length == 2) {
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
        sender.sendMessage(ChatColor.BLUE + "Usage: /gamemode <gamemode> [<player>]");
    }
}
