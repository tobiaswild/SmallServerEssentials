package de.tobiaswild.newplugin.listeners;

import com.mysql.cj.jdbc.jmx.LoadBalanceConnectionGroupManager;
import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.FreezeManager;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    private final FreezeManager freezeManager = Main.getInstance().getFreezeManager();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!freezeManager.getFrozenPlayers().contains(player.getUniqueId())) return;
        Location playerLocation = player.getLocation();
        Location freezeLocation = freezeManager.getFreezePosition(player.getUniqueId());
        if (playerLocation == freezeLocation) return;
        player.teleport(freezeLocation);
    }
}
