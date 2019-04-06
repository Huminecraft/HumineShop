package humine.events.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Page;
import humine.utils.shop.Stock;
public class ClickMaterialInventory implements Listener
{

	@EventHandler
	public void onclick(InventoryClickEvent event) {

		if(event.getSlot() == 39) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().contains("#")) {
					Cosmetique c = getCosmetique(event.getCurrentItem().getItemMeta().getDisplayName().split("#")[1], (Player) event.getWhoClicked());
					if(c != null) {
						event.setCancelled(true);
						event.getWhoClicked().getInventory().setHelmet(null);
					}
				}
			}
		}
	}
	
	private Cosmetique getCosmetique(String id, Player player)
	{
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		
		if(stock == null)
			return null;
		
		
		for (Page page : stock.getPages())
		{
			for (int i = 0; i < page.getSize(); i++)
			{
				if (page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(id))
				{
					return page.getCosmetiques()[i];
				}
			}
		}
		
		return null;
	}
}
