package humine.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Cosmetique;
import humine.utils.Page;
import humine.utils.Utils;

public class ClickPresentationCosmetique implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		if(event.getInventory().getName().startsWith("Presentation")) {
			if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
				return;
			
			Player player = (Player) event.getWhoClicked();
			if(event.getCurrentItem().getItemMeta().getLore() != null && event.getCurrentItem().getItemMeta().getLore().contains("Try")) {
				Cosmetique c = getCosmetique(event.getInventory().getName().split("#")[1]);
				if(c != null) {
					Utils.lauchTryCosmetique(player, c, MainShop.getInstance());
				}
				player.closeInventory();
				MainShop.getInstance().getShop().getPlayersOnShop().remove(player);
			}
			else if(event.getCurrentItem().getItemMeta().getLore() != null && event.getCurrentItem().getItemMeta().getLore().contains("Buy")) {
				MainShop.sendMessage(player, "BUY CLICKED");
			}
			else {
				if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Retour")) {
					Utils.openShop(MainShop.getInstance().getShop(), player);
				}
			}
		}
	}
	
	private Cosmetique getCosmetique(String id) {
		for(Page page : MainShop.getInstance().getShop().getPages()) {
			for(int i = 0; i < page.getSize(); i++) {
				if(page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(id)) {
					return page.getCosmetiques()[i];
				}
			}
		}
		return null;
	}
}
