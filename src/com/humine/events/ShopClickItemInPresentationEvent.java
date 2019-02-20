package com.humine.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.humine.main.ShopMain;
import com.humine.utils.shop.Cosmetique;

public class ShopClickItemInPresentationEvent implements Listener {

	@EventHandler
	public void onCLick(InventoryClickEvent event) {

		if (event.getInventory().getName().startsWith("Présentation")) {
			Player player = (Player) event.getWhoClicked();

			if (event.getCurrentItem() != null) {

				if (ClickedOnBuyBlock(event.getCurrentItem())) {
					Cosmetique cosmetique = ShopMain.getInstance().getShop().getCosmetique(event.getCurrentItem().getItemMeta().getDisplayName());

					if (cosmetique != null) {
						if (ShopMain.getInstance().getGemmeManager().getGemme(player).getGemme() >= cosmetique.getPrice()) {
							if(!ShopMain.getInstance().getCosmetiquePlayerManager().getCosmetiquesOfPlayer(player).contains(cosmetique)) {
								ShopMain.getInstance().getCosmetiquePlayerManager().addCosmetique(player, cosmetique);
							}
						}
					}
				}

			}

		}
	}

	private boolean ClickedOnBuyBlock(ItemStack item) {
		if (item.getItemMeta().getLore().contains("Buy"))
			return true;
		else
			return false;
	}

	private boolean ClickedOnTryBlock(ItemStack item) {
		if (item.getItemMeta().getLore().contains("Try"))
			return true;
		else
			return false;
	}
}
