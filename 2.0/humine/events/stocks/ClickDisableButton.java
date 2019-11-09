package humine.events.stocks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.ParticleScheduler;
import humine.utils.events.ClickItemStockEvent;

/**
 * Package regroupant les evenements des stocks du plugin HumineShop
 * Classe d'evenement permettant de desactiver un effet de particule
 * 
 * @author miza
 */
public class ClickDisableButton implements Listener
{

	@EventHandler
	public void onClick(ClickItemStockEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemDisable())) {
			ParticleScheduler.disableParticleCosmetique(event.getShopper().getPlayer());
			event.getShopper().getPlayer().closeInventory();
		}
	}
	
}
