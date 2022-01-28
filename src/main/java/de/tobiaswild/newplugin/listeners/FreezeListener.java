package de.tobiaswild.newplugin.listeners;

import de.tobiaswild.newplugin.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    private Main plugin;

    public FreezeListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!plugin.getFrozenPlayers().containsKey(event.getPlayer())) {
            return;
        }
        if ((int) event.getFrom().getBlockX() != (int) event.getTo().getBlockX() ||
                (int) event.getFrom().getBlockY() != (int) event.getTo().getBlockY() ||
                (int) event.getFrom().getBlockZ() != (int) event.getTo().getBlockZ()) {
            event.getPlayer().teleport(plugin.getFrozenPlayers().get(event.getPlayer()));
        }
    }
}
