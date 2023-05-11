package com.imjona.customjoinevents.commands;

import com.imjona.customjoinevents.CustomJoinEvents;
import com.imjona.customjoinevents.manager.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.imjona.customjoinevents.utils.UtilsPlugin.sendMessageToPlayer;

public class HelpCommand extends SubCommand {
    @Override
    public String getPermission() {
        return "customjoinevents.admin.help";
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission(getPermission())) {
            sendMessageToPlayer(player, "&cNo tienes permisos");
            return;
        }
        sendHelp(player);
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }

    private void sendHelp(Player sender) {
        sendMessageToPlayer(sender, "&8&m-------------------------------------");
        sendMessageToPlayer(sender, "&e&l» &b&lCustomJoin&f&lEvents &8- &7" + CustomJoinEvents.get().getVersion() + "&e&l «");
        sendMessageToPlayer(sender, "&fCommand aliases: &b/cj - /cje");
        sendMessageToPlayer(sender, "");
        sendMessageToPlayer(sender, "&a&l* &b/customjoinevents &9- &fOpen the main menu");
        sendMessageToPlayer(sender, "&a&l* &b/customjoinevents help &9- &fSend this message");
        sendMessageToPlayer(sender, "&a&l* &b/customjoinevents reload &9- &fReload config");
        sendMessageToPlayer(sender, "&a&l* &b/customjoinevents save &9- &fSave player data");
        //sendMessageToPlayer(sender, "&a&l* &b/cj open <menu> <player> &9- &fOpen a menu specifically for a player");
        //sendMessageToPlayer(sender, "&a&l* &b/cj create <type> &9- &fCrea un nuevo evento mediante menu");
        sendMessageToPlayer(sender, "");
        sendMessageToPlayer(sender, "&b&lGet support at my discord: &f_ImJona#2065");
        sendMessageToPlayer(sender, "&8&m-------------------------------------");
    }
}
