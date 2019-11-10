package humine.utils.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;

public class ClickCosmetiqueShopEvent extends Event implements Cancellable
{
	private static HandlerList handler = new HandlerList();
	private boolean cancel;
	
	private Shopper shopper;
	private Inventory inv;
	private ItemStack item;
	private InventoryView view;
	private Cosmetique cosmetique;

	public ClickCosmetiqueShopEvent(Shopper shopper, Inventory inv, InventoryView view, ItemStack item, Cosmetique cosmetique)
	{
		this.shopper = shopper;
		this.inv = inv;
		this.item = item;
		this.view = view;
		this.cancel = false;
		this.cosmetique = cosmetique;
	}
	
	public Shopper getShopper()
	{
		return shopper;
	}
	
	public Inventory getInventory()
	{
		return inv;
	}
	public ItemStack getItem()
	{
		return item;
	}
	
	public InventoryView getView()
	{
		return view;
	}
	
	public Cosmetique getCosmetique()
	{
		return cosmetique;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handler;
	}
	
	public static HandlerList getHandlerList() {
		return handler;
	}

	@Override
	public boolean isCancelled()
	{
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel)
	{
		this.cancel = cancel;
	}
}
