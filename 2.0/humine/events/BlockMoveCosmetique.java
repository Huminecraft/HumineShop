package humine.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.Presentation;
import humine.utils.shop.CustomHeadStock;
import humine.utils.shop.HatStock;
import humine.utils.shop.ParticleStock;

public class BlockMoveCosmetique implements Listener{

	@EventHandler
	public void onMove(InventoryClickEvent event) {
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getShop().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getParticleShop().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getHatShop().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getCustomHeadShop().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().startsWith(ParticleStock.getParticleStockName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().startsWith(HatStock.getHatStockName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().startsWith(CustomHeadStock.getCustomHeadStockName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().startsWith(Presentation.getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getMenuIntermediaire().getName())) {
			event.setCancelled(true);
		}
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getEmperorShop().getName())) {
			event.setCancelled(true);
		}
	}
}
