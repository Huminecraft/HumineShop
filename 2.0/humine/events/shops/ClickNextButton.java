package humine.events.shops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.shop.Shop;

/**
 * Package regroupant les evenements des shops du plugin HumineShop
 * Classe d'evenement permettant d'aller sur la page suivante du shop
 * 
 * @author miza
 */
public class ClickNextButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getParticleShop().getName())) {
			openShop(MainShop.getInstance().getParticleShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getHatShop().getName())) {
			openShop(MainShop.getInstance().getHatShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getCustomHeadShop().getName())) {
			openShop(MainShop.getInstance().getCustomHeadShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName())) {
			openShop(MainShop.getInstance().getRandomShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getEmperorShop().getName())) {
			openShop(MainShop.getInstance().getEmperorShop(), event.getCurrentItem(), player);
		}
	}
	
	public void openShop(Shop shop, ItemStack item, Player player) {
		if(item != null && item.getType() != Material.AIR) {
			if(item.getItemMeta().getDisplayName().equals(ItemShop.itemNextArrow().getItemMeta().getDisplayName())) {
				shop.nextPage(player);
			}
		}
	}
}
