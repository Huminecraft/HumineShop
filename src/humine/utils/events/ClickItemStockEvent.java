package humine.utils.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import humine.utils.Shopper;

public class ClickItemStockEvent extends Event implements Cancellable
{
	
	private static HandlerList handler = new HandlerList();
	private boolean cancel;
	
	private Shopper shopper;
	private Inventory inv;
	private ItemStack item;
	private InventoryView view;
	
	public ClickItemStockEvent(Shopper shopper, Inventory inv, InventoryView view, ItemStack item)
	{
		this.shopper = shopper;
		this.inv = inv;
		this.item = item;
		this.view = view;
		this.cancel = false;
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
