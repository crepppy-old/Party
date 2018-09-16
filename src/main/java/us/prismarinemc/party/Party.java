package us.prismarinemc.party;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.prismarinemc.party.bot.Bot;
import us.prismarinemc.party.commands.BotCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Party extends JavaPlugin {
    public static List<PartyObject> parties;
    public static HashMap<Player, Integer /* id*/> leaveTimers = new HashMap<>();
    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.DARK_AQUA + "Party" + ChatColor.DARK_GRAY + "]" + ChatColor.DARK_AQUA + " » " + ChatColor.GRAY;
    public static HashMap<OfflinePlayer, User> linkedAccounts = new HashMap<>();
    private static Party instance;
    public static boolean discord = true;

    public static Party getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        parties = new ArrayList<>();
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveDefaultConfig();
        new Bot(getConfig().getString("token"));
        Bukkit.getServer().getPluginCommand("party").setExecutor(new CommandManager());
        Bukkit.getServer().getPluginCommand("link").setExecutor(new BotCommand());
        Bukkit.getServer().getPluginCommand("unlink").setExecutor(new BotCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatListener(), this);

        for (String s : getConfig().getStringList("linked-accounts")) {
            linkedAccounts.put(Bukkit.getOfflinePlayer(UUID.fromString(s.split(":")[0])), Bot.jda.getUserById(s.split(":")[1]));
        }
    }


    @Override
    public void onDisable() {
        List<String> stringList = new ArrayList<>();
        for (OfflinePlayer p : linkedAccounts.keySet()) {
            stringList.add(p.getUniqueId().toString() + ":" + linkedAccounts.get(p).getId());
        }
        reloadConfig();
        getConfig().set("linked-accounts", stringList);
        saveConfig();
        for (VoiceChannel c : Bot.pChannels) {
            c.delete().queue();
        }
    }
}
