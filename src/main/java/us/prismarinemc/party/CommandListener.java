package us.prismarinemc.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener {
    @EventHandler
    public void onPlayerCommandExecute(PlayerCommandPreprocessEvent e) {
        //TODO CHECK ALL PLAYERS HAVE THE PERMISSION, CHECK FOR SERVER SENDING COMMAND WITH VARIABLES.
        if (PartyObject.findParty(e.getPlayer()) == null) return;
        for (String m : Party.getInstance().getConfig().getStringList("commands")) {
            m = m.replaceAll("%player%", e.getPlayer().getName().toLowerCase());
            if (e.getMessage().toLowerCase().startsWith("/" + m.toLowerCase())) {
                if (PartyObject.findParty(e.getPlayer()).getLeader() != e.getPlayer()) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Party.prefix + ChatColor.RED + "You are not the leader of the party so you can't do this!");
                } else {
                    for (Player p : PartyObject.findParty(e.getPlayer()).getPlayers()) {
                        p.performCommand(e.getMessage().split("/", 2)[1]);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent e) {
        for (String m : Party.getInstance().getConfig().getStringList("console-commands")) {
            if (e.getCommand().toLowerCase().startsWith(m.toLowerCase().replaceAll("%player%", ""))) {
                if (m.toLowerCase().contains("%player%")) {
                    Player p;
                    String[] check = m.split(" ");
                    String pName = "";
                    for (int i = 0; i < check.length; i++)
                        if (check[i].equalsIgnoreCase("%player%"))
                            pName = e.getCommand().split(" ")[i];

                    if (pName != "" && Bukkit.getServer().getPlayer(pName) != null) {
                        p = Bukkit.getServer().getPlayer(pName);
                    } else return;
                    if (PartyObject.findParty(p) == null) return;
                    if (PartyObject.findParty(p).getLeader() == p) {
                        for (Player pP : PartyObject.findParty(p).getPlayers())
                            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), e.getCommand().replaceAll(p.getName(), pP.getName()));
                    }
                }
            }
        }
    }
}
