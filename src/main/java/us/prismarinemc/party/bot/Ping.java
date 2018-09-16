package us.prismarinemc.party.bot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Ping extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().toLowerCase().startsWith("^ping")) {
            event.getChannel().sendMessage(event.getAuthor().getAsMention() + " Pong! That took " + Bot.jda.getPing() + "ms.").queue();
        }
    }
}
