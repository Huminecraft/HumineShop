package humine.events.menuintermediaire;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;

public class ClickCustomHeadButton implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(MainShop.getInstance().getMenuIntermediaire().itemHatShop())) {
					Player player = (Player) event.getWhoClicked();
					player.sendMessage("Custom head Shop indisponible");
					openCustomHeadShop(player);
				}
			}
		}
	}
	
	private void openCustomHeadShop(Player player) {
		//TODO
	}
}
