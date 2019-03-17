package humine.events.randomshop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Shop;

public class ClickQuitButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		if (event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName()))
		{
			if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
				return;

			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Quittez"))
			{
				Shop.openShop(MainShop.getInstance().getShop(), player);
			}
		}
	}
}
