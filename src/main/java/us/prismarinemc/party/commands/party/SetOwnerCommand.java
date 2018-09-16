package us.prismarinemc.party.commands.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.prismarinemc.party.commands.SubCommand;

public class SetOwnerCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (Bukkit.getServer().getPlayer(args[0]) == null) {
            p.sendMessage(prefix + ChatColor.RED + "This player is not online");
        } else {
            for (Player newOwner : party.getPlayers()) {
                if (newOwner.getName().equalsIgnoreCase(args[0])) {
                    party.setLeader(newOwner);
                    return;
                }
            }
            p.sendMessage(prefix + ChatColor.RED + "This player is not in the party");
        }

    }

    @Override
    public String getName() {
        return "setowner";
    }

    @Override
    public String getDescription() {
        return "Changes the party owner";
    }

    @Override
    public String[] getAliases() {
        return new String[0];
    }

    @Override
    public String getUsage() {
        return "/party setowner <Player>";
    }

    @Override
    public int getArgsRequired() {
        return 1;
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
