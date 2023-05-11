package com.imjona.customjoinevents.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilsPlugin {

    public static String prefix = "&8[&bCustomJoin&fEvents&8] ";

    public static String fixColorMessagess(String message) {
        if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18") ||
                Bukkit.getVersion().contains("1.19")) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher match = pattern.matcher(message);
            while (match.find()) {
                String color = message.substring(match.start(), match.end());
                message = message.replace(color, ChatColor.of(color).toString());
                match = pattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String fixColorMessages(String message) {
        if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.17") || Bukkit.getVersion().contains("1.18") ||
                Bukkit.getVersion().contains("1.19")) {
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher match = pattern.matcher(message);
            while (match.find()) {
                String color = message.substring(match.start(), match.end());
                message = message.replace(color, ChatColor.of(color).toString());
                match = pattern.matcher(message);
            }
            Pattern gradientPattern = Pattern.compile("(<#([a-fA-F0-9]{6})>.*?<#([a-fA-F0-9]{6})>)");
            Matcher gradientMatch = gradientPattern.matcher(message);
            while (gradientMatch.find()) {
                String gradientStart = gradientMatch.group(2);
                String gradientEnd = gradientMatch.group(3);
                String gradientText = gradientMatch.group().substring(2, gradientMatch.group().length() - 2);
                message = message.replace(gradientMatch.group(),
                        gradient(gradientStart, gradientEnd, gradientText));
                gradientMatch = gradientPattern.matcher(message);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String gradient(String start, String end, String text) {
        int gradientSize = text.length();
        StringBuilder gradientBuilder = new StringBuilder();
        for (int i = 0; i < gradientSize; i++) {
            double ratio = (double) i / (double) gradientSize;
            int r = (int) (Integer.parseInt(start.substring(0, 2), 16) * (1 - ratio) + Integer.parseInt(end.substring(0, 2), 16) * ratio);
            int g = (int) (Integer.parseInt(start.substring(2, 4), 16) * (1 - ratio) + Integer.parseInt(end.substring(2, 4), 16) * ratio);
            int b = (int) (Integer.parseInt(start.substring(4, 6), 16) * (1 - ratio) + Integer.parseInt(end.substring(4, 6), 16) * ratio);
            gradientBuilder.append(ChatColor.of(new Color(r, g, b)).toString()).append(text.charAt(i));
        }
        return gradientBuilder.toString();
    }


    public static void sendMessageToPlayer(CommandSender player, String message) {
        player.sendMessage(fixColorMessages(message));
    }

    public static void sendMessageToConsole(String message) {
        ConsoleCommandSender sender = Bukkit.getServer().getConsoleSender();
        sender.sendMessage(fixColorMessages(prefix + message));
    }

    public static Boolean getPlugin(String pluginName) {
        return Bukkit.getServer().getPluginManager().getPlugin(pluginName) != null;
    }
}
