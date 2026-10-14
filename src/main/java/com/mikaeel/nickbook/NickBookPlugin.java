package com.mikaeel.nickbook;

import com.mikaeel.nickbook.command.NickCommand;
import com.mikaeel.nickbook.listener.NickListener;
import com.mikaeel.nickbook.service.NickService;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class NickBookPlugin extends JavaPlugin {
    private NickService nickService;
    @Override public void onEnable() {
        saveDefaultConfig();
        nickService = new NickService(this);
        NickCommand command = new NickCommand(this, nickService);
        getCommand("nick").setExecutor(command);
        getCommand("nick").setTabCompleter(command);
        getServer().getPluginManager().registerEvents(new NickListener(this, nickService), this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (getConfig().getBoolean("players." + player.getUniqueId() + ".active", false)) {
                    player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                            net.md_5.bungee.api.chat.TextComponent.fromLegacyText(
                                    nickService.color("&fYou are currently &cNICKED (In games only)")));
                }
            }
        }, 20L, 20L);
    }
    public NickService getNickService() { return nickService; }
}
