package de.tobiaswild.newplugin.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import de.tobiaswild.newplugin.Main;

public class BuildManager {

    private final YamlConfiguration config = Main.getInstance().getConfiguration().getConfig();
    private final Map<UUID, ItemStack[]> map;

    public BuildManager() {
        map = new HashMap<>();
        load();
    }

    public ItemStack[] getItems(UUID uuid) {
        if (map.containsKey(uuid)) {
            return map.get(uuid);
        }
        ItemStack[] items = new ItemStack[] {};
        return items;
    }

    public void setItems(UUID uuid, ItemStack[] items) {
        map.put(uuid, items);
    }

    private void load() {
        List<String> uuids = config.getStringList("buildInvs");
        uuids.forEach(s -> {
            UUID uuid = UUID.fromString(s);
            String base64 = config.getString("buildInv." + s);
            ItemStack[] items = new ItemStack[] {};
            try {
                items = Base64.itemStackArrayFromBase64(base64);
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put(uuid, items);
        });
    }

    public void save() {
        List<String> uuids = new ArrayList<>();
        for (UUID uuid : map.keySet()) {
            uuids.add(uuid.toString());
        }
        config.set("buildInvs", uuids);
        map.forEach((uuid, items) -> config.set("buildInv." + uuid.toString(), toBase64(items)));
    }

    public String toBase64(ItemStack[] items) {
        return Base64.itemStackArrayToBase64(items);
    }
}
