package us.prismarinemc.party.commands.party;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.prismarinemc.party.CommandManager;
import us.prismarinemc.party.commands.SubCommand;

public class HelpCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        p.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "--------" + ChatColor.RESET + ChatColor.DARK_AQUA + "Party Commands:" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + "--------");
        for (SubCommand s : CommandManager.getCommands()) {
            p.sendMessage(ChatColor.DARK_AQUA + s.getUsage() + ChatColor.GRAY + " » " + s.getDescription());
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Sends the party help message.";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return "/party help";
    }

    @Override
    public int getArgsRequired() {
        return 0;
    }

    @Override
    public boolean getPartyRequired() {
        return false;
    }

    @Override
    public boolean getLeaderRequired() {
        return false;
    }
}
