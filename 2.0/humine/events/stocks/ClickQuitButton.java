package humine.events.stocks;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.shop.HatStock;
import humine.utils.shop.ParticleStock;

public class ClickQuitButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(ParticleStock.getParticleStockName()) ||
				event.getInventory().getName().startsWith(HatStock.getHatStockName())) {
			
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemQuit())) {
					Player player = (Player) event.getWhoClicked();
					MainShop.getInstance().getMenuAccueil().openMenu(player);
				}
			}
		}
	}
}
