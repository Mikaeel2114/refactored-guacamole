package com.mikaeel.nickbook.ui;

import com.mikaeel.nickbook.NickBookPlugin;
import com.mikaeel.nickbook.service.NickService;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public final class NickBook {
    private NickBook() {}
    public static void openRank(NickBookPlugin p, Player player, NickService s) {
        ComponentBuilder b = new ComponentBuilder(s.color("&0Let's get you set up with your nickname! First, you'll need to choose which &0&lRANK&r&0 you would like to be shown as when nicked.\n\n"));
        String[] ranks = {"DEFAULT", "VIP", "VIP+", "MVP", "MVP+"};
        String[] colors = {"&7", "&a", "&a", "&b", "&b"};
        for (int i=0;i<ranks.length;i++) b.append(button(s, "&0➤ "+colors[i]+ranks[i]+"\n", "/nick rank "+ranks[i], "&fClick here to be shown as "+colors[i]+ranks[i]));
        open(player, b.create());
    }
    public static void openSkin(NickBookPlugin p, Player player, NickService s) {
        ComponentBuilder b = new ComponentBuilder(s.color("&0Awesome! Now, which &0&lSKIN&r&0 would you like to have while nicked?\n\n"));
        b.append(button(s,"&0➤ My normal skin\n","/nick skin normal","&fClick here to use your normal skin"));
        b.append(button(s,"&0➤ Steve/Alex skin\n","/nick skin alex","&fClick here to use a Steve/Alex skin"));
        b.append(button(s,"&0➤ Random skin\n","/nick skin random","&fClick here to use a random skin"));
        open(player, b.create());
    }
    public static void openName(NickBookPlugin p, Player player, NickService s) {
        ComponentBuilder b = new ComponentBuilder(s.color("&0Alright, now you'll need to choose the &0&lNAME&r&0 to use!\n\n"));
        b.append(button(s,"&0➤ Use a random name\n","/nick name random","&fClick here to use a randomly generated name.\n&7You can do this &f4x &7more in the next 24 hours."));
        b.append(s.color("&0To go back to being your usual self, type:\n&0&l/nick reset"));
        open(player, b.create());
    }
    public static void openRandomName(NickBookPlugin p, Player player, NickService s) {
        s.setRandomName(player);
        ComponentBuilder b = new ComponentBuilder(s.color("&0We've generated a random username for you:\n&0&l"+s.candidate(player)+"\n\n"));
        b.append(button(s,"&2&l&nUSE NAME\n","/nick _use","&fClick here to use this name."));
        b.append(button(s,"&c&l&nTRY AGAIN","/nick name random","&fClick here to generate another name."));
        open(player, b.create());
    }
    public static void openConfirmation(NickBookPlugin p, Player player, NickService s) {
        open(player, s.color("&0You have finished setting up your nickname!\n\n&0When you go into a game, you will be nicked as "+s.get(player).name()+"&0.\n\n&0You will not be nicked in lobbies.\n\n&0To go back to being your usual self, type:\n&0&l/nick reset"));
    }
    private static BaseComponent[] button(NickService s, String text, String command, String hover) {
        BaseComponent[] parts = TextComponent.fromLegacyText(s.color(text));
        for (BaseComponent part : parts) {
            part.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
            part.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(s.color(hover))));
        }
        return parts;
    }
    private static void open(Player player, BaseComponent[] page) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("Nick Setup");
        meta.setAuthor("NickBook");
        meta.spigot().addPage(page);
        book.setItemMeta(meta);
        player.getInventory().addItem(book);
        player.sendMessage("§aNick Setup book added to your inventory. Right-click it to open.");
    }
    private static void open(Player player, String page) {
        open(player, TextComponent.fromLegacyText(page));
    }
}
