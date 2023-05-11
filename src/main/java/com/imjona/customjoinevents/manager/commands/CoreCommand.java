package com.imjona.customjoinevents.manager.commands;

import com.imjona.customjoinevents.CustomJoinEvents;
import com.imjona.customjoinevents.menus.MainMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.imjona.customjoinevents.utils.UtilsPlugin.sendMessageToConsole;

public class CoreCommand extends Command {
    private final ArrayList<SubCommand> subCommands;

    protected CoreCommand(String name, String description, String usageMessage, List<String> aliases, ArrayList<SubCommand> subCommands) {
        super(name, description, usageMessage, aliases);
        this.subCommands = subCommands;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessageToConsole("&cOnly players can run this command");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            new MainMenu(CustomJoinEvents.get()).mainMenu(player);
        } else if (args.length == 1) {
            for (SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.getName()) ||
                    subCommand.getAliases() != null && subCommand.getAliases().contains(args[0])) {
                    subCommand.perform(sender, args);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        for (SubCommand subCommand : subCommands) {
            if (sender.hasPermission(subCommand.getPermission())) {
                if (args.length == 1) {
                    ArrayList<String> subcommandsArguments = new ArrayList<>();
                    for (int i = 0; i < getSubCommands().size(); i++) {
                        subcommandsArguments.add(getSubCommands().get(i).getName());
                    }
                    return subcommandsArguments;
                } else if (args.length >= 2) {
                    for (int i = 0; i < getSubCommands().size(); i++) {
                        if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                            List<String> subCommandArgs = getSubCommands().get(i).getSubCommandArguments((Player) sender, args
                            );
                            return Objects.requireNonNullElse(subCommandArgs, Collections.emptyList());
                        }
                    }
                }
            }
        }
        return Collections.emptyList();
    }
}
