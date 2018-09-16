package us.prismarinemc.party.commands.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.prismarinemc.party.Party;
import us.prismarinemc.party.PartyObject;
import us.prismarinemc.party.commands.SubCommand;

public class JoinCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (Bukkit.getServer().getPlayer(args[0]) == null)
            p.sendMessage(prefix + ChatColor.RED + "This person is not online");
        else {
            if (PartyObject.findParty(Bukkit.getServer().getPlayer(args[0])) == null) {
                p.sendMessage(prefix + ChatColor.RED + "This person isn't in a party");
            } else {
                if (party != null)
                    p.sendMessage(Party.prefix + "Please leave you party before joining a new one");
                else
                    PartyObject.findParty(Bukkit.getServer().getPlayer(args[0])).addPlayers(p);
            }
        }
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Joins a players party";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return "/party join <Player>";
    }

    @Override
    public int getArgsRequired() {
        return 1;
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
