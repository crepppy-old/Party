package us.prismarinemc.party.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.prismarinemc.party.Party;
import us.prismarinemc.party.PartyObject;
import us.prismarinemc.party.bot.Link;

import java.util.Random;

public class BotCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = Party.prefix;
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + ChatColor.RED + "Only players may execute this command");
        }
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("link")) {
            if (Party.linkedAccounts.containsKey(p)) {
                p.sendMessage(prefix + ChatColor.RED + "Your account is already linked to discord user " + Party.linkedAccounts.get(p).getName()+"#"+Party.linkedAccounts.get(p).getDiscriminator());
            } else {
                int i = new Random().nextInt(1000000);
                while (Link.link.containsKey(i)) i = new Random().nextInt(1000000);
                Link.link.put(i, p);
                p.sendMessage(prefix + "Please run the ^link " + i + " in the official PrismarineMC discord server to link your account");
            }
        }
        if (cmd.getName().equalsIgnoreCase("unlink")) {
            if (Party.linkedAccounts.containsKey(p)) {
                if (PartyObject.findParty(p) != null && PartyObject.findParty(p).getVc() != null && PartyObject.findParty(p).getVc().getMembers().contains(PartyObject.findParty(p).getVc().getGuild().getMember(Party.linkedAccounts.get(p))))
                    PartyObject.findParty(p).getVc().getGuild().getController().moveVoiceMember(PartyObject.findParty(p).getVc().getGuild().getMember(Party.linkedAccounts.get(p)), PartyObject.findParty(p).getVc().getGuild().getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby"))).queue();
                Party.linkedAccounts.remove(p);
                p.sendMessage(prefix + "Removed linked account");
            } else {
                p.sendMessage(prefix + ChatColor.RED + "Your minecraft account isn't currently linked with discord");
            }
        }
        return true;
    }
}
