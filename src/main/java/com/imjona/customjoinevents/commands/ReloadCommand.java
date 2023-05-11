package com.imjona.customjoinevents.commands;

import com.imjona.customjoinevents.CustomJoinEvents;
import com.imjona.customjoinevents.manager.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.imjona.customjoinevents.utils.UtilsPlugin.sendMessageToPlayer;

public class ReloadCommand extends SubCommand {

    @Override
    public String getPermission() {
        return "customjoinevents.admin.reload";
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public List<String> getAliases() {
        return null;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission(getPermission())) {
            sendMessageToPlayer(player, "&aLa configuraciób ha sido recargada");
            CustomJoinEvents.get().getLangManager().getLanguage().load();
        }
    }

    @Override
    public List<String> getSubCommandArguments(Player player, String[] args) {
        return null;
    }
}
