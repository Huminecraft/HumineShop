package humine.events.stocks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.utils.ItemShop;
import humine.utils.ParticleScheduler;
import humine.utils.shop.ParticleStock;

/**
 * Package regroupant les evenements des stocks du plugin HumineShop
 * Classe d'evenement permettant de desactiver un effet de particule
 * 
 * @author miza
 */
public class ClickDisableButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(event.getInventory().getName().startsWith(ParticleStock.getParticleStockName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemDisable())) {
					ParticleScheduler.disableParticleCosmetique(player);
					player.closeInventory();
				}
			}
		}
	}
	
}
