package humine.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import humine.utils.cosmetiques.Cosmetique;

public class BankItemShop
{

	private Map<String, ItemStack> itemShops;
	
	public BankItemShop(Map<String, ItemStack> items)
	{
		this.itemShops = items;
	}
	
	public BankItemShop()
	{
		this(new HashMap<>());
	}
	
	public Map<String, ItemStack> getItemShops()
	{
		return itemShops;
	}
	
	public void setItemShops(Map<String, ItemStack> itemShops)
	{
		this.itemShops = itemShops;
	}
	
	public ItemStack getItemShop(Cosmetique cosmetique) {
		return this.itemShops.get(cosmetique.getId());
	}
	
	public ItemStack getItemShop(String id) {
		return this.itemShops.get(id);
	}
	
	public ItemStack put(Cosmetique cosmetique, ItemStack item) {
		return this.itemShops.put(cosmetique.getId(), item);
	}
}
