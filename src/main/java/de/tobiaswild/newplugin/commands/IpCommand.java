package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class IpCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "ip")) {
            Main.noPermission(player);
            return false;
        }
        if (args.length > 0) {
            player.sendMessage(Main.INFO + "Usage: /ip");
        }
        player.sendMessage(Main.SUCCESS + "Your IP is: " + player.getAddress().getHostString());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
