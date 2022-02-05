package de.tobiaswild.newplugin.listeners;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.FreezeManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    private final FreezeManager freezeManager = Main.getInstance().getFreezeManager();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!freezeManager.getFrozenPlayers().contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if ((int) event.getFrom().getBlockX() != (int) event.getTo().getBlockX() ||
                (int) event.getFrom().getBlockY() != (int) event.getTo().getBlockY() ||
                (int) event.getFrom().getBlockZ() != (int) event.getTo().getBlockZ()) {
            event.getPlayer().teleport(freezeManager.getFreezePosition(event.getPlayer().getUniqueId()));
        }
    }
}
