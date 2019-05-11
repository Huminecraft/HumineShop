package humine.events.shops;

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
 * Classe d'evenement permettant de retourner sur le menu intermediare/accueil
 * 
 * @author miza
 */
public class ClickQuitButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getParticleShop().getName()) ||
				event.getInventory().getName().equals(MainShop.getInstance().getHatShop().getName()) ||
					event.getInventory().getName().equals(MainShop.getInstance().getCustomHeadShop().getName()) ||
						event.getInventory().getName().equals(MainShop.getInstance().getEmperorShop().getName())) {
			
			returnToIntermediaire(MainShop.getInstance().getParticleShop(), event.getCurrentItem(), player);
		}
		else if(event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName())) {
			returnToAccueil(MainShop.getInstance().getRandomShop(), event.getCurrentItem(), player);
		}
	}
	
	public void returnToIntermediaire(Shop shop, ItemStack item, Player player) {
		if(item != null) {
			if(item.isSimilar(ItemShop.itemQuit())) {
				MainShop.getInstance().getMenuIntermediaire().openMenu(player);
			}
		}
	}
	
	public void returnToAccueil(Shop shop, ItemStack item, Player player) {
		if(item != null) {
			if(item.isSimilar(ItemShop.itemQuit())) {
				MainShop.getInstance().getMenuAccueil().openMenu(player);
			}
		}
	}
}
