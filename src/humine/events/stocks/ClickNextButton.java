package humine.events.stocks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.events.ClickItemStockEvent;

/**
 * Package regroupant les evenements des stocks du plugin HumineShop
 * Classe d'evenement permettant d'aller a la page suivante du stock
 * 
 * @author miza
 */
public class ClickNextButton implements Listener{

	@EventHandler
	public void onClick(ClickItemStockEvent event) {
		if(event.getItem().getItemMeta().getDisplayName().equals(ItemShop.itemNextArrow().getItemMeta().getDisplayName())) {
			event.getShopper().getStock().nextPage();
		}
	}
}
