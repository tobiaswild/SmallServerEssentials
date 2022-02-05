package de.tobiaswild.newplugin.utils;

import de.tobiaswild.newplugin.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class FreezeManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final HashMap<UUID, Location> frozenPlayers;

    public FreezeManager() {
        frozenPlayers = new HashMap<>();
        load();
    }

    public Location getFreezePosition(UUID uuid) {
        if (frozenPlayers.containsKey(uuid)) {
            return frozenPlayers.get(uuid);
        }
        return null;
    }

    public void setFreezePosition(UUID uuid, Location location) {
        frozenPlayers.put(uuid, location);
    }

    public void deleteFreezePosition(UUID uuid) {
        frozenPlayers.remove(uuid);
    }

    private void load() {
        List<String> uuids = config.getStringList("frozen");
        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            Location location = config.getLocation("freeze." + s);
            try {
                frozenPlayers.put(uuid, location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {
        List<String> uuids = new ArrayList<>();
        for (UUID uuid : frozenPlayers.keySet()) {
            uuids.add(uuid.toString());
        }
        config.set("frozen", uuids);
        frozenPlayers.forEach((uuid, location) -> config.set("freeze." + uuid.toString(), location));
    }

    public Set<UUID> getFrozenPlayers() {
        return frozenPlayers.keySet();
    }
}
