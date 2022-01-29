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

public class HealCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public HealCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "heal")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (args.length == 0) {
            if (player.getGameMode() != GameMode.SURVIVAL) {
                player.sendMessage(plugin.wrongGamemode(GameMode.SURVIVAL));
                return false;
            }
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setSaturation(20);
            player.setRemainingAir(300);
            player.sendMessage(Main.SUCCESS + "You got healed");
            return true;
        }
        if (args.length == 1) {
            if (!player.hasPermission(Main.PERMISSION + "heal.other")) {
                player.sendMessage(Main.NO_PERMISSION);
                return false;
            }
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(Main.ERROR + "The player " + args[0] + " is offline");
                return false;
            }
            if (target.getName().equals(player.getName())) {
                player.sendMessage(Main.ERROR + "Just use /heal if you want to get healed");
                return false;
            }
            if (target.getGameMode() != GameMode.SURVIVAL) {
                player.sendMessage(plugin.wrongTargetGamemode(target.getDisplayName(), GameMode.SURVIVAL));
                return false;
            }
            target.setHealth(20);
            target.setFoodLevel(20);
            target.setSaturation(20);
            target.setRemainingAir(300);
            target.sendMessage(Main.SUCCESS + "You got healed");
            player.sendMessage(Main.SUCCESS + "You successfully healed " + target.getDisplayName());
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
            if (sender.hasPermission(Main.PERMISSION + "heal.other")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    list.add(player.getName());
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
