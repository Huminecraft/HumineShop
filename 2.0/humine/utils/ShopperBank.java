package humine.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class ShopperBank
{

	private Map<Player, Shopper> shoppers;
	
	public ShopperBank(Map<Player, Shopper> shoppers) {
		this.shoppers = shoppers;
	}
	
	public ShopperBank() {
		this(new HashMap<>());
	}
	
	public Shopper getShopper(Player player) {
		return this.shoppers.get(player);
	}
	
	public Shopper addShopper(Shopper shopper) {
		return this.shoppers.put(shopper.getPlayer(), shopper);
	}
	
	public Shopper removeShopper(Shopper shopper) {
		return this.shoppers.remove(shopper.getPlayer());
	}
	
	public Shopper removeShopper(Player player) {
		return this.shoppers.remove(player);
	}
	
	public Map<Player, Shopper> getShoppers() {
		return shoppers;
	}
	
	public Collection<Shopper> getOnlyShoppers() {
		return shoppers.values();
	}
}
