package humine.events.menuaccueil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;

public class ClickCustomHeadButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ItemShop.itemCustomHeadStock().getItemMeta().getDisplayName())) {
					Player player = (Player) event.getWhoClicked();
					MainShop.sendMessage(player, "Custom head stock indisponible");
				}
			}
		}
	}
}
