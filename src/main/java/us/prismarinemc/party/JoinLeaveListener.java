package us.prismarinemc.party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        if (PartyObject.findParty(e.getPlayer()) != null) {
            if (PartyObject.findParty(e.getPlayer()) != null) {
                Runnable runnable = () -> {
                    if (PartyObject.findParty(e.getPlayer()).getLeader() == e.getPlayer()) {
                        PartyObject.findParty(e.getPlayer()).setLeader(PartyObject.findParty(e.getPlayer()).getPlayers().get(0));
                        PartyObject.findParty(e.getPlayer()).removePlayer(e.getPlayer());
                    } else
                        PartyObject.findParty(e.getPlayer()).removePlayer(e.getPlayer());

                    Party.leaveTimers.remove(e.getPlayer());

                };
                int id = Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Party.getInstance(), runnable, 20 * 60 * 5);

                Party.leaveTimers.put(e.getPlayer(), id);
            }
        }
    }

    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Party.leaveTimers.keySet().contains(e.getPlayer())) {
            Bukkit.getServer().getScheduler().cancelTask(Party.leaveTimers.get(e.getPlayer()));
            Party.leaveTimers.remove(e.getPlayer());
            PartyObject.findParty(e.getPlayer()).getLeader().sendMessage(Party.prefix + e.getPlayer().getName() + " has rejoined the party");
            for (Player p : PartyObject.findParty(e.getPlayer()).getPlayers()) {
                p.sendMessage(Party.prefix + e.getPlayer().getName() + " has rejoined the party");
            }
        }
    }
}
