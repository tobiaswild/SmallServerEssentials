package de.tobiaswild.newplugin.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import de.tobiaswild.newplugin.Main;
import de.tobiaswild.newplugin.utils.Timer;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final Timer timer = Main.getInstance().getTimer();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Main.PERMISSION + "timer")) {
            sender.sendMessage(Main.NO_PERMISSION);
            return false;
        }
        if (args.length == 0) {
            sendUsage(sender);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "resume", "start" -> {
                if (!timer.isHidden()) {
                    if (timer.isRunning()) {
                        sender.sendMessage(Main.ERROR + "timer already running");
                        return false;
                    }
                    timer.setRunning(true);
                    Bukkit.broadcastMessage(Main.SUCCESS + "timer started");
                    return true;
                }
                sender.sendMessage(Main.NOT_POSSIBLE);
                return false;
            }
            case "pause", "stop" -> {
                if (!timer.isHidden()) {
                    if (!timer.isRunning()) {
                        sender.sendMessage(Main.ERROR + "timer already stopped");
                        return false;
                    }
                    timer.setRunning(false);
                    Bukkit.broadcastMessage(Main.SUCCESS + "timer stopped");
                    return true;
                }
                sender.sendMessage(Main.NOT_POSSIBLE);
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
                        sender.sendMessage(Main.ERROR + "second parameter has to be a int");
                    }
                    break;
                }
                sender.sendMessage(Main.NOT_POSSIBLE);
                return false;
            }
            case "reset" -> {
                if (!timer.isHidden()) {
                    timer.setRunning(false);
                    timer.setTime(0);
                    sender.sendMessage(Main.SUCCESS + "timer reset");
                    return true;
                }
                sender.sendMessage(Main.NOT_POSSIBLE);
                return false;
            }
            case "show" -> {
                if (timer.isHidden()) {
                    timer.setHidden(false);
                    sender.sendMessage(Main.SUCCESS + "timer now shown");
                    return true;
                } else {
                    sender.sendMessage(Main.ERROR + "timer already shown");
                    return false;
                }
            }
            case "hide" -> {
                if (!timer.isHidden()) {
                    timer.setHidden(true);
                    sender.sendMessage(Main.SUCCESS + "timer now hidden");
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
        sendUsage(sender);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            if (timer.isHidden()) {
                list.add("show");
            } else {
                list.add("hide");
                if (timer.isRunning()) {
                    list.add("stop");
                    list.remove("hide");
                } else {
                    list.add("start");
                    list.add("set");
                    list.add("reset");
                }
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length - 1].toLowerCase();
        for (String s1 : list) {
            if (s1.startsWith(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }

    public static void sendUsage(CommandSender sender) {
        sender.sendMessage("Usage: /timer resume, pause, set <time>, reset, show, hide");
    }
}
