package de.tobiaswild.sse.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.tobiaswild.sse.Main;
import de.tobiaswild.sse.utils.Backpack;
import de.tobiaswild.sse.utils.BackpackManager;

public class BackpackCommand implements CommandExecutor, TabCompleter {

    private final BackpackManager backpackManager = Main.getInstance().getBackpackManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "backpack")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        Backpack backpack = backpackManager.getBackpack(player.getUniqueId());
        player.openInventory(backpack.getInventory());
        player.sendMessage(Main.SUCCESS + "Opened your Backpack");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}
