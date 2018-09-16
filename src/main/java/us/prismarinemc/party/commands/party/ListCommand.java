package us.prismarinemc.party.commands.party;

import org.bukkit.entity.Player;
import us.prismarinemc.party.PartyObject;
import us.prismarinemc.party.commands.SubCommand;

public class ListCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        StringBuilder people = new StringBuilder(PartyObject.findParty(p).getLeader().getName());
        for (Player u : PartyObject.findParty(p).getPlayers()) people.append(", ").append(u.getName());
        p.sendMessage(prefix + "Players currently in the party: " + people.toString());
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return "Lists current party members.";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return "/party list";
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
