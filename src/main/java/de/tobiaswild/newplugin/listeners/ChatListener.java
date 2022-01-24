package de.tobiaswild.newplugin.listeners;

import de.tobiaswild.newplugin.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(player.getDisplayName() + ": " + event.getMessage());
        if (event.getMessage().startsWith("7")) {
            player.sendMessage(Main.INFO + "Did you mean \"" + event.getMessage().replace("7", "/") + "\" ?");
            event.setCancelled(true);
        }
    }
}
