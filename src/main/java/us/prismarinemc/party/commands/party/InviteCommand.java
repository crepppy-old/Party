package us.prismarinemc.party.commands.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.prismarinemc.party.PartyObject;
import us.prismarinemc.party.commands.SubCommand;

public class InviteCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        for (int i = 0; i < args.length; i++) {
            boolean bool = false;
            for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
                if (pl.getName().equalsIgnoreCase(args[i])) {
                    bool = true;
                    break;
                }
            }
            if (bool) {
                if (Bukkit.getPlayer(args[i]) == p) {
                    p.sendMessage(prefix + ChatColor.RED + "You can't invite yourself to a party");
                    continue;
                }
                if (party == null) {
                    new PartyObject(p).invitePlayer(Bukkit.getPlayer(args[i]));
                } else {
                    if (party.getLeader() == p) {
                        if (party.getInvitedPlayers().contains(Bukkit.getPlayer(args[i])))
                            p.sendMessage(prefix + ChatColor.RED + args[i] + " has already been invited to this party");
                        else
                            party.invitePlayer(Bukkit.getPlayer(args[i]));
                    } else {
                        p.sendMessage(prefix + ChatColor.RED + "You are not the leader of this party");
                    }
                }
            } else {
                p.sendMessage(prefix + ChatColor.RED + args[i] + " isn't online at the moment");
            }
        }
        return;
    }

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invites a player to the party";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return "/party invite <Player1> [Player2] [Player3]...";
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
