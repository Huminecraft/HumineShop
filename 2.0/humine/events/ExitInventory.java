package humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import humine.main.MainShop;
import humine.utils.Presentation;
import humine.utils.shop.HatStock;
import humine.utils.shop.ParticleStock;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant de mettre a jour les listes des shops
 * 
 * @author miza
 */
public class ExitInventory implements Listener
{

	@EventHandler
	public void onExit(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		
		if(event.getInventory().getName().equals(MainShop.getInstance().getShop().getName()))
			MainShop.getInstance().getShop().getPlayersOnShop().remove(player);
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getRandomShop().getName()))
			MainShop.getInstance().getRandomShop().getPlayersOnShop().remove(player);
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getEmperorShop().getName()))
			MainShop.getInstance().getEmperorShop().getPlayersOnShop().remove(player);
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getParticleShop().getName()))
			MainShop.getInstance().getParticleShop().getPlayersOnShop().remove(player);
		
		else if(event.getInventory().getName().equals(MainShop.getInstance().getHatShop().getName()))
			MainShop.getInstance().getHatShop().getPlayersOnShop().remove(player);
		
		else if(event.getInventory().getName().startsWith(ParticleStock.getParticleStockName()))
			MainShop.getInstance().getParticleStockList().remove(player.getName());
		
		else if(event.getInventory().getName().startsWith(HatStock.getHatStockName()))
			MainShop.getInstance().getHatStockList().remove(player.getName());
		
		else if(event.getInventory().getName().startsWith(Presentation.getName()))
			Presentation.getCosmetiques().remove(player);
	}
}
