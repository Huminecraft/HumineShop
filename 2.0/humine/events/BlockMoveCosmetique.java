package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant d'annuler les deplacements des items
 * dans tout les shop de HumineShop
 * 
 * @author miza
 */
public class BlockMoveCosmetique implements Listener{

	@EventHandler
	public void onMove(InventoryClickEvent event) {
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			event.setCancelled(true);
		}
		
	}
}
