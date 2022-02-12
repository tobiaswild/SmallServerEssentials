package de.tobiaswild.newplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.DeathManager;

public class BackCommand implements CommandExecutor, TabCompleter {

    private final DeathManager deathManager = Main.getInstance().getDeathManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "back")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (!deathManager.getDeathPlayers().contains(player.getUniqueId())) {
            player.sendMessage(Main.ERROR + "Your death location is not contained in the config file");
            return false;
        }
        Location location = deathManager.getDeathPlayerLocation(player.getUniqueId());
        player.teleport(location);
        player.sendMessage(Main.SUCCESS + "You got send to your death location");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return new ArrayList<>();
    }
}
