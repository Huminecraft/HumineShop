package humine.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.humine.main.ShopMain;

import humine.main.MainShop;



public abstract class Utils {

	/**
	 * Ouvrir la premiere page de la boutique au joueur, ne fait rien
	 * si la boutique est vide
	 * @param shop la boutique a ouvrir
	 * @param player a qui ouvrir la boutique
	 */
	public static void openShop(Shop shop, Player player) {
		if(shop.isEmpty())
			return;
		
		Inventory inv = Bukkit.createInventory(player, shop.getFirstPage().getSize()+9, shop.getName());
		for(int i = 0; i < shop.getFirstPage().getSize(); i++) {
			if(shop.getFirstPage().getCosmetiques()[i] != null) {
				ItemStack item = CosmetiqueToItem(shop.getFirstPage().getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		shop.getPlayersOnShop().put(player, 1);
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page suivante
	 * @param shop la boutique en question
	 * @param player le joueur en question
	 */
	public static void nextPage(Shop shop, Player player) {
		if(shop.isEmpty() || !shop.containsPlayer(player))
			return;
		
		if((shop.getPlayersOnShop().get(player) + 1) > shop.getPages().size())
			return;
		
		int page = shop.getPlayersOnShop().get(player) + 1;
		
		Inventory inv = Bukkit.createInventory(player, shop.getPage(page-1).getSize(), shop.getName());
		for(int i = 0; i < shop.getPage(page-1).getSize(); i++) {
			if(shop.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = CosmetiqueToItem(shop.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		shop.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page precedente
	 * @param shop la boutique en question
	 * @param player le joueur en question
	 */
	public static void previousPage(Shop shop, Player player) {
		if(shop.isEmpty() || !shop.containsPlayer(player))
			return;
		
		if((shop.getPlayersOnShop().get(player) - 1) < 1)
			return;
		
		int page = shop.getPlayersOnShop().get(player) - 1;
		
		Inventory inv = Bukkit.createInventory(player, shop.getPage(page-1).getSize(), shop.getName());
		for(int i = 0; i < shop.getPage(page-1).getSize(); i++) {
			if(shop.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = CosmetiqueToItem(shop.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		shop.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page numPage
	 * @param shop la boutique en question
	 * @param player le joueur en question
	 * @param numPage la page voulu
	 */
	public static void goToPage(Shop shop, Player player, int numPage) {
		if(shop.isEmpty() || !shop.containsPlayer(player))
			return;
		
		if((shop.getPlayersOnShop().get(player) + 1) > shop.getPages().size())
			return;
		
		int page = shop.getPlayersOnShop().get(player) + 1;
		
		Inventory inv = Bukkit.createInventory(player, shop.getPage(page-1).getSize(), shop.getName());
		for(int i = 0; i < shop.getPage(page-1).getSize(); i++) {
			if(shop.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = CosmetiqueToItem(shop.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		shop.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}
	
	/**
	 * ajoute un cosmetique dans un shop
	 * @param shop le shop dans lequel ajouter
	 * @param cosmetique le cosmetique a ajouter
	 */
	public static void addCosmetique(Shop shop, Cosmetique cosmetique) {
		if(!shop.isEmpty()) {
			if(!shop.getLastPage().isFull()) {
				shop.getLastPage().addCosmetique(cosmetique);
			}
			else {
				Page page = new Page("Page "+(Shop.getNumId()+1), shop.getLastPage().getSize());
				page.addCosmetique(cosmetique);
				shop.addPage(page);
			}
		}
		else {
			Page page = new Page("Page 1", 36);
			page.addCosmetique(cosmetique);
			shop.addPage(page);
		}
		
	}
	
	/**
	 * Convertir un cosmetique en itemStack
	 * @param c le cosmetique
	 */
	public static ItemStack CosmetiqueToItem(Cosmetique c) {
		ItemStack item = new ItemStack(c.getItemShop());
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(c.getName() + " #" + c.getId());
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * Permet d'ouvrir la version presentation d'un cosmetique
	 * @param player
	 * @param cosmetique
	 */
	public static void openPresentation(Player player, Cosmetique cosmetique) {
		Inventory inv = Bukkit.createInventory(player, (9 * 3), "Présentation #" + cosmetique.getId());

		inv.setItem(14, blockBuy(cosmetique, player));
		inv.setItem(12, blockTest(cosmetique));
		inv.setItem(18, addArrow("Retour"));

		player.openInventory(inv);
	}
	
	private static ItemStack blockBuy(Cosmetique cosmetique, Player player) {
		ItemStack item = new ItemStack(Material.GREEN_WOOL);
		ItemMeta meta = item.getItemMeta();

		List<String> lores = new ArrayList<String>();

		if (MainShop.getInstance().getBank().getMoney(player) >= cosmetique.getPrice())
			lores.add(ChatColor.BOLD + "" + ChatColor.GREEN + "Acheter !");
		else
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Vous n'avez pas assez de "+MainShop.getInstance().getBank().getNameValue()+" !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getPrice());
		lores.add("Vous avez " + MainShop.getInstance().getBank().getMoney(player) + " " + MainShop.getInstance().getBank().getNameValue());
		lores.add("Buy");

		meta.setDisplayName(cosmetique.getName());

		meta.setLore(lores);

		item.setItemMeta(meta);
		return item;

	}

	private static ItemStack blockTest(Cosmetique cosmetique) {
		ItemStack item = new ItemStack(Material.PURPLE_WOOL);
		ItemMeta meta = item.getItemMeta();
		ArrayList<String> lores = new ArrayList<String>();

		meta.setDisplayName(cosmetique.getName());

		lores.add(ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Essayer le !");
		lores.add("Try");
		meta.setLore(lores);

		item.setItemMeta(meta);

		return item;

	}

	private static ItemStack addArrow(String name) {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + name);
		item.setItemMeta(meta);

		return item;
	}
}
