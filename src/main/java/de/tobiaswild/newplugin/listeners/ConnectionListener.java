package de.tobiaswild.newplugin.listeners;

import de.tobiaswild.newplugin.utils.VanishManager;
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

    private final Main plugin;
    private final VanishManager vanishManager = Main.getInstance().getVanishManager();

    public ConnectionListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + "» " + ChatColor.RESET + player.getDisplayName());
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (vanishManager.getVanishedPlayers().contains(p.getUniqueId())) {
                player.hidePlayer(plugin, p);
            }
            if (vanishManager.getVanishedPlayers().contains(player.getUniqueId())) {
                event.setJoinMessage(null);
                p.hidePlayer(plugin, player);
                p.sendMessage(Main.INFO + "You are currently vanished");
                if (p.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    p.sendMessage(ChatColor.GREEN + "» " + ChatColor.RESET + player.getDisplayName());
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
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    p.sendMessage(ChatColor.RED + "« " + ChatColor.RESET + player.getDisplayName());
                }
            }
        }
    }
}
