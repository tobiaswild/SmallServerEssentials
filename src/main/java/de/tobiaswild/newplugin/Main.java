package de.tobiaswild.newplugin;

import de.tobiaswild.newplugin.commands.BackpackCommand;
import de.tobiaswild.newplugin.commands.TimerCommand;
import de.tobiaswild.newplugin.timer.Timer;
import de.tobiaswild.newplugin.backpack.BackpackManager;
import de.tobiaswild.newplugin.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private Timer timer;
    private Config config;
    private BackpackManager backpackManager;

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        backpackManager = new BackpackManager();
        timer = new Timer();
        getCommands();
    }

    @Override
    public void onDisable() {
        backpackManager.save();
        timer.save();
        config.save();
    }

    public void getCommands() {
        this.getCommand("backpack").setExecutor(new BackpackCommand());
        this.getCommand("timer").setExecutor(new TimerCommand());
    }

    public static String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.BLUE + "NewPlugin" + ChatColor.GRAY + "]";
    }
    public static void noPlayer(CommandSender sender) {
        sendToSender(sender, Main.getPrefix() + ChatColor.RED + "You have to be a player.");
    }
    public static void noPermission(CommandSender sender) {
        sendToSender(sender,Main.getPrefix() + ChatColor.RED + "You are not allowed to do this.");
    }
    public static void notPossible(CommandSender sender) {
        sendToSender(sender,Main.getPrefix() + ChatColor.RED + "this is currently not possible.");
    }
    public static void sendToSender(CommandSender sender, String message) {
        sender.sendMessage(Main.getPrefix() + " " + message);
    }
    public static void sendToAll(String message) {
        Main.getInstance().getServer().broadcastMessage(Main.getPrefix() + " " + message);
    }

    public static Main getInstance() {return instance;}
    public Config getConfiguration() {return config;}
    public BackpackManager getBackpackManager() {return backpackManager;}
    public Timer getTimer() {return timer;}

}
