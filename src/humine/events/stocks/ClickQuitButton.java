package humine.events.stocks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.events.ClickItemStockEvent;
import humine.utils.menus.MenuAccueil;

/**
 * Package regroupant les evenements des stocks du plugin HumineShop
 * Classe d'evenement permettant de retourner a la page d'accueil
 * 
 * @author miza
 */
public class ClickQuitButton implements Listener{

	@EventHandler
	public void onClick(ClickItemStockEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemQuit())) {
			MenuAccueil.openMenu(event.getShopper().getPlayer());
		}
	}
}
