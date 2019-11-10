package humine.events.shops;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.events.ClickCosmetiqueRandomShopEvent;
import humine.utils.events.ClickItemRandomShopEvent;
import humine.utils.menus.MenuAccueil;

public class RandomShopEvent implements Listener
{

	@EventHandler
	public void onClickCosmetique(ClickCosmetiqueRandomShopEvent event) {
		Presentation.openPresentation(event.getShopper().getPlayer(), event.getCosmetique());
	}
	
	@EventHandler
	public void onClickQuitButton(ClickItemRandomShopEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemQuit())) {
			MenuAccueil.openMenu(event.getShopper().getPlayer());
		}
	}
	
	@EventHandler
	public void onClickCancelMove(ClickItemRandomShopEvent event) {
		event.setCancelled(true);
	}
}
