package us.prismarinemc.party;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (PartyObject.findParty(e.getPlayer()) != null && PartyObject.findParty(e.getPlayer()).getInPartyChat().contains(e.getPlayer())) {
            e.setCancelled(true);
            PartyObject.findParty(e.getPlayer()).broadcast(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "PartyChat" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + e.getPlayer().getName() + ": " + e.getMessage());
        }
    }
}
