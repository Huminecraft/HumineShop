package humine.events.stocks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.shop.HatStock;
import humine.utils.shop.ParticleStock;
import humine.utils.shop.Shop;

public class ClickPreviousButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if(event.getInventory().getName().startsWith(ParticleStock.getParticleStockName())) {
			ParticleStock pStock = MainShop.getInstance().getParticleStockList().get(player.getName());
			if(pStock != null) {
				previousPage(pStock, event.getCurrentItem(), player);
			}
		}
		else if(event.getInventory().getName().startsWith(HatStock.getHatStockName())) {
			HatStock pStock = MainShop.getInstance().getHatStockList().get(player.getName());
			if(pStock != null) {
				previousPage(pStock, event.getCurrentItem(), player);
			}
		}
	}
	
	public void previousPage(Shop shop, ItemStack item, Player player) {
		if(item != null && item.getType() != Material.AIR) {
			if(item.getItemMeta().getDisplayName().equals(ItemShop.itemPreviousArrow().getItemMeta().getDisplayName())) {
				shop.previousPage(player);
			}
		}
	}
}
