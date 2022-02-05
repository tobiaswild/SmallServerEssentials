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
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.FreezeManager;

public class FreezeCommand implements CommandExecutor, TabCompleter {

    private final FreezeManager freezeManager = Main.getInstance().getFreezeManager();
    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
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
                if (config.contains("freeze." + target.getUniqueId())) {
                    freezeManager.deleteFreezePosition(target);
                    target.playSound(target.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, 1, 1);
                    target.sendMessage(Main.SUCCESS + "You are now free");
                } else {
                    int x = (int) target.getLocation().getX();
                    int z = (int) target.getLocation().getZ();
                    int y = Objects.requireNonNull(Bukkit.getWorld(target.getWorld().getName())).getHighestBlockYAt(x,
                            z);
                    target.teleport(new Location(Bukkit.getWorld("world"), x, y + 2, z));
                    if (target.isFlying()) {
                        target.setFlying(false);
                    }
                    Location location = new Location(
                            target.getLocation().getWorld(),
                            (int) target.getLocation().getX(),
                            (int) target.getLocation().getY(),
                            (int) target.getLocation().getZ());
                    freezeManager.setFreezePosition(target, location);
                    target.playSound(target.getLocation(), Sound.BLOCK_IRON_DOOR_CLOSE, 1, 1);
                    target.sendMessage(Main.ERROR + "You are frozen");
                }
                return true;
            }
            return false;
        }
        sender.sendMessage(Main.ERROR + "Please enter a player to freeze.");
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
