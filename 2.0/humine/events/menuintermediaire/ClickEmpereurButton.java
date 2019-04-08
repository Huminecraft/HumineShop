package humine.events.menuintermediaire;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;

public class ClickEmpereurButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemEmpereur())) {
					Player player = (Player) event.getWhoClicked();
					if(MainShop.getInstance().getEmperorShop().isEmpty())
						player.sendMessage("Emperor Shop indisponible");
					else
						MainShop.getInstance().getEmperorShop().openShop(player);
				}
			}
		}
	}
	
}
