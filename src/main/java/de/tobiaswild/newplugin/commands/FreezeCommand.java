package de.tobiaswild.newplugin.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.tobiaswild.newplugin.Main;

public class FreezeCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public FreezeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Main.PERMISSION + "freeze")) {
            sender.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (args.length == 1) {
            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);
            if (target != null) {
                if (plugin.getFrozenPlayers().containsKey(target)) {
                    plugin.getFrozenPlayers().remove(target);
                    target.playSound(target.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
                    target.sendMessage(Main.SUCCESS + "You are now free");
                } else {
                    int x = (int) target.getLocation().getX();
                    int z = (int) target.getLocation().getZ();
                    int y = Objects.requireNonNull(Bukkit.getWorld(target.getWorld().getName())).getHighestBlockYAt(x, z);
                    target.teleport(new Location(Bukkit.getWorld("world"), x, y + 2, z));
                    if (target.isFlying()) {
                        target.setFlying(false);
                    }
                    plugin.getFrozenPlayers().put(target, target.getLocation().clone());
                    target.playSound(target.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
                    target.sendMessage(Main.ERROR + "You are frozen");
                }
                return true;
            }
            return false;
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
}
