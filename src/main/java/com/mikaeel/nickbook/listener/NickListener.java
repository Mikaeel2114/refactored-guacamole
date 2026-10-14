package com.mikaeel.nickbook.listener;
import com.mikaeel.nickbook.NickBookPlugin; import com.mikaeel.nickbook.service.NickService; import org.bukkit.event.*; import org.bukkit.event.player.PlayerJoinEvent;
public final class NickListener implements Listener { private final NickBookPlugin p; private final NickService s; public NickListener(NickBookPlugin p,NickService s){this.p=p;this.s=s;} @EventHandler public void join(PlayerJoinEvent e){if(p.getConfig().contains("players."+e.getPlayer().getUniqueId()))s.saveAndApply(e.getPlayer());}}
