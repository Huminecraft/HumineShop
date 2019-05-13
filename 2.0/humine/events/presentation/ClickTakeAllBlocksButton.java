package humine.events.presentation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.CustomHeadBlockInfo;
import humine.utils.Presentation;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Stock;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement de recuperer tout les cosmetiques custom heads du joueur se 
 * trouvant sous forme de block
 * 
 * @author miza
 */
public class ClickTakeAllBlocksButton implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(Presentation.itemTakeAllBlocks().getItemMeta().getDisplayName())) {
					takeAllBlocks((Player) event.getWhoClicked());
				}
			}
		}
	}
	
	public void takeAllBlocks(Player player) {
		CustomHeadBlockInfo chb = MainShop.getInstance().getPlayerCustomHeadList().get(player.getName());
		Cosmetique c = Presentation.getCosmetiques().get(player);
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		
		if(chb == null || c == null || stock == null)
			return;
		
		ClickTakeAllButton.TakeBlocks(chb, stock, c);
	}
}
