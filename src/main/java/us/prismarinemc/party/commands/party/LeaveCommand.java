package us.prismarinemc.party.commands.party;

import org.bukkit.entity.Player;
import us.prismarinemc.party.commands.SubCommand;

public class LeaveCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (party.getLeader() == p)
            party.setLeader(party.getPlayers().get(0));
        party.removePlayer(p);
        p.sendMessage(prefix + "You have left the party");
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leaves the player's current party.";
    }

    @Override
    public String[] getAliases() {
        String[] aliases = new String[1];
        aliases[0] = "quit";
        return aliases;
    }

    @Override
    public String getUsage() {
        return "/party leave";
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
