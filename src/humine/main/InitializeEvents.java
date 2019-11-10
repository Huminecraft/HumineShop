package humine.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.utils.Presentation;
import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.events.ClickCosmetiqueEvent;
import humine.utils.events.ClickCosmetiquePresentationEvent;
import humine.utils.events.ClickCosmetiqueRandomShopEvent;
import humine.utils.events.ClickCosmetiqueShopEvent;
import humine.utils.events.ClickCosmetiqueStockEvent;
import humine.utils.events.ClickItemEvent;
import humine.utils.events.ClickItemMenuAccueilEvent;
import humine.utils.events.ClickItemMenuIntermediaireEvent;
import humine.utils.events.ClickItemPresentationEvent;
import humine.utils.events.ClickItemRandomShopEvent;
import humine.utils.events.ClickItemShopEvent;
import humine.utils.events.ClickItemStockEvent;
import humine.utils.menus.MenuAccueil;
import humine.utils.menus.MenuIntermediaire;
import humine.utils.shop.RandomShop;
import humine.utils.shop.Shop;
import humine.utils.shop.Stock;

public class InitializeEvents implements Listener
{
	@EventHandler
	public void onClickItem(InventoryClickEvent event) {
		if(event.getView().getTitle().equals(Shop.SHOPNAME) || 
				event.getView().getTitle().startsWith(Stock.STOCKNAME) || 
					event.getView().getTitle().equals(RandomShop.RANDOMSHOPNAME) || 
						event.getView().getTitle().startsWith(Presentation.PRESENTATION_NAME) ||
							event.getView().getTitle().equals(MenuAccueil.ACCUEIL_NAME) ||
								event.getView().getTitle().equals(MenuIntermediaire.NAME_INTERMEDIAIRE)) {
			
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getWhoClicked() instanceof Player) {
					Shopper shopper = MainShop.getShopperManager().getShopper((Player) event.getWhoClicked());
					MainShop.getInstance().getServer().getPluginManager().callEvent(new humine.utils.events.ClickItemEvent(shopper, event.getInventory(), event.getView(), event.getCurrentItem()));
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onClickCosmetique(ClickItemEvent event) {
		if(!event.getItem().getItemMeta().getDisplayName().contains("#"))
			return;
		
		Cosmetique c = ShopUtils.getCosmetiqueInShop(event.getItem());
		if(c != null) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new humine.utils.events.ClickCosmetiqueEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem(), c));
		}
	}
	
	@EventHandler
	public void onClickItemShop(ClickItemEvent event) {
		if(event.getView().getTitle().equals(Shop.SHOPNAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickItemShopEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem()));
		}
	}
	
	@EventHandler
	public void onClickItemRandomShop(ClickItemEvent event) {
		if(event.getView().getTitle().equals(RandomShop.RANDOMSHOPNAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickItemRandomShopEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem()));
		}
	}
	
	@EventHandler
	public void onClickItemMenuAccueil(ClickItemEvent event) {
		if(event.getView().getTitle().equals(MenuAccueil.ACCUEIL_NAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickItemMenuAccueilEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem()));
		}
	}
	
	@EventHandler
	public void onClickItemMenuIntermediaire(ClickItemEvent event) {
		if(event.getView().getTitle().equals(MenuIntermediaire.NAME_INTERMEDIAIRE)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickItemMenuIntermediaireEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem()));
		}
	}
	
	@EventHandler
	public void onClickCosmetiqueShop(ClickCosmetiqueEvent event) {
		if(event.getView().getTitle().equals(Shop.SHOPNAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickCosmetiqueShopEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem(), event.getCosmetique()));
		}
	}
	
	@EventHandler
	public void onClickCosmetiqueRandomShop(ClickCosmetiqueEvent event) {
		if(event.getView().getTitle().equals(RandomShop.RANDOMSHOPNAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickCosmetiqueRandomShopEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem(), event.getCosmetique()));
		}
	}
	
	@EventHandler
	public void onClickItemStock(ClickItemEvent event) {
		if(event.getView().getTitle().startsWith(Stock.STOCKNAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickItemStockEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem()));
		}
	}
	
	@EventHandler
	public void onClickItemPresentation(ClickItemEvent event) {
		if(event.getView().getTitle().startsWith(Presentation.PRESENTATION_NAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickItemPresentationEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem()));
		}
	}
	
	@EventHandler
	public void onClickCosmetiqueStock(ClickCosmetiqueEvent event) {
		if(event.getView().getTitle().equals(Stock.STOCKNAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickCosmetiqueStockEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem(), event.getCosmetique()));
		}
	}
	
	@EventHandler
	public void onClickCosmetiquePresentation(ClickCosmetiqueEvent event) {
		if(event.getView().getTitle().startsWith(Presentation.PRESENTATION_NAME)) {
			MainShop.getInstance().getServer().getPluginManager().callEvent(new ClickCosmetiquePresentationEvent(event.getShopper(), event.getInventory(), event.getView(), event.getItem(), event.getCosmetique()));
		}
	}
}
