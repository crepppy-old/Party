package us.prismarinemc.party;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.prismarinemc.party.commands.SubCommand;
import us.prismarinemc.party.commands.party.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements CommandExecutor {
    private static List<SubCommand> commands;

    public CommandManager() {
        commands = new ArrayList<>();
        commands.add(new HelpCommand());
        commands.add(new InviteCommand());
        commands.add(new JoinCommand());
        commands.add(new LeaveCommand());
        commands.add(new ListCommand());
        commands.add(new ChatCommand());
        commands.add(new DisbandCommand());
        commands.add(new RemoveCommand());
        commands.add(new SetOwnerCommand());
        commands.add(new DiscordCommand());
    }

    public static List<SubCommand> getCommands() {
        return commands;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Party.prefix + ChatColor.RED + "You must be a player to execute this command");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0 || get(args[0]) == null) {
            p.performCommand("party help");
            return true;
        }
        SubCommand command = get(args[0]);
        //party invite name

        if (!(args.length - 1 >= command.getArgsRequired()))
            p.sendMessage(Party.prefix + ChatColor.RED + "Incorrect usage: " + command.getUsage());
        else if (command.getPartyRequired() && PartyObject.findParty(p) == null)
            p.sendMessage(Party.prefix + ChatColor.RED + "You aren't in a party");
        else if (command.getLeaderRequired() && PartyObject.findParty(p).getLeader() != p)
            p.sendMessage(Party.prefix + ChatColor.RED + "You are not the leader of this party");
        else {
            if (command.getPartyRequired()) command.setParty(PartyObject.findParty(p));
            command.onCommand(p, Arrays.copyOfRange(args, 1, args.length));
        }
        return true;
    }

    private SubCommand get(String name) {
        for (SubCommand subCommand : commands) {
            if (subCommand.getName().equalsIgnoreCase(name))
                return subCommand;

            for (String alias : subCommand.getAliases()) {
                if (alias.equalsIgnoreCase(name)) return subCommand;
            }
        }
        return null;
    }
}
