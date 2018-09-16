package us.prismarinemc.party;

import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class PartyObject {
    private List<Player> players;
    private List<Player> invitedPlayers;
    private List<Player> inPartyChat;
    private Player leader;
    private VoiceChannel vc;

    public PartyObject(Player leader) {
        this.leader = leader;
        vc = null;
        players = new ArrayList<>();
        invitedPlayers = new ArrayList<>();
        inPartyChat = new ArrayList<>();
        Party.parties.add(this);
    }

    public static PartyObject findParty(Player p) {
        for (PartyObject pa : Party.parties) {
            if (pa.getPlayers().contains(p) || pa.getLeader() == p) return pa;
        }
        return null;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayers(Player... players) {
        for (Player p : players) {
            if (invitedPlayers.contains(p)) {
                invitedPlayers.remove(p);
                this.players.add(p);
                if (Party.linkedAccounts.containsKey(p) && vc != null)
                    vc.createPermissionOverride(vc.getGuild().getMember(Party.linkedAccounts.get(Party.linkedAccounts.get(p)))).complete().getManager().grant(3146752).queue();
                broadcast(Party.prefix + p.getName() + " has joined the party");
            } else {
                p.sendMessage(Party.prefix + ChatColor.RED + "You have not been invited to this party!");
            }
        }
    }

    public Player getLeader() {
        return leader;
    }

    public void setLeader(Player leader) {
        players.add(this.leader);
        this.leader = leader;
        players.remove(leader);
        if (vc != null)
            vc.getManager().setName(leader.getName() + "'s Party Channel").queue();
        broadcast(Party.prefix + leader.getName() + " is now the leader of the party");
    }

    public List<Player> getInvitedPlayers() {
        return invitedPlayers;
    }

    public void invitePlayer(Player p) {
        invitedPlayers.add(p);
        //p.sendMessage(Party.prefix + "You have been invited to " + leader.getName() + "'s party. Do /party join " + leader.getName() + " or click here");
        broadcast(Party.prefix + p.getName() + " has been invited to the party");
        try {
            Class<?> chatSerializer = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer");
            Class<?> chatComponent = Reflection.getNMSClass("IChatBaseComponent");
            Class<?> packet = Reflection.getNMSClass("PacketPlayOutChat");
            Constructor constructor = packet.getConstructor(chatComponent);
            Object text = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\":\"§8[§3Party§8]§7 » You have §7been §7invited §7to §7" + leader.getName() + "'s party. §7You have 100 §7seconds §7to §7join §7the party. §7To join, §7do §7/party join " + leader.getName() + " §7or \",\"extra\":[{\"text\":\"§3click here\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/party join " + leader.getName() + "\"}}]}");
            Object packetFinal = constructor.newInstance(text);
            Field field = packetFinal.getClass().getDeclaredField("a");
            field.setAccessible(true);
            field.set(packetFinal, text);
            Reflection.sendPacket(p, packetFinal);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Party.getInstance(), () -> {
            if (!(players.contains(p) || leader == p)) {
                removeInvite(p);
            }
        }, 20 * 100);
    }

    public void broadcast(String message) {
        leader.sendMessage(message);
        for (Player p : players) {
            p.sendMessage(message);
        }
    }

    public void removePlayer(Player p) {
        players.remove(p);
        if (Party.linkedAccounts.containsKey(p) && vc != null)
            if(vc.getMembers().contains(Party.linkedAccounts.get(p))) vc.getGuild().getController().moveVoiceMember(vc.getGuild().getMember(Party.linkedAccounts.get(p)), vc.getGuild().getVoiceChannelById(Party.getInstance().getConfig().getString("discord-party-lobby"))).queue();
        broadcast(Party.prefix + p.getName() + " has left the party");
        if (players.isEmpty() && invitedPlayers.isEmpty()) {
            disband();
        }
    }

    public VoiceChannel getVc() {
        return vc;
    }

    public void setVc(VoiceChannel vc) {
        this.vc = vc;
    }

    private void removeInvite(Player p) {
        invitedPlayers.remove(p);
        broadcast(Party.prefix + p.getName() + "'s invite has expired");
        if (players.isEmpty() && invitedPlayers.isEmpty()) {
            disband();
        }
    }

    public List<Player> getInPartyChat() {
        return inPartyChat;
    }

    public void disband() {
        broadcast(Party.prefix + "The party has been disbanded" + ((players.isEmpty() && invitedPlayers.isEmpty()) ? " as no one is in the party" : ""));
        if (vc != null)
            vc.delete().queue();
        Party.parties.remove(this);
    }
}
