package de.tobiaswild.sse.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.tobiaswild.sse.Main;

public class TopCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "top")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (player.getWorld().getName().equals("world")) {
            int x = (int) player.getLocation().getX();
            int z = (int) player.getLocation().getZ();
            int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);
            player.teleport(new Location(Bukkit.getWorld("world"), x, y + 2, z));
        } else {
            int x = (int) Bukkit.getWorld("world").getSpawnLocation().getX();
            int z = (int) Bukkit.getWorld("world").getSpawnLocation().getZ();
            int y = Bukkit.getWorld("world").getHighestBlockYAt(x, z);
            player.teleport(new Location(Bukkit.getWorld("world"), x, y + 1, z));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
