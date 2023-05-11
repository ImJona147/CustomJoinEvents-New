package com.imjona.customjoinevents.manager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {

    @SafeVarargs
    public static void createCoreCommand(String commandName,
                                         String commandDescription,
                                         String commandUsage,
                                         List<String> aliases,
                                         Class<? extends SubCommand>... subCommands) {
        ArrayList<SubCommand> commands = new ArrayList<>();
        Arrays.stream(subCommands).map(subCommand -> {
            try {
                Constructor<? extends SubCommand> constructor = subCommand.getConstructor();
                return constructor.newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }).forEach(commands::add);
        CommandMap commandMap;
        try {
            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getPluginManager());
            commandMap.register(commandName, new CoreCommand(commandName, commandDescription, commandUsage, aliases, commands));
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }
    }
}
