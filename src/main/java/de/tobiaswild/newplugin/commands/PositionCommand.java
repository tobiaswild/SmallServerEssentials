package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.PositionManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class PositionCommand implements CommandExecutor, TabCompleter {

    private final PositionManager positionManager = Main.getInstance().getPositionManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.NO_PLAYER);
            return false;
        }
        if (!player.hasPermission(Main.PERMISSION + "position")) {
            player.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (args.length >= 1) {
            switch (args[0]) {
                case "create", "add" -> {
                    if (args.length <= 1) {
                        player.sendMessage(Main.ERROR + "Please enter a name for this position");
                        return false;
                    }
                    String posName = args[1];
                    if (positionManager.getPositions().contains(posName)) {
                        player.sendMessage(Main.ERROR + "Config does already contain a position with this name");
                        return false;
                    }
                    Location location = new Location(
                            player.getLocation().getWorld(),
                            (int) player.getLocation().getX(),
                            (int) player.getLocation().getY(),
                            (int) player.getLocation().getZ());
                    positionManager.setPosition(posName, location);
                    player.sendMessage(Main.SUCCESS + "Position " + posName + " created");
                    return true;
                }
                case "delete", "remove" -> {
                    if (args.length <= 1) {
                        player.sendMessage(Main.ERROR + "Please enter a name for this position");
                        return false;
                    }
                    String posName = args[1];
                    if (!positionManager.getPositions().contains(posName)) {
                        player.sendMessage(Main.ERROR + "Config does not contain a position with this name");
                        return false;
                    }
                    positionManager.deletePosition(posName);
                    player.sendMessage(Main.SUCCESS + "Position " + posName + " removed");
                    return true;
                }
                case "show", "get" -> {
                    if (args.length <= 1) {
                        player.sendMessage(Main.ERROR + "Please enter the name of a position");
                        return false;
                    }
                    String posName = args[1];
                    if (!positionManager.getPositions().contains(posName)) {
                        player.sendMessage(Main.ERROR + "Config does not contain a position with this name");
                        return false;
                    }
                    Location location = positionManager.getPosition(posName);
                    int x = location.getBlockX();
                    int y = location.getBlockY();
                    int z = location.getBlockZ();
                    player.sendMessage(Main.SUCCESS + "Position " + posName + " found: x" + x + " y" + y + " z" + z);
                    return true;
                }
                case "list" -> {
                    Set<String> positions = positionManager.getPositions();
                    player.sendMessage(
                            Main.SUCCESS + "All currently available positions: " + Arrays.toString(positions.toArray())
                                    .replace("[", "").replace("]", ""));
                }
                default -> {
                    player.sendMessage(Main.ERROR + "error");
                    return false;
                }
            }
        }
        player.sendMessage(Main.ERROR + "error");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            list.add("create");
            list.add("remove");
            list.add("show");
            list.add("list");
        }
        if (args.length == 2 && !args[1].equals("list")) {
            list.addAll(positionManager.getPositions());
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();
        for (String s : list) {
            if (s.startsWith(current)) {
                comList.add(s);
            }
        }
        return comList;
    }
}
