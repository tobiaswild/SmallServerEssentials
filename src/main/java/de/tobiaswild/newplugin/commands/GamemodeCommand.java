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

    private final Main plugin;

    public GamemodeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
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
                if (gamemode == player.getGameMode()) {
                    player.sendMessage(Main.ERROR + "You are already in " + gamemode + " mode");
                    return false;
                }
                player.setGameMode(gamemode);
                player.sendMessage(Main.SUCCESS + "Your new gamemode is " + gamemode);
                return true;
            }
            if (args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage(plugin.playerNotAvailable(args[0]));
                    return false;
                }
                if (gamemode == target.getGameMode()) {
                    player.sendMessage(Main.ERROR + target.getDisplayName() + " is already in " + gamemode + " mode");
                    return false;
                }
                target.setGameMode(gamemode);
                target.sendMessage(Main.SUCCESS + "Your new gamemode is " + gamemode);
                player.sendMessage(Main.SUCCESS + target.getDisplayName() + " new gamemode is " + gamemode);
                return true;

            } else {
                sendUsage(player);
            }
        }
        sendUsage(player);
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
