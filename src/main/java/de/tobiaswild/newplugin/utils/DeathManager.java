package de.tobiaswild.newplugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import de.tobiaswild.newplugin.Main;

public class DeathManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final HashMap<UUID, Location> deathPlayers;

    public DeathManager() {
        deathPlayers = new HashMap<>();
        load();
    }

    public Location getDeathPlayerLocation(UUID uuid) {
        if (deathPlayers.containsKey(uuid)) {
            return deathPlayers.get(uuid);
        }
        return null;
    }

    public void setDeathPlayer(UUID uuid, Location location) {
        deathPlayers.put(uuid, location);
    }

    public void deleteDeathPlayer(UUID uuid) {
        deathPlayers.remove(uuid);
    }

    private void load() {
        List<String> uuids = config.getStringList("dead");
        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            Location location = config.getLocation("death." + s);
            try {
                deathPlayers.put(uuid, location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void save() {
        List<String> uuids = new ArrayList<>();
        for (UUID uuid : deathPlayers.keySet()) {
            uuids.add(uuid.toString());
        }
        config.set("dead", uuids);
        deathPlayers.forEach((uuid, location) -> config.set("death." + uuid.toString(), location));
    }

    public Set<UUID> getDeathPlayers() {
        return deathPlayers.keySet();
    }
}
