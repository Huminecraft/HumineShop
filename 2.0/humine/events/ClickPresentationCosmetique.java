package humine.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Cosmetique;
import humine.utils.Page;
import humine.utils.Shop;
import humine.utils.Stock;
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
			}
			else if(event.getCurrentItem().getItemMeta().getLore() != null && event.getCurrentItem().getItemMeta().getLore().contains("Buy with humis")) {
				
				Cosmetique c = getCosmetique(event.getInventory().getName().split("#")[1]);
				if(c != null) {
					if(MainShop.getInstance().getBankHumis().getMoney(player) >= c.getHumisPrice()) {
						Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
						if(stock != null) {
							Utils.addCosmetique(stock, c);
							player.closeInventory();
							MainShop.sendMessage(player, "Cosmetique achete !");
							MainShop.getInstance().getBankHumis().removeMoney(player, c.getHumisPrice());
						}
					}
				}
				
			}
			else if(event.getCurrentItem().getItemMeta().getLore() != null && event.getCurrentItem().getItemMeta().getLore().contains("Buy with pixel")) {
				
				Cosmetique c = getCosmetique(event.getInventory().getName().split("#")[1]);
				if(c != null) {
					if(MainShop.getInstance().getBankPixel().getMoney(player) >= c.getPixelPrice()) {
						Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
						if(stock != null) {
							Utils.addCosmetique(stock, c);
							player.closeInventory();
							MainShop.sendMessage(player, "Cosmetique achete !");
							MainShop.getInstance().getBankPixel().removeMoney(player, c.getPixelPrice());
						}
					}
				}
				
			}
			else {
				if(event.getCurrentItem().getItemMeta().getDisplayName().contains("Retour")) {
					Shop.openShop(MainShop.getInstance().getShop(), player);
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
		
		for(Page page : MainShop.getInstance().getRandomShop().getPages()) {
			for(int i = 0; i < page.getSize(); i++) {
				if(page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(id)) {
					return page.getCosmetiques()[i];
				}
			}
		}
		
		return null;
	}
}
