package humine.events.presentation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Presentation;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant de retourner dans le lieu precedent le menu
 * de presentation
 * 
 * @author miza
 */

public class ClickQuitButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemQuit())) {
					Player player = (Player) event.getWhoClicked();
					returnToShop(player, event.getInventory().getName());
				}
			}
		}
	}

	private void returnToShop(Player player, String name)
	{
		if(MainShop.getInstance().getParticleShop().getCosmetique(name.split("#")[1]) != null)
			MainShop.getInstance().getParticleShop().openShop(player);
		
		else if(MainShop.getInstance().getHatShop().getCosmetique(name.split("#")[1]) != null)
			MainShop.getInstance().getHatShop().openShop(player);
		
		else if(MainShop.getInstance().getInventories().getStockOfPlayer(player.getName()).getCosmetique(name.split("#")[1]) != null)
			MainShop.getInstance().getMenuAccueil().openMenu(player);
			
		else if(MainShop.getInstance().getCustomHeadShop().getCosmetique(name.split("#")[1]) != null)
			MainShop.getInstance().getCustomHeadShop().openShop(player);
		
		else if(MainShop.getInstance().getRandomShop().getCosmetique(name.split("#")[1]) != null)
			MainShop.getInstance().getRandomShop().openShop(player);
		
		else if(MainShop.getInstance().getEmperorShop().getCosmetique(name.split("#")[1]) != null)
			MainShop.getInstance().getEmperorShop().openShop(player);
	}
}
