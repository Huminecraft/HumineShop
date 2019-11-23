package humine.events.presentation;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.events.ClickItemPresentationEvent;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant d'acheter si possible avec les pixels
 * 
 * @author miza
 */
public class ClickPixelBuyButton implements Listener
{
	
	@EventHandler
	public void onClick(ClickItemPresentationEvent event) {
		Cosmetique c = Presentation.getCosmetiques().get(event.getShopper().getPlayer());
		if(c == null)
			return;
		
		if(event.getItem().isSimilar(ItemShop.blockPixelBuy(c, event.getShopper().getPlayer()))) {
			if(!(c instanceof AbstractCustomHatCosmetique) && event.getShopper().getStock().containsCosmetique(c.getId()))
				return;
			
			if(c.getPixelPrice() <= event.getShopper().getPixel().getAmount()) {
				if(c instanceof AbstractCustomHatCosmetique) {
					AbstractCustomHatCosmetique custom = (AbstractCustomHatCosmetique) event.getShopper().getStock().getCosmetique(c.getId());
					if(custom != null)
						custom.setAmount((custom.getAmount() + 1));
					else
						event.getShopper().getCosmetiques().add(custom);
				}
				
				event.getShopper().getCosmetiques().add(c);
				event.getShopper().getPixel().removeAmount(c.getPixelPrice());
				event.getShopper().getPlayer().closeInventory();
				event.getShopper().getPlayer().getWorld().playSound(event.getShopper().getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100.0f, 1.0f);
				MainShop.sendMessage(event.getShopper().getPlayer(), "Cosmetique achete ! il se trouve maintenant dans votre stock. /shop");
			}
			else {
				event.getShopper().getPlayer().closeInventory();
				sendLink(event.getShopper().getPlayer());
			}
		}
	}
	
	private void sendLink(Player player) {
		MainShop.sendMessage(player, ChatColor.LIGHT_PURPLE + "Tu n'as pas assez de pixels. Pour en obtenir tu dois completer les defis quotidiens et hebdomadaires. /dailychallenge");
	}
}
