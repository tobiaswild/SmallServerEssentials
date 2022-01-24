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

import java.util.List;

public class FreezeCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Main.PERMISSION + "freeze")) {
            Main.noPermission(sender);
            return false;
        }
        if (args.length == 1) {
            String targetName = args[0];
            if (Bukkit.getOfflinePlayer(targetName).getPlayer() != null) {
                Player target = Bukkit.getPlayer(targetName);
                if (Main.getInstance().getFrozenPlayers().containsKey(target)) {
                    Main.getInstance().getFrozenPlayers().remove(target);
                    target.playSound(target.getLocation() , Sound.BLOCK_IRON_DOOR_OPEN , 1, 1);
                    target.sendMessage(ChatColor.GREEN + "Du kannst dich wieder frei bewegen");
                    return true;
                } else {
                    if (!target.isOnGround()) {
                        int x = (int) target.getLocation().getX();
                        int z = (int) target.getLocation().getZ();
                        int y = Bukkit.getWorld(String.valueOf(target.getWorld())).getHighestBlockYAt(x , z);
                        target.teleport(new Location(Bukkit.getWorld("world") , x ,y+2 ,z));
                        if (target.isFlying()) {
                            target.setFlying(false);
                        }
                        target.playSound(target.getLocation() , Sound.ENTITY_ENDERMAN_TELEPORT , 1, 1);
                        target.sendMessage(ChatColor.RED + "Du wurdest auf den Boden zurückgeholt, um sicherzugehen, dass dir nichts passiert");
                    }
                    Main.getInstance().getFrozenPlayers().put(target, target.getLocation().clone());
                    //TODO: fix respawn point. isn't the same as freeze point
                    target.getServer().getWorld(String.valueOf(target.getWorld())).setSpawnLocation(target.getLocation());
                    target.playSound(target.getLocation() , Sound.BLOCK_IRON_DOOR_CLOSE , 1, 1);
                    target.sendMessage(ChatColor.RED + "Du wurdest eingefroren");
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public void sendUsage(CommandSender sender) {

    }
}
