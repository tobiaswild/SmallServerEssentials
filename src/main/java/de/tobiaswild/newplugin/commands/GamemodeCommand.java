package de.tobiaswild.newplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.tobiaswild.newplugin.Main;

public class GamemodeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Main.PERMISSION + "gamemode")) {
            sender.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (args.length >= 1) {
            GameMode gamemode;
            switch (args[0]) {
                case "0", "survival", "Survival", "SURVIVAL" -> gamemode = GameMode.SURVIVAL;
                case "1", "creative", "Creative", "CREATIVE" -> gamemode = GameMode.CREATIVE;
                case "2", "adventure", "Adventure", "ADVENTURE" -> gamemode = GameMode.ADVENTURE;
                case "3", "spectator", "Spectator", "SPECTATOR" -> gamemode = GameMode.SPECTATOR;
                default -> {
                    sender.sendMessage(Main.ERROR + "Please enter a valid gamemode");
                    return false;
                }
            }
            if (args.length == 1) {
                if (!(sender instanceof Player player)) {
                    sender.sendMessage(Main.NO_PLAYER);
                    return false;
                }
                player.setGameMode(gamemode);
                player.sendMessage(Main.SUCCESS + "Neuer Spielmodus: " + player.getGameMode());
                return true;
            }
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    target.setGameMode(gamemode);
                    target.sendMessage(Main.SUCCESS + "Neuer Spielmodus: " + target.getGameMode());
                    sender.sendMessage(Main.SUCCESS + "Neuer Spielmodus von " + target.getDisplayName() + ": "
                            + target.getGameMode());
                    return true;
                }
            } else {
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
                list.add(player.getDisplayName());
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();
        for (String s1 : list) {
            if (s1.contains(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(Main.INFO + "Usage: /gamemode <gamemode> [<player>]");
    }
}
