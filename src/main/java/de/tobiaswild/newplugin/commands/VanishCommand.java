package de.tobiaswild.newplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import de.tobiaswild.newplugin.Main;

public class VanishCommand implements CommandExecutor, TabCompleter {

    private Main plugin;
    private YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();

    public VanishCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "vanish")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (config.getBoolean("vanish." + player.getUniqueId())) {
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.showPlayer(plugin, player);
            }
            config.set("vanish." + player.getUniqueId(), false);
            player.removePotionEffect(PotionEffectType.GLOWING);
            player.sendMessage(Main.INFO + "You are now visible again");
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.isOp()) {
                    p.hidePlayer(plugin, player);
                }
            }
            config.set("vanish." + player.getUniqueId(), true);
            player.addPotionEffect((new PotionEffect(PotionEffectType.GLOWING, 1000000000, 255, false)));
            player.sendMessage(Main.INFO + "You are now vanished");
        }
        plugin.saveConfig();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
