package us.prismarinemc.party.commands.party;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import us.prismarinemc.party.Party;
import us.prismarinemc.party.bot.Bot;
import us.prismarinemc.party.commands.SubCommand;

import static us.prismarinemc.party.Party.linkedAccounts;
import static us.prismarinemc.party.bot.Bot.jda;

public class DiscordCommand extends SubCommand {
    @Override
    public void onCommand(Player p, String[] args) {
        if (!Party.discord)
            p.sendMessage(prefix + ChatColor.RED + "This command is disabled. Please message a staff member if you think this is a bug.");
        else {
            if (linkedAccounts.get(p) == null)
                p.sendMessage(prefix + ChatColor.RED + "Your discord isn't linked to your account");
            else {
                Guild g = jda.getGuilds().get(0);
                if (g.getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby")).getMembers().contains(g.getMember(linkedAccounts.get(p)))) {
                    VoiceChannel c = (VoiceChannel) g.getController().createVoiceChannel(p.getName() + "'s Party Channel").complete();
                    Bot.pChannels.add(c);
                    c.createPermissionOverride(g.getPublicRole()).complete().getManager().deny(3146752).queue();
                    party.setVc(c);
                    party.broadcast(prefix + "Created party discord channel. Please link your account by doing /link and join the Party Lobby discord channel to get moved.");
                    g.getController().moveVoiceMember(g.getMember(linkedAccounts.get(p)), c).queue();
                    for (Player pl : party.getPlayers()) {
                        if (Party.linkedAccounts.containsKey(pl))
                            if (g.getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby")).getMembers().contains(g.getMember(Party.linkedAccounts.get(pl))))
                                g.getController().moveVoiceMember(g.getMember(Party.linkedAccounts.get(pl)), c).queue();
                    }
                } else {
                    p.sendMessage(prefix + ChatColor.RED + "Please join the Party Lobby voice channel so we can move you to a private voice channel");
                }
            }
        }
    }

    @Override
    public String getName() {
        return "discord";
    }

    @Override
    public String getDescription() {
        return "Creates a private discord channel for party members.";
    }

    @Override
    public String[] getAliases() {
        String[] aliases = new String[1];
        aliases[0] = "dc";
        return aliases;
    }

    @Override
    public String getUsage() {
        return "/party discord";
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
