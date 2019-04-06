package humine.utils.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.utils.ItemShop;

public class MenuIntermediaire implements Menu{

	private String name;
	
	public MenuIntermediaire() {
		this.name = "Menu";
	}
	
	@Override
	public void openMenu(Player player) {
		player.openInventory(initInventory(player));
	}

	@Override
	public Inventory initInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, this.name);

		inv.setItem(11, itemParticleShop());
		inv.setItem(13, itemHatShop());
		inv.setItem(15, itemCustomHeadShop(player));
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		inv.setItem(inv.getSize() - 5, ItemShop.itemEmpereur());
		
		return inv;
	}
	
	public ItemStack itemParticleShop() {
		ItemStack item = ItemShop.itemParticleStock();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Boutique particule");
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack itemHatShop() {
		ItemStack item = ItemShop.itemHatStock();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Boutique de tete");
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack itemCustomHeadShop(Player player) {
		ItemStack item = ItemShop.itemCustomHeadStock(player);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Boutique de tete personnalisee");
		item.setItemMeta(meta);
		
		return item;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
