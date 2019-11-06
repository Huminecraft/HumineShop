package humine.utils.events;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import humine.utils.Shopper;

public class ClickItemShopEvent extends ClickItemEvent
{
	public ClickItemShopEvent(Shopper shopper, Inventory inv, InventoryView view, ItemStack item)
	{
		super(shopper, inv, view, item);
	}
}
