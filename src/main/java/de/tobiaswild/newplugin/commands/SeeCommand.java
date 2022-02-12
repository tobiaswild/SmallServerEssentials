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
import de.tobiaswild.newplugin.utils.Backpack;
import de.tobiaswild.newplugin.utils.BackpackManager;

public class SeeCommand implements CommandExecutor, TabCompleter {

    private final Main plugin;
    private final BackpackManager backpackManager = Main.getInstance().getBackpackManager();

    public SeeCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!sender.hasPermission(Main.PERMISSION + "see")) {
            sender.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(plugin.playerNotAvailable(args[0]));
                return false;
            }
            switch (args[0]) {
                case "backpack", "bp" -> {
                    Backpack backpack = backpackManager.getBackpack(target.getUniqueId());
                    player.openInventory(backpack.getInventory());
                    player.sendMessage(Main.SUCCESS + "Opened " + target.getDisplayName() + "'s Backpack");
                    return true;
                }
                case "enderchest", "ec" -> {
                    player.openInventory(target.getEnderChest());
                    player.sendMessage(Main.SUCCESS + "Opened " + target.getDisplayName() + "'s EnderChest");
                    return true;
                }
                case "inventory", "inv" -> {
                    player.openInventory(target.getInventory());
                    player.sendMessage(Main.SUCCESS + "Opened " + target.getDisplayName() + "'s Inventory");
                    return true;
                }
                default -> sendUsage(player);
            }
        }
        sendUsage(player);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            list.add("backpack");
            list.add("enderchest");
            list.add("inventory");
        }
        if (args.length == 2) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                list.add(player.getDisplayName());
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();
        for (String s : list) {
            if (s.startsWith(current)) {
                comList.add(s);
            }
        }
        return comList;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(Main.INFO + "Usage: /see [backpack/enderchest/inventory] [<player>]");
    }

}
