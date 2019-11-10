package humine.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.main.ShopUtils;
import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant de supprimer un cosmetique material hat
 * se trouvant sur la tete du joueur en cliquant dessus
 * 
 * @author miza
 */
public class ClickMaterialInventory implements Listener
{

	@EventHandler
	public void onclick(InventoryClickEvent event) {

		if(event.getSlot() == 39) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().contains("#")) {
					Cosmetique c = getCosmetique(event.getCurrentItem(), (Player) event.getWhoClicked());
					if(c != null) {
						event.setCancelled(true);
						event.getWhoClicked().getInventory().setHelmet(null);
					}
				}
			}
		}
	}
	
	private Cosmetique getCosmetique(ItemStack item, Player player)
	{
		Shopper shopper = MainShop.getShopperManager().getShopper(player);
		if(shopper == null)
			return null;
		
		return ShopUtils.getCosmetiqueInStock(shopper, item);
	}
}
