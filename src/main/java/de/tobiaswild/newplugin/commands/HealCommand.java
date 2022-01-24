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

public class HealCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "heal")) {
            Main.noPermission(player);
            return false;
        }
        if (args.length == 0) {
            if(!(player.getGameMode() == GameMode.SURVIVAL)) {
                Main.wrongGamemode(player, GameMode.SURVIVAL);
                return false;
            }
            player.setHealth(20);
            player.setSaturation(20);
            player.setRemainingAir(300);
            player.sendMessage(ChatColor.GREEN + "Du wurdest geheilt");
            return true;
        }
        if (args.length == 1) {
            String targetName = args[0];
            Player target = Bukkit.getOfflinePlayer(targetName).getPlayer();
            if (target != null) {
                target.setHealth(20);
                target.setSaturation(30);
                target.setRemainingAir(300);
                target.sendMessage(ChatColor.GREEN + "Du wurdest geheilt");
                sender.sendMessage(ChatColor.GREEN + "Du hast " + target.getName() + " geheilt");
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
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
            if (s1.startsWith(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }

    public void sendUsage(CommandSender sender) {

    }
}
