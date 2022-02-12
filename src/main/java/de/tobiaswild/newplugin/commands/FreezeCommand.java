package de.tobiaswild.newplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.FreezeManager;

public class FreezeCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;
    private final FreezeManager freezeManager = Main.getInstance().getFreezeManager();

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
            if (target == null) {
                sender.sendMessage(plugin.playerNotAvailable(args[0]));
                return false;
            }
            if (freezeManager.getFrozenPlayers().contains(target.getUniqueId())) {
                freezeManager.deleteFreezePosition(target.getUniqueId());
                target.playSound(target.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
                target.sendMessage(Main.INFO + "You are now free");
                sender.sendMessage(Main.SUCCESS + target.getDisplayName() + " is now free");
            } else {
                Location location = target.getLocation();
                freezeManager.setFreezePosition(target.getUniqueId(), location);
                target.playSound(target.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
                target.sendMessage(Main.INFO + "You are now frozen");
                sender.sendMessage(Main.SUCCESS + target.getDisplayName() + " is now frozen");
            }
            return true;
        }
        sender.sendMessage(Main.ERROR + "Please enter a player to freeze");
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
