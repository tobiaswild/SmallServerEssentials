package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WeatherCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "weather")) {
            Main.noPermission(player);
            return false;
        }
        if (args.length == 1) {
            switch (args[0]) {
                case "clear":
                    Bukkit.getWorld("world").setThundering(false);
                    Bukkit.getWorld("world").setStorm(false);
                    player.sendMessage("Clear Wetter");
                    break;
                case "storm":
                    Bukkit.getWorld("world").setStorm(true);
                    player.sendMessage("Sturm");
                    break;
                case "thunder":
                    player.sendMessage("Donner");
                    Bukkit.getWorld("world").setThundering(true);
                    break;
                default:
                    player.sendMessage("error");
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            list.add("clear");
            list.add("storm");
            list.add("thunder");
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

    }
}
