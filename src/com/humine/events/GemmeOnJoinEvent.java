package com.humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.humine.main.ShopMain;

public class GemmeOnJoinEvent implements Listener
{

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if(!ShopMain.getInstance().getGemmeManager().containsPlayer(event.getPlayer())) {
			ShopMain.getInstance().getGemmeManager().addPlayer(event.getPlayer());
		}
	}
}
