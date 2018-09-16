package us.prismarinemc.party.commands.party;

import org.bukkit.entity.Player;
import us.prismarinemc.party.commands.SubCommand;

public class DisbandCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        party.disband();
    }

    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "Disbands the player's current party.";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return "/party disband";
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
        return true;
    }
}
