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
import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;
import humine.utils.shop.Stock;

public class ClickTakeAllButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemMinus())) {
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
		
		for(Entry<Block, ItemStack> entry : chb.getBlocks().entrySet()) {
			Cosmetique cosmetique = stock.getCosmetique(entry.getValue().getItemMeta().getDisplayName().split("#")[1]);
			
			if(cosmetique == null)
				continue;
			
			if(cosmetique instanceof CustomHeadCosmetique || cosmetique instanceof TemporaryCustomHeadCosmetique) {
				entry.getKey().setType(Material.AIR);
				chb.removeBlock(entry.getKey());
			}
		}
		
		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			ItemStack item = player.getInventory().getContents()[i];
			if(item == null)
				continue;
			if(!item.getItemMeta().getDisplayName().contains("#"))
				continue;
			
			Cosmetique cosmetique = stock.getCosmetique(item.getItemMeta().getDisplayName().split("#")[1]);
			if(cosmetique == null)
				continue;
			
			if(c.equals(cosmetique))
				somme += item.getAmount();
			
		}
	}
}
