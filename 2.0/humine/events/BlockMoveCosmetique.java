package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;

public class BlockMoveCosmetique implements Listener{

	@EventHandler
	public void onMove(InventoryClickEvent event) {
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getShop().getName())) {
			event.setCancelled(true);
		}
		
		if(event.getInventory().getName().startsWith("Presentation")) {
			event.setCancelled(true);
		}
		
		if(event.getInventory().getName().startsWith("Inventaire")) {
			event.setCancelled(true);
		}
	}
}
