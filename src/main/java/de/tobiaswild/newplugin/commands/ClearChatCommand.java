package de.tobiaswild.newplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.tobiaswild.newplugin.Main;

public class ClearChatCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "clearchat")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        int amount = 100;
        if (args.length == 1) {
            amount = Integer.parseInt(args[0]);
        }
        for (int i = 0; i <= amount; i++) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.sendMessage(" \n");
            }
        }
        sender.sendMessage(Main.SUCCESS + "chat cleared with " + amount + " lines");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias,
            String[] args) {
        return new ArrayList<>();
    }
}
