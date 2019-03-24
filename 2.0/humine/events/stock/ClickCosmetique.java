package humine.events.stock;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Cosmetique;
import humine.utils.MaterialHatCosmetique;
import humine.utils.Page;
import humine.utils.ParticleCosmetique;
import humine.utils.Stock;
import humine.utils.Utils;
import humine.utils.randomshop.TemporaryMaterialHatCosmetique;
import humine.utils.randomshop.TemporaryParticleCosmetique;

public class ClickCosmetique implements Listener
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
				if (event.getCurrentItem().getItemMeta().getDisplayName().contains("#"))
				{
					String id = event.getCurrentItem().getItemMeta().getDisplayName().split("#")[1];
					Cosmetique cosmetique = getCosmetique(id);
					if (cosmetique != null)
					{
						Utils.disableParticleCosmetique(player);
						if(cosmetique instanceof ParticleCosmetique || cosmetique instanceof TemporaryParticleCosmetique) {
							Utils.lauchBuyCosmetique(player, cosmetique);
							player.closeInventory();
						}
						else if(cosmetique instanceof MaterialHatCosmetique || cosmetique instanceof TemporaryMaterialHatCosmetique) {
							Utils.swearHatCosmetique(player, cosmetique);
							player.closeInventory();
						}
						
					}
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
