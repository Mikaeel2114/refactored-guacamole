package com.mikaeel.nickbook.service;

import com.mikaeel.nickbook.NickBookPlugin;
import com.mikaeel.nickbook.model.NickProfile;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class NickService {
    private final NickBookPlugin plugin;
    public NickService(NickBookPlugin plugin) { this.plugin = plugin; }
    public NickProfile get(Player player) {
        String path = "players." + player.getUniqueId(); FileConfiguration c = plugin.getConfig();
        return new NickProfile(c.getString(path + ".rank", "DEFAULT"), c.getString(path + ".skin", "normal"), c.getString(path + ".name", player.getName()));
    }
    public void setRank(Player p, String rank) { if (plugin.getConfig().isConfigurationSection("ranks." + rank)) { plugin.getConfig().set("players." + p.getUniqueId() + ".rank", rank); saveAndApply(p); } }
    public void setSkin(Player p, String skin) { plugin.getConfig().set("players." + p.getUniqueId() + ".skin", skin); plugin.saveConfig(); p.sendMessage(color("&aSkin choice saved: &f" + skin)); }
    public void setRandomName(Player p) {
        List<String> names = new ArrayList<String>(plugin.getConfig().getStringList("random-names")); if (names.isEmpty()) names.add("PixelPilot");
        String old = get(p).name(); if (names.size() > 1) names.removeIf(n -> n.equalsIgnoreCase(old));
        plugin.getConfig().set("players." + p.getUniqueId() + ".name", names.get(ThreadLocalRandom.current().nextInt(names.size()))); saveAndApply(p);
    }
    public String randomName(Player p) {
        List<String> names = new ArrayList<String>(plugin.getConfig().getStringList("random-names"));
        if (names.isEmpty()) names.add("PixelPilot");
        String old = get(p).name();
        if (names.size() > 1) names.removeIf(n -> n.equalsIgnoreCase(old));
        String value = names.get(ThreadLocalRandom.current().nextInt(names.size()));
        plugin.getConfig().set("players." + p.getUniqueId() + ".candidate", value);
        plugin.saveConfig();
        return value;
    }
    public String candidate(Player p) { return plugin.getConfig().getString("players." + p.getUniqueId() + ".candidate", randomName(p)); }
    public void useCandidate(Player p) {
        plugin.getConfig().set("players." + p.getUniqueId() + ".name", candidate(p));
        saveAndApply(p);
    }
    public void reset(Player p) { plugin.getConfig().set("players." + p.getUniqueId(), null); plugin.saveConfig(); p.setDisplayName(p.getName()); p.setPlayerListName(p.getName()); p.sendMessage(color("&aNickname reset.")); }
    public List<String> ranks() { return new ArrayList<String>(plugin.getConfig().getConfigurationSection("ranks").getKeys(false)); }
    public void saveAndApply(Player p) { plugin.saveConfig(); NickProfile x = get(p); String prefix = plugin.getConfig().getString("ranks." + x.rank(), ""); String shown = color(prefix + x.name()); p.setDisplayName(shown); p.setPlayerListName(shown); p.sendMessage(color("&aNickname applied: &f" + x.name())); }
    public String color(String text) { return ChatColor.translateAlternateColorCodes('&', text); }
}
