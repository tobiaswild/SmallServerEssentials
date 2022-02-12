package de.tobiaswild.newplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.VanishManager;

public class ConnectionListener implements Listener {

    private final Main plugin;
    private final VanishManager vanishManager = Main.getInstance().getVanishManager();

    public ConnectionListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + "» " + ChatColor.RESET + player.getDisplayName());
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (vanishManager.getVanishedPlayers().contains(online.getUniqueId())) {
                player.hidePlayer(plugin, online);
            }
            if (vanishManager.getVanishedPlayers().contains(player.getUniqueId())) {
                event.setJoinMessage(null);
                online.hidePlayer(plugin, player);
                online.sendMessage(Main.INFO + "You are currently vanished");
                if (online.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    online.sendMessage(ChatColor.GREEN + "» " + ChatColor.RESET + player.getDisplayName());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.RED + "« " + ChatColor.RESET + player.getDisplayName());
        if (vanishManager.getVanishedPlayers().contains(player.getUniqueId())) {
            event.setQuitMessage(null);
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    online.sendMessage(ChatColor.RED + "« " + ChatColor.RESET + player.getDisplayName());
                }
            }
        }
    }
}
