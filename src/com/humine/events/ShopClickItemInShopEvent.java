package com.humine.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.humine.main.ShopMain;
import com.humine.utils.shop.Cosmetique;
import com.humine.utils.shop.Presentation;

public class ShopClickItemInShopEvent implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event)
	{
		
		if (event.getInventory().getName().startsWith(ShopMain.getInstance().getShop().getName()))
		{
			if (event.getCurrentItem() != null)
			{
				if (event.getCurrentItem().getType() == Material.ARROW)
					clickArrowForChangePage((Player) event.getWhoClicked(), event.getCurrentItem());
				else
				{
					blockToPresentation(event.getCurrentItem(), (Player) event.getWhoClicked());
				}
			}
		}
	}

	private void clickArrowForChangePage(Player player, ItemStack item)
	{

		if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "" + ChatColor.ITALIC + "Precedent"))
		{
			ShopMain.getInstance().getShop().previousPage(player);
		}

		if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "" + ChatColor.ITALIC + "Suivant"))
		{
			ShopMain.getInstance().getShop().nextPage(player);
		}

		if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "" + ChatColor.ITALIC + "Retour"))
		{
			ShopMain.getInstance().getShop().openShop(player);
		}
	}

	private void blockToPresentation(ItemStack item, Player player)
	{
		Cosmetique cosmetique = ShopMain.getInstance().getShop().getCosmetique(item.getItemMeta().getDisplayName());

		if (cosmetique != null)
		{
			Presentation.openPresentation(cosmetique, player);
		}
	}
}
