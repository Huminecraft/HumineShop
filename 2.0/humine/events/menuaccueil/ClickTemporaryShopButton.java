package humine.events.menuaccueil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;

public class ClickTemporaryShopButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemTemporaryShop())) {
					Player player = (Player) event.getWhoClicked();
					if(!MainShop.getInstance().getRandomShop().isEmpty())
						MainShop.getInstance().getRandomShop().openShop(player);
					else
						MainShop.sendMessage(player, "Boutique aleatoire indisponible :3");
				}
			}
		}
	}
}
