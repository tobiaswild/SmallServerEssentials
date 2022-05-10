package de.tobiaswild.sse.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import de.tobiaswild.sse.Main;
import de.tobiaswild.sse.utils.FreezeManager;

public class MoveListener implements Listener {

    private final FreezeManager freezeManager = Main.getInstance().getFreezeManager();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!freezeManager.getFrozenPlayers().contains(player.getUniqueId()))
            return;
        Location playerLocation = player.getLocation();
        Location freezeLocation = freezeManager.getFreezePosition(player.getUniqueId());
        if (playerLocation == freezeLocation)
            return;
        player.teleport(freezeLocation);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 10, 10);
    }

    @EventHandler
    public void onPlayerPortal(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Bukkit.broadcastMessage(
                player.getDisplayName() + " switch from " + event.getFrom().getName() + " to "
                        + player.getWorld().getName());
    }
}
