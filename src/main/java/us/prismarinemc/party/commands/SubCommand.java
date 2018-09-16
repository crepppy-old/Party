package us.prismarinemc.party.commands;

import org.bukkit.entity.Player;
import us.prismarinemc.party.Party;
import us.prismarinemc.party.PartyObject;

public abstract class SubCommand {
    protected String prefix = Party.prefix;
    protected PartyObject party = null;

    public abstract void onCommand(Player p, String[] args);

    public abstract String getName();

    public abstract String getDescription();

    public abstract String[] getAliases();

    public abstract String getUsage(); // /party <command> <args>

    public abstract int getArgsRequired(); //Starts at 1

    public abstract boolean getPartyRequired();

    public abstract boolean getLeaderRequired();

    public void setParty(PartyObject party) {
        this.party = party;
    }

}
