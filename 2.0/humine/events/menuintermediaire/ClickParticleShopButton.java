package humine.events.menuintermediaire;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;

/**
 * Package regroupant les evenements du menu intermediaire du plugin HumineShop
 * Classe d'evenement permettant d'ouvrir le sous-shop cosmetique particle
 * 
 * @author miza
 */
public class ClickParticleShopButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(MainShop.getInstance().getMenuIntermediaire().itemParticleShop())) {
					Player player = (Player) event.getWhoClicked();
					openParticleShop(player);
				}
			}
		}
	}

	private void openParticleShop(Player player) {
		if(MainShop.getInstance().getParticleShop().isEmpty())
			player.sendMessage("Particle Shop indisponible");
		else
			MainShop.getInstance().getParticleShop().openShop(player);
	}
}
