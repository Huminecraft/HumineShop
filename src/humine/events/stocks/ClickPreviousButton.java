package humine.events.stocks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.events.ClickItemStockEvent;

/**
 * Package regroupant les evenements des stocks du plugin HumineShop
 * Classe d'evenement permettant d'aller a la page precedente du stock
 * 
 * @author miza
 */
public class ClickPreviousButton implements Listener
{

	@EventHandler
	public void onClick(ClickItemStockEvent event) {
		if(event.getItem().getItemMeta().getDisplayName().equals(ItemShop.itemPreviousArrow().getItemMeta().getDisplayName())) {
			event.getShopper().getStock().previousPage();
		}
	}
}
