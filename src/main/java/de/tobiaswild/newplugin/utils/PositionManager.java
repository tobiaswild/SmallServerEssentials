package de.tobiaswild.newplugin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import de.tobiaswild.newplugin.Main;

public class PositionManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final Map<String, Location> map;

    public PositionManager() {
        map = new HashMap<>();
        load();
    }

    public Location getPosition(String name) {
        if (map.containsKey(name)) {
            return map.get(name);
        }
        return null;
    }

    public void setPosition(String name, Location location) {
        map.put(name, location);
    }

    public void deletePosition(String name) {
        map.remove(name);
    }

    private void load() {
        List<String> names = config.getStringList("positions");
        names.forEach(s -> {
            Location location = config.getLocation("position." + s);
            map.put(s, location);
        });
    }

    public void save() {
        List<String> names = new ArrayList<>(map.keySet());
        config.set("positions", names);
        map.forEach((name, position) -> config.set("position." + name, position));
    }

    public Set<String> getPositions() {
        return map.keySet();
    }
}
