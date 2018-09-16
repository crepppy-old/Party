package us.prismarinemc.party.bot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import us.prismarinemc.party.Party;
import us.prismarinemc.party.PartyObject;

public class UnLink extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("^unlink")) {
            if (Party.linkedAccounts.containsValue(event.getAuthor())) {
                Party.linkedAccounts.forEach((i, e) -> {
                    if (event.getAuthor().equals(e)) {
                        if (i.isOnline() && PartyObject.findParty(i.getPlayer()) != null && PartyObject.findParty(i.getPlayer()).getVc() != null && PartyObject.findParty(i.getPlayer()).getVc().getMembers().contains(event.getMember()))
                            event.getGuild().getController().moveVoiceMember(event.getMember(), event.getGuild().getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby"))).queue();
                        Party.linkedAccounts.remove(i);
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Removed linked account").queue();
                    }
                });
                
            } else {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Your minecraft account isn't currently linked with discord").queue();
            }
        }
    }
}
