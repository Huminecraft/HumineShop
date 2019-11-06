package humine.utils.events;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import humine.utils.Shopper;

public class ClickItemStockEvent extends ClickItemEvent
{
	
	public ClickItemStockEvent(Shopper shopper, Inventory inv, InventoryView view, ItemStack item)
	{
		super(shopper, inv, view, item);
	}
}
