package de.tobiaswild.newplugin.listeners;

import de.tobiaswild.newplugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class VanishListener implements Listener {

    private Main plugin;

    public VanishListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (plugin.getConfig().getBoolean("vanish.player." + player.getUniqueId())) {
                event.getPlayer().hidePlayer(player);
            }
            if (plugin.getConfig().getBoolean("vanish.player." + event.getPlayer().getUniqueId())) {
                player.hidePlayer(event.getPlayer());
                event.setJoinMessage(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.getConfig().getBoolean("vanish.player." + event.getPlayer().getUniqueId())) {
            event.setQuitMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.getConfig().getBoolean("vanish.player." + event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            for(Player player : Bukkit.getOnlinePlayers()) {
                if (event.getMessage().startsWith("7")) {
                    player.sendMessage(Main.PREFIX + ChatColor.YELLOW + "Meinst du " + ChatColor.GOLD + "\"" + event.getMessage().replace("7", "/") + ChatColor.YELLOW + "\" ?");
                    return;
                }
                if (player.hasPermission("essentials.vanish.chat")) {
                    player.sendMessage(ChatColor.YELLOW + "[Vanished] " + ChatColor.RESET + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCommandSend(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().contains("/deop")) {
            String msg = event.getMessage().replace("/deop ", "");
            if (Bukkit.getOfflinePlayer(msg).getPlayer() != null) {
                Player player = Bukkit.getPlayer(msg);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (plugin.getConfig().getBoolean("vanish.player." + p.getUniqueId())) {
                        player.hidePlayer(p);
                    }
                }
            }
        }
        if (event.getMessage().contains("/op")) {
            String msg = event.getMessage().replace("/op ", "");
            if (Bukkit.getOfflinePlayer(msg).getPlayer() != null) {
                Player player = Bukkit.getPlayer(msg);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(p);
                }
            }
        }
    }


}
