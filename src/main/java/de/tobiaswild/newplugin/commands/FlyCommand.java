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

public class FlyCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;

    public FlyCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "fly")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (args.length == 0) {
            if (player.getGameMode() != GameMode.SURVIVAL) {
                player.sendMessage(plugin.wrongGamemode(GameMode.SURVIVAL));
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
            if (!player.hasPermission(Main.PERMISSION + "fly.other")) {
                player.sendMessage(Main.NO_PERMISSION);
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(plugin.playerNotAvailable(args[0]));
                return false;
            }
            if (target.getName().equals(player.getName())) {
                player.sendMessage(Main.ERROR + "Just use /fly if you want to fly");
                return false;
            }
            if (target.getGameMode() != GameMode.SURVIVAL) {
                player.sendMessage(plugin.wrongTargetGamemode(args[0], GameMode.SURVIVAL));
                return false;
            }
            if (target.getAllowFlight()) {
                target.setAllowFlight(false);
                target.sendMessage(Main.INFO + "You cant fly now");
                player.sendMessage(Main.SUCCESS + target.getDisplayName() + " cant fly anymore");
            } else {
                target.setAllowFlight(true);
                target.sendMessage(Main.SUCCESS + "You are able to fly");
                player.sendMessage(Main.INFO + target.getDisplayName() + " is now able to fly");
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
        if (args.length == 1) {
            if (sender.hasPermission(Main.PERMISSION + "fly.other")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    list.add(player.getDisplayName());
                }
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();
        for (String s1 : list) {
            if (s1.startsWith(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }
}
