package de.tobiaswild.sse.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tobiaswild.sse.Main;
import de.tobiaswild.sse.utils.BuildManager;

public class BuildCommand implements CommandExecutor, TabCompleter {

    private final BuildManager buildManager = Main.getInstance().getBuildManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "build")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        UUID uuid = player.getUniqueId();
        Inventory inventory = player.getInventory();
        if (player.getGameMode().equals(GameMode.SURVIVAL)) {
            buildManager.setItems(uuid, inventory.getContents());

            ItemStack[] empty = new ItemStack[] {};
            inventory.setContents(empty);

            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Main.SUCCESS + "You can now build");
            return true;
        } else if (player.getGameMode().equals(GameMode.CREATIVE)) {
            inventory.setContents(buildManager.getItems(uuid));

            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Main.INFO + "You can now no longer build");
            return true;
        } else {
            player.sendMessage(Main.ERROR + "You have to be in creative or survival mode");
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}
