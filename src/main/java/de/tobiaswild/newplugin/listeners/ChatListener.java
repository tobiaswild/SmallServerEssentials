package de.tobiaswild.newplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import de.tobiaswild.newplugin.Main;

public class ChatListener implements Listener {

    private Main plugin;
    private YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();

    public ChatListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        event.setFormat(event.getPlayer().getDisplayName() + ": " + event.getMessage());
        if (event.getMessage().startsWith("7")) {
            event.setCancelled(true);
            event.getPlayer()
                    .sendMessage(Main.INFO + "Did you mean \"" + event.getMessage().replace("7", "/") + "\" ?");
            return;
        }
        if (config.getBoolean("vanish." + event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    player.sendMessage(ChatColor.BLUE + "[Vanished] " + ChatColor.RESET
                            + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCommandSend(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().contains("/deop")) {
            String msg = event.getMessage().replace("/deop ", "");
            if (Bukkit.getPlayer(msg) != null) {
                Player player = Bukkit.getPlayer(msg);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (config.getBoolean("vanish." + p.getUniqueId())) {
                        player.hidePlayer(plugin, p);
                    }
                }
            }
        }
        if (event.getMessage().contains("/op")) {
            String msg = event.getMessage().replace("/op ", "");
            if (Bukkit.getPlayer(msg) != null) {
                Player player = Bukkit.getPlayer(msg);
                for (Player p : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(plugin, p);
                }
            }
        }
    }
}
