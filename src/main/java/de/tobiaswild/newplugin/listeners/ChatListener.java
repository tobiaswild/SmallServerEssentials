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

import java.util.Objects;

public class ChatListener implements Listener {

    private YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        event.setFormat(player.getDisplayName() + ": " + event.getMessage());
        if (message.startsWith("7")) {
            event.setCancelled(true);
            player.sendMessage(Main.INFO + "Did you mean \"" + message.replace("7", "/") + "\" ?");
            return;
        }
        if (config.getBoolean("vanish." + event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(Main.PERMISSION + "vanish.chat")) {
                    p.sendMessage(ChatColor.BLUE + "[Vanished] " + ChatColor.RESET
                            + event.getPlayer().getDisplayName() + ": " + event.getMessage());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerCommandSend(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().startsWith("/deop")) {
            String player = event.getMessage().replace("/deop ", "");
            if (Bukkit.getPlayer(player) != null) {
                Objects.requireNonNull(Bukkit.getPlayer(player)).kickPlayer(Main.ERROR + "You got deopoed.");
            }
        }
    }
}
