package humine.utils.events;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import humine.utils.Shopper;

public class ClickItemPresentationEvent extends ClickItemEvent
{
	
	public ClickItemPresentationEvent(Shopper shopper, Inventory inv, InventoryView view, ItemStack item)
	{
		super(shopper, inv, view, item);
	}
}
