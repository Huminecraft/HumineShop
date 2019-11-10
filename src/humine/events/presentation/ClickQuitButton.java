package humine.events.presentation;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.InventoryView;

import humine.utils.ItemShop;
import humine.utils.Shopper;
import humine.utils.events.ClickItemPresentationEvent;
import humine.utils.menus.MenuAccueil;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant de retourner dans le lieu precedent le menu
 * de presentation
 * 
 * @author miza
 */

public class ClickQuitButton implements Listener{

	@EventHandler
	public void onClick(ClickItemPresentationEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemQuit())) {
			returnToShop(event.getShopper(), event.getView());
		}
	}
	
	private void returnToShop(Shopper shopper, InventoryView inventory)
	{
		String id = inventory.getTitle().split("#")[1];
		if(shopper.getStock().containsCosmetique(id))
			MenuAccueil.openMenu(shopper.getPlayer());
		else if(shopper.getShop().containsCosmetique(id))
			shopper.getShop().openShop();
		else
			MenuAccueil.openMenu(shopper.getPlayer());
	}
}
