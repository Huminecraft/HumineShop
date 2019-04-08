package humine.events.menuintermediaire;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;

public class ClickCustomHeadButton implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(MainShop.getInstance().getMenuIntermediaire().itemCustomHeadShop().getItemMeta().getDisplayName())) {
					Player player = (Player) event.getWhoClicked();
					MainShop.sendMessage(player, "Custom head Shop indisponible");
					openCustomHeadShop(player);
				}
			}
		}
	}
	
	private void openCustomHeadShop(Player player) {
		//TODO
	}
}
