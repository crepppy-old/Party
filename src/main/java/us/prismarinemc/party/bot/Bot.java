package us.prismarinemc.party.bot;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.ChatColor;
import us.prismarinemc.party.Party;

import java.util.ArrayList;
import java.util.List;

public class Bot {
    public static JDA jda;
    public static List<VoiceChannel> pChannels = new ArrayList<>();

    public Bot(String token) {
        try {
            jda = new JDABuilder(AccountType.BOT).setGame(Game.of(Game.GameType.DEFAULT, "^link | PrismarineMC")).setAutoReconnect(true).setToken(token).addEventListener(new Ping(), new Link(), new UnLink()).buildBlocking();
        } catch (Exception e) {
            Party.getInstance().getLogger().severe(ChatColor.RED + "ERROR: BOT TOKEN NOT SET IN CONFIG. /PARTY DISCORD COMMAND DISABLED");
            Party.getInstance().getLogger().severe(ChatColor.RED + "ERROR: BOT TOKEN NOT SET IN CONFIG. /PARTY DISCORD COMMAND DISABLED");
            Party.discord = false;
        }
    }
}
