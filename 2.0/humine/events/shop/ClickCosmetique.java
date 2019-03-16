package humine.events.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Cosmetique;
import humine.utils.Page;
import humine.utils.Shop;
import humine.utils.Utils;

public class ClickCosmetique implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event)
	{

		if (event.getInventory().getName().equals(MainShop.getInstance().getShop().getName()))
		{
			if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
				return;

			Player player = (Player) event.getWhoClicked();
			if (event.getCurrentItem().getItemMeta().getDisplayName().contains("#"))
			{
				String id = event.getCurrentItem().getItemMeta().getDisplayName().split("#")[1];
				Cosmetique cosmetique = getCosmetique(id);
				if (cosmetique != null)
				{
					Utils.openPresentation(player, cosmetique);
					MainShop.getInstance().getShop().getPlayersOnShop().remove(player);
				}
			}
			else
			{
				if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Retour"))
				{
					Shop.previousPage(MainShop.getInstance().getShop(), player);
				}
				else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Suivant"))
				{
					Shop.nextPage(MainShop.getInstance().getShop(), player);
				}
			}
		}
	}

	private Cosmetique getCosmetique(String id)
	{
		for (Page page : MainShop.getInstance().getShop().getPages())
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
