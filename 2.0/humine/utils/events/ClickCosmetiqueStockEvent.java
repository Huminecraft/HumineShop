package humine.utils.events;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;

public class ClickCosmetiqueStockEvent extends ClickCosmetiqueEvent
{
	public ClickCosmetiqueStockEvent(Shopper shopper, Inventory inv, InventoryView view, ItemStack item, Cosmetique cosmetique)
	{
		super(shopper, inv, view, item, cosmetique);
	}
}
