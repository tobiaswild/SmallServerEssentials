package de.tobiaswild.newplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.tobiaswild.newplugin.Main;

public class ConnectionListener implements Listener {

    private Main plugin;
    private YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();

    public ConnectionListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(ChatColor.GREEN + "» " + ChatColor.RESET + event.getPlayer().getDisplayName());
        // Vanish
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (config.getBoolean("vanish." + player.getUniqueId())) {
                event.getPlayer().hidePlayer(plugin, player);
            }
            if (config.getBoolean("vanish." + event.getPlayer().getUniqueId())) {
                event.setJoinMessage(null);
                player.hidePlayer(plugin, event.getPlayer());
                player.sendMessage(Main.INFO + "You are currently vanished");
                if (player.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    player.sendMessage(ChatColor.GREEN + "» " + ChatColor.RESET + event.getPlayer().getDisplayName());
                }
            }

        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.RED + "« " + ChatColor.RESET + event.getPlayer().getDisplayName());
        // Vanish
        if (config.getBoolean("vanish." + event.getPlayer().getUniqueId())) {
            event.setQuitMessage(null);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    player.sendMessage(ChatColor.RED + "« " + ChatColor.RESET + event.getPlayer().getDisplayName());
                }
            }
        }
    }
}
