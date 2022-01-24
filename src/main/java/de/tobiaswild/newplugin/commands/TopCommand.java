package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TopCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "top")) {
            Main.noPermission(player);
            return false;
        }
        if (args.length > 0) {
            player.sendMessage(Main.PREFIX + ChatColor.YELLOW + "Usage: /top");
        }
        if (player.getWorld().getName().equals("world")) {
            int x = (int) player.getLocation().getX();
            int z = (int) player.getLocation().getZ();
            int y = Bukkit.getWorld("world").getHighestBlockYAt(x , z);
            player.teleport(new Location(Bukkit.getWorld("world") , x ,y+2 ,z));
            player.playSound(player.getLocation() , Sound.ENTITY_ENDERMAN_TELEPORT , 1, 1);
        }else {
            int x = (int) Bukkit.getWorld("world").getSpawnLocation().getX();
            int z = (int) Bukkit.getWorld("world").getSpawnLocation().getZ();
            int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);
            player.teleport(new Location(Bukkit.getWorld("world"), x, y + 1, z));
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
