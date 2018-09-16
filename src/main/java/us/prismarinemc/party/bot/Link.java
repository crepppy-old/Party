package us.prismarinemc.party.bot;

import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.entity.Player;
import us.prismarinemc.party.Party;
import us.prismarinemc.party.PartyObject;

import java.util.HashMap;

public class Link extends ListenerAdapter {
    public static HashMap<Integer, Player> link = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("^link")) {
            if (event.getMessage().getContentRaw().contains(" ")) {
                int i;
                try {
                    i = Integer.parseInt(event.getMessage().getContentRaw().split(" ")[1]);
                } catch (NumberFormatException e) {
                    event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Please enter a valid ID").queue();
                    return;
                }
                if (link.get(i) == null) {
                    event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Please run /link in game, and then execute this command with the given id").queue();
                } else {
                    if (Party.linkedAccounts.containsValue(event.getAuthor())) {
                        final String[] p = {""};
                        Party.linkedAccounts.forEach((pl, u) -> {
                            if (event.getAuthor().equals(u)) {
                                p[0] = pl.getName();
                            }
                        });
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " This discord is currently linked to " + p[0]).queue();
                        link.remove(i);
                    } else {
                        Party.linkedAccounts.put(link.get(i), event.getAuthor());
                        if (PartyObject.findParty(link.get(i)) != null && PartyObject.findParty(link.get(i)).getVc() != null && event.getGuild().getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby")).getMembers().contains(event.getMember()))
                            event.getGuild().getController().moveVoiceMember(event.getMember(), PartyObject.findParty(link.get(i)).getVc()).queue();
                        link.remove(i);
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Your account has been linked. Do /unlink in game or ^unlink ").queue();
                    }
                }
            } else {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Please run /link in game before running the command in discord").queue();
            }
        }
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        if (event.getChannelJoined().equals(event.getGuild().getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby"))) && Party.linkedAccounts.containsValue(event.getMember().getUser())) {
            Party.linkedAccounts.forEach((p, u) -> {
                if (u.equals(event.getMember().getUser()) && p.isOnline() && PartyObject.findParty(p.getPlayer()) != null && PartyObject.findParty(p.getPlayer()).getVc() != null)
                    event.getGuild().getController().moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby"))).queue();
            });
        }
    }
}
