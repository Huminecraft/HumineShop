package com.humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.humine.main.ShopMain;

public class ShopMoveItemEvent implements Listener
{

	@EventHandler
	public void onMoveItem(InventoryClickEvent event) {
		
		if (event.getInventory().getName().startsWith(ShopMain.getInstance().getShop().getName()))
			event.setCancelled(true);
		
		if(event.getInventory().getName().startsWith("Présentation"))
			event.setCancelled(true);
	}
}
