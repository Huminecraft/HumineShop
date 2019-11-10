package humine.events.presentation;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import humine.main.ShopUtils;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.Shopper;
import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.CustomHatLocation;
import humine.utils.events.ClickItemPresentationEvent;

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
	public void onClick(ClickItemPresentationEvent event) {
		if(event.getItem().getItemMeta().getDisplayName().equals(ItemShop.itemMinus().getItemMeta().getDisplayName())) {
			takeAll(event.getShopper());
		}
	}
	
	public void takeAll(Shopper shopper) {
		Cosmetique c = Presentation.getCosmetiques().get(shopper.getPlayer());
		if(c == null || !(c instanceof AbstractCustomHatCosmetique))
			return;
		
		AbstractCustomHatCosmetique cosmetique = (AbstractCustomHatCosmetique) c;
		
		TakeBlocks(cosmetique);
		takeItemInInventory(shopper, cosmetique);
	}
	
	public static void TakeBlocks(AbstractCustomHatCosmetique cosmetique) {

		for(CustomHatLocation l : cosmetique.getCustomHatData().getLocations()) {
			Location location = l.convertToLocation();
			Block block = location.getWorld().getBlockAt(location);
			block.setType(Material.AIR);
			cosmetique.getCustomHatData().setInStock((cosmetique.getCustomHatData().getInStock() + 1));
		}
		
		cosmetique.getCustomHatData().resetLocation();
	}
	
	public static void takeItemInInventory(Shopper shopper, AbstractCustomHatCosmetique c) {
		if(c.getCustomHatData().getInInventory() <= 0)
			return;
			
		for(int i = 0; i < shopper.getPlayer().getInventory().getStorageContents().length; i++) {
			ItemStack item = shopper.getPlayer().getInventory().getContents()[i];
			if(item == null)
				continue;
			
			Cosmetique cosmetique = ShopUtils.getCosmetiqueInStock(shopper, item);
			if(cosmetique == null)
				continue;
			
			if(cosmetique.getId().equals(c.getId())) {
				int amount = item.getAmount();
				c.getCustomHatData().setInStock((c.getCustomHatData().getInStock() + amount));
				c.getCustomHatData().setInInventory((c.getCustomHatData().getInInventory() - amount));
				shopper.getPlayer().getInventory().setItem(i, null);
			}
				
		}
	}
}
