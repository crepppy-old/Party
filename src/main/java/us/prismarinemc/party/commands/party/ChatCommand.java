package us.prismarinemc.party.commands.party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.prismarinemc.party.commands.SubCommand;

public class ChatCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (args.length == 0) {
            if (party.getInPartyChat().contains(p)) {
                party.getInPartyChat().remove(p);
            } else party.getInPartyChat().add(p);
        } else {
            String msg = "";
            for (int i = 0; i < args.length; i++) {
                msg = args[i] + " ";
            }
            party.broadcast(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "PartyChat" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + p.getName() + ": " + msg);
        }
    }

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public String getDescription() {
        return "Sends a message in party chat or changes the players chat channel to party chat.";
    }

    @Override
    public String[] getAliases() {
        String[] aliases = new String[1];
        aliases[0] = "c";
        return aliases;
    }

    @Override
    public String getUsage() {
        return "/party chat [message]";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public boolean getPartyRequired() {
        return true;
    }

    @Override
    public boolean getLeaderRequired() {
        return false;
    }
}
