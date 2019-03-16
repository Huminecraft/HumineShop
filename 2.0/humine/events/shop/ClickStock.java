package humine.events.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Stock;

public class ClickStock implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		if (event.getInventory().getName().equals(MainShop.getInstance().getShop().getName()))
		{
			if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
				return;

			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Vos cosmetiques"))
			{
				Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
				if(stock != null) {
					Stock.openStock(stock, player);
				}
				
			}
		}
	}

}
