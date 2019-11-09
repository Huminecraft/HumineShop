package humine.events.shops;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.events.ClickCosmetiqueShopEvent;
import humine.utils.events.ClickItemShopEvent;

public class ShopEvent implements Listener
{

	@EventHandler
	public void onClickCosmetique(ClickCosmetiqueShopEvent event) {
		Presentation.openPresentation(event.getShopper().getPlayer(), event.getCosmetique());
	}
	
	@EventHandler
	public void onClickNextButton(ClickItemShopEvent event) {
		if(event.getItem().getItemMeta().getDisplayName().equals(ItemShop.itemNextArrow().getItemMeta().getDisplayName())) {
			event.getShopper().getShop().nextPage();
		}
	}
	
	@EventHandler
	public void onClickPreviousButton(ClickItemShopEvent event) {
		if(event.getItem().getItemMeta().getDisplayName().equals(ItemShop.itemPreviousArrow().getItemMeta().getDisplayName())) {
			event.getShopper().getShop().previousPage();
		}
	}
	
	@EventHandler
	public void onClickQuitButton(ClickItemShopEvent event) {
		if(event.getItem().isSimilar(ItemShop.itemQuit())) {
			MainShop.getInstance().getMenuIntermediaire().openMenu(event.getShopper().getPlayer());
		}
	}
	
	@EventHandler
	public void onClickCancelMove(ClickItemShopEvent event) {
		event.setCancelled(true);
	}
}
