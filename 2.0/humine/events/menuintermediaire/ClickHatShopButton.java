package humine.events.menuintermediaire;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;

/**
 * Package regroupant les evenements du menu intermediaire du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le sous-shop cosmetique material hat
 * 
 * @author miza
 */
public class ClickHatShopButton implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(MainShop.getInstance().getMenuIntermediaire().itemHatShop())) {
					Player player = (Player) event.getWhoClicked();
					openHatShop(player);
				}
			}
		}
	}
	
	private void openHatShop(Player player) {
		if(MainShop.getInstance().getHatShop().isEmpty())
			player.sendMessage("Hat Shop indisponible");
		else
			MainShop.getInstance().getHatShop().openShop(player);
	}
}
