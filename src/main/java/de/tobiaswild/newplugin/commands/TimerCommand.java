package de.tobiaswild.newplugin.commands;

import de.tobiaswild.newplugin.utils.timer.Timer;
import de.tobiaswild.newplugin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Timer timer = Main.getInstance().getTimer();

        if (!sender.hasPermission(Main.PERMISSION + "timer")) {
            Main.noPermission(sender);
            return false;
        }
        if (args.length == 0) {
            sendUsage(sender);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "resume" -> {
                if (!timer.isHidden()) {
                    if (timer.isRunning()) {
                        sender.sendMessage(Main.ERROR + "timer already running");
                        return false;
                    }
                    timer.setRunning(true);
                    Main.getInstance().getServer().getConsoleSender().sendMessage(Main.SUCCESS + "timer started");
                    return true;
                }
                Main.notPossible(sender);
                return false;
            }
            case "pause" -> {
                if (!timer.isHidden()) {
                    if (!timer.isRunning()) {
                        sender.sendMessage(Main.ERROR + "timer already stopped");
                        return false;
                    }
                    timer.setRunning(false);
                    Main.getInstance().getServer().getConsoleSender().sendMessage(Main.SUCCESS + "timer stopped");
                    return true;
                }
                Main.notPossible(sender);
                return false;
            }
            case "set" -> {
                if (!timer.isHidden()) {
                    if (args.length != 2) {
                        sender.sendMessage("Usage: /timer time <time>");
                        return false;
                    }
                    try {
                        timer.setRunning(false);
                        timer.setTime(Integer.parseInt(args[1]));
                        sender.sendMessage(Main.SUCCESS + "timer set to " + args[1]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Main.ERROR + "second parameter has to be a int!");
                    }
                    break;
                }
                Main.notPossible(sender);
                return false;
            }
            case "reset" -> {
                if (!timer.isHidden()) {
                    timer.setRunning(false);
                    timer.setTime(0);
                    sender.sendMessage(Main.SUCCESS + "timer reset");
                    return true;
                }
                Main.notPossible(sender);
                return false;
            }
            case "show" -> {
                if (timer.isHidden()) {
                    timer.setHidden(false);
                    Main.getInstance().getServer().getConsoleSender().sendMessage(Main.SUCCESS + "shown");
                    return true;
                } else {
                    sender.sendMessage(Main.ERROR + "timer already shown");
                    return false;
                }
            }
            case "hide" -> {
                if (!timer.isHidden()) {
                    timer.setHidden(true);
                    Main.getInstance().getServer().getConsoleSender().sendMessage(Main.SUCCESS + "hidden");
                    return true;
                } else {
                    sender.sendMessage(Main.ERROR + "timer already hidden");
                    return false;
                }
            }
            default -> {
                sendUsage(sender);
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Timer timer = Main.getInstance().getTimer();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            if (timer.isHidden()) {
                list.add("show");
            } else {
                list.add("hide");
                if (timer.isRunning()) {
                    list.add("pause");
                    list.remove("hide");
                } else {
                    list.add("resume");
                    list.add("set");
                    list.add("reset");
                }
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length-1].toLowerCase();
        for (String s1 : list) {
            if (s1.startsWith(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }

    public static void sendUsage(CommandSender sender) {
        sender.sendMessage("""
                Usage:
                /timer resume
                /timer pause
                /timer set <time>
                /timer reset
                /timer show
                /timer hide"""
        );
    }

}
