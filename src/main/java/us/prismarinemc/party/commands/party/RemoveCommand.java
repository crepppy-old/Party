package us.prismarinemc.party.commands.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.prismarinemc.party.commands.SubCommand;

public class RemoveCommand extends SubCommand {
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
                if (party.getPlayers().contains((Bukkit.getServer().getPlayer(args[i]))))
                    party.removePlayer(Bukkit.getServer().getPlayer(args[i]));
                else p.sendMessage(prefix + ChatColor.RED + "This person is not in your party");
            } else {
                p.sendMessage(prefix + ChatColor.RED + "This person is not in your party");
            }

        }
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getDescription() {
        return "Removes a player from the party";
    }

    @Override
    public String[] getAliases() {
        String[] aliases = new String[1];
        aliases[0] = "kick";
        return aliases;
    }

    @Override
    public String getUsage() {
        return "/party remove <Player1> [Player2] [Player3]...";
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
