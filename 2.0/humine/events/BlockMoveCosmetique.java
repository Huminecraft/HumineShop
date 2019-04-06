package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Presentation;

public class BlockMoveCosmetique implements Listener{

	@EventHandler
	public void onMove(InventoryClickEvent event) {
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getShop().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().startsWith(Presentation.getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().startsWith("Inventaire")) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getEmperorShop().getName())) {
			event.setCancelled(true);
		}
	}
}
