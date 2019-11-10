package humine.utils.menus;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.utils.ItemShop;
import net.md_5.bungee.api.ChatColor;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant le menu intermediaire de HumineShop
 * 
 * @author miza
 */
public abstract class MenuIntermediaire {

	public static final String NAME_INTERMEDIAIRE = "Menu intermediaire";
	
	public static void openMenu(Player player) {
		player.openInventory(initInventory(player));
	}

	public static Inventory initInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, (9 * 4), NAME_INTERMEDIAIRE);

		inv.setItem(11, itemParticleShop());
		inv.setItem(13, itemHatShop());
		inv.setItem(15, itemCustomHeadShop());
		
		inv.setItem(inv.getSize() - 9, ItemShop.itemQuit());
		inv.setItem(inv.getSize() - 1, ItemShop.itemQuit());
		
		inv.setItem(inv.getSize() - 5, ItemShop.itemEmpereur());
		
		return inv;
	}
	
	public static ItemStack itemParticleShop() {
		ItemStack item = ItemShop.itemParticleStock();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Particules");
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemHatShop() {
		ItemStack item = ItemShop.itemHatStock();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Chapeaux");
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemCustomHeadShop() {
		ItemStack item = ItemShop.itemCustomHeadStock();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Tetes decoratives");
		item.setItemMeta(meta);
		
		return item;
	}
}
