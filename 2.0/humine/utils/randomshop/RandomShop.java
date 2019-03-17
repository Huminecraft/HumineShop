package humine.utils.randomshop;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.utils.Page;
import humine.utils.Shop;
import humine.utils.Utils;

public class RandomShop extends Shop{

	private LocalDate currentDate;
	
	public RandomShop(String name) {
		super(name);
		this.currentDate = LocalDate.now();
	}
	
	public void update(File folder) {
		if(!folder.exists())
			return;
		
		File file = new File(folder, LocalDate.now().toString());
		if(!file.exists())
			return;

		resetShop();
		for(File f : file.listFiles()) {
			Page page = new Page("", 0);
			page.load(f);
			this.addPage(page);
		}
		
		this.currentDate = LocalDate.now();
	}
	
	public void resetShop() {
		this.getPages().clear();
	}
	
	public LocalDate getCurrentDate() {
		return currentDate;
	}
	
	/**
	 * Ouvrir la premiere page de la boutique aleatoire au joueur, ne fait rien
	 * si la boutique est vide
	 * @param shop la boutique a ouvrir
	 * @param player a qui ouvrir la boutique
	 */
	public static void openRandomShop(RandomShop shop, Player player) {
		if(shop.isEmpty()) {
			return;
		}
		
		Inventory inv = Bukkit.createInventory(player, shop.getFirstPage().getSize()+9, shop.getName());
		for(int i = 0; i < shop.getFirstPage().getSize(); i++) {
			if(shop.getFirstPage().getCosmetiques()[i] != null) {
				ItemStack item = cosmetiqueToItem((TemporaryCosmetique) shop.getFirstPage().getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, Utils.addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, Utils.itemQuit());
		inv.setItem(inv.getSize() - 1, Utils.addArrow("Suivant"));
		
		shop.getPlayersOnShop().put(player, 1);
		player.openInventory(inv);
	}
	
	/**
	 * Permet d'aller a la page suivante
	 * @param shop la boutique en question
	 * @param player le joueur en question
	 */
	public static void nextPage(RandomShop shop, Player player) {
		if(shop.isEmpty() || !shop.containsPlayer(player))
			return;
		
		Bukkit.broadcastMessage("DEBUG 3");
		
		if((shop.getPlayersOnShop().get(player) + 1) > shop.getPages().size())
			return;
		
		Bukkit.broadcastMessage("DEBUG 3");
		
		int page = shop.getPlayersOnShop().get(player) + 1;
		
		Inventory inv = Bukkit.createInventory(player, shop.getPage(page-1).getSize(), shop.getName());
		for(int i = 0; i < shop.getPage(page-1).getSize(); i++) {
			if(shop.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = Utils.CosmetiqueToItem(shop.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		Bukkit.broadcastMessage("DEBUG 3");
		
		inv.setItem(inv.getSize() - 9, Utils.addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, Utils.itemQuit());
		inv.setItem(inv.getSize() - 1, Utils.addArrow("Suivant"));
		
		shop.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
		
		Bukkit.broadcastMessage("DEBUG 4");
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
				ItemStack item = cosmetiqueToItem((TemporaryCosmetique) shop.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, Utils.addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, Utils.itemQuit());
		inv.setItem(inv.getSize() - 1, Utils.addArrow("Suivant"));
		
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
				ItemStack item = cosmetiqueToItem((TemporaryCosmetique) shop.getPage(page-1).getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, Utils.addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, Utils.itemQuit());
		inv.setItem(inv.getSize() - 1, Utils.addArrow("Suivant"));
		
		shop.getPlayersOnShop().replace(player, page);
		player.openInventory(inv);
	}
	
	private static ItemStack cosmetiqueToItem(TemporaryCosmetique cosmetique) {
		ItemStack item = new ItemStack(cosmetique.getItemShop());
		ChatColor color = ChatColor.valueOf(cosmetique.getPrestige().getColor());
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color + cosmetique.getName() + " #" + cosmetique.getId());
		
		ArrayList<String> lores = new ArrayList<String>();
		lores.add(color + "Prix: " + cosmetique.getPrice());
		lores.add(color + "Prestige: " + cosmetique.getPrestige().toString().toLowerCase());
		if(cosmetique instanceof TemporaryParticleCosmetique) {
			lores.add(color + "Effet particule: " + ((TemporaryParticleCosmetique) cosmetique).getParticle().name().toLowerCase());
		}
		
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
}
