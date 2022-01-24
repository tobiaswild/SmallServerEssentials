package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ShutdownCommand implements CommandExecutor, TabCompleter {

    private int count;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Main.PERMISSION + "shutdown")) {
            return false;
        }
        setCount(Integer.parseInt(args[0]));
        run();
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public void sendUsage(CommandSender sender) {

    }

    public void sendToChat() {
        Bukkit.broadcastMessage(ChatColor.GOLD + "Server wird in " + getCount() + " Sekunden geschlossen!");
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                switch (getCount()) {
                    case 120, 90, 60, 45, 30, 15, 10, 5, 3, 2, 1:
                        sendToChat();
                        break;
                    default:
                        break;
                }
                setCount(getCount()-1);
                if (getCount() == -1) Bukkit.shutdown();
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
