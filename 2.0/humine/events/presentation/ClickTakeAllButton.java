package humine.events.presentation;

import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.CustomHeadBlockInfo;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Stock;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement de recuperer tout les cosmetiques custom heads du joueur
 * (ceux se trouvant poser sous forme de block et ceux se trouvant dans l'inventaire
 * du joueur)
 * 
 * @author miza
 */
public class ClickTakeAllButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ItemShop.itemMinus().getItemMeta().getDisplayName())) {
					takeAll((Player) event.getWhoClicked());
				}
			}
		}
	}
	
	public void takeAll(Player player) {
		CustomHeadBlockInfo chb = MainShop.getInstance().getPlayerCustomHeadList().get(player.getName());
		Cosmetique c = Presentation.getCosmetiques().get(player);
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		
		if(chb == null || c == null || stock == null)
			return;
		
		TakeBlocks(chb, stock, c);
		takeItemInInventory(player, stock, c);
	}
	
	public static void TakeBlocks(CustomHeadBlockInfo chb, Stock stock, Cosmetique c) {
		for(Entry<Block, ItemStack> entry : chb.getBlocks().entrySet()) {
			Cosmetique cosmetique = stock.getCosmetique(entry.getValue().getItemMeta().getDisplayName().split("#")[1]);
			
			if(cosmetique == null)
				continue;
			
			if(cosmetique.getId().equals(c.getId())) {
				entry.getKey().setType(Material.AIR);
				chb.removeBlock(entry.getKey());
			}
		}
	}
	
	public static void takeItemInInventory(Player player, Stock stock, Cosmetique c) {
		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			ItemStack item = player.getInventory().getContents()[i];
			if(item == null)
				continue;
			if(!item.getItemMeta().getDisplayName().contains("#"))
				continue;
			
			Cosmetique cosmetique = stock.getCosmetique(item.getItemMeta().getDisplayName().split("#")[1]);
			if(cosmetique == null)
				continue;
			
			if(cosmetique.getId().equals(c.getId())) {
				player.getInventory().getContents()[i] = null;
			}
		}
	}
}
