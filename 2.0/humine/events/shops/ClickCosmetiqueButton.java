package humine.events.shops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Presentation;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Shop;

public class ClickCosmetiqueButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getParticleShop().getName())) {
			clickCosmetique(MainShop.getInstance().getParticleShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getHatShop().getName())) {
			clickCosmetique(MainShop.getInstance().getHatShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName())) {
			clickCosmetique(MainShop.getInstance().getRandomShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getEmperorShop().getName())) {
			clickCosmetique(MainShop.getInstance().getEmperorShop(), event.getCurrentItem(), player);
		}
	}
	
	public void clickCosmetique(Shop shop, ItemStack item, Player player) {
		if(item != null && item.getType() != Material.AIR) {
			if(item.getItemMeta().getDisplayName().contains("#")) {
				Cosmetique c = shop.getCosmetique(item.getItemMeta().getDisplayName().split("#")[1]);
				
				if(c != null) {
					Presentation.openPresentation(player, c);
				}
			}
		}
	}
}
