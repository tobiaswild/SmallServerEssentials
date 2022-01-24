package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class VanishCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public VanishCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        FileConfiguration configuration = plugin.getConfig();
        if(!(sender instanceof Player player)) {
            Main.noPlayer(sender);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "vanish")) {
            Main.noPermission(player);
            return false;
        }
        if (args.length > 0) {
            sendUsage(sender);
            return false;
        }
        if (plugin.getConfig().getBoolean("vanish.player." + player.getUniqueId())) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.showPlayer(player);
            }
            plugin.getConfig().set("vanish.player." + player.getUniqueId(), false);
            player.removePotionEffect(PotionEffectType.GLOWING);
            player.sendMessage(Main.PREFIX + ChatColor.YELLOW + "Du bist wieder sichtbar");
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.isOp()) {
                    p.hidePlayer(player);
                }
            }
            plugin.getConfig().set("vanish.player." + player.getUniqueId(), true);
            player.addPotionEffect((new PotionEffect(PotionEffectType.GLOWING, 1000000000, 255, false)));
            player.sendMessage(Main.PREFIX + ChatColor.YELLOW + "Du bist nicht mehr sichtbar");
        }
        plugin.saveConfig();
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.BLUE + "Verwendung: /vanish");
    }
}
