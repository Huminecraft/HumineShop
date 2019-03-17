package humine.events.stock;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Stock;

public class ClickArrowButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		if (event.getInventory().getName().startsWith("Inventaire"))
		{
			if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
				return;

			Player player = (Player) event.getWhoClicked();
			Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
			
			if(stock != null) {
				if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Retour"))
				{
					Stock.previousPage(stock, player);
				}
				else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Suivant"))
				{
					Stock.nextPage(stock, player);
				}
			}
		}
	}
}
