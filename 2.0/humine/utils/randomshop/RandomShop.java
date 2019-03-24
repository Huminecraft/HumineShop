package humine.utils.randomshop;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.main.MainShop;
import humine.utils.Shop;
import humine.utils.Utils;

public class RandomShop extends Shop{

	private LocalDate currentDate;
	
	public RandomShop(String name) {
		super(name);
		this.currentDate = LocalDate.now();
	}
	
	public void update() {
		File folder = new File(MainShop.getInstance().getRandomShopFolder(), LocalDate.now().toString());
		if(!folder.exists())
			return;

		super.load(folder);
		this.currentDate = LocalDate.now();
	}
	
	@Override
	public void save(File folder)
	{
		super.save(folder);
		
		File index = new File(folder, "date.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		config.set("currentDate", this.currentDate.toString());
		
		try {
			config.save(index);
		} catch (IOException e) {
			System.err.println("Erreur enregistrement index.yml");
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public void load(File folder)
	{
		super.load(folder);

		File index = new File(folder, "date.yml");
		
		
		if(!index.exists()) {
			System.err.println("Erreur fichier introuvable index.yml");
			return;
		}
		
		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		
		String date = config.getString("currentDate");
		
		this.currentDate = LocalDate.of(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
		index.delete();
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
		
		
		if((shop.getPlayersOnShop().get(player) + 1) > shop.getPages().size())
			return;
		
		int page = shop.getPlayersOnShop().get(player) + 1;
		
		Inventory inv = Bukkit.createInventory(player, shop.getPage(page-1).getSize(), shop.getName());
		for(int i = 0; i < shop.getPage(page-1).getSize(); i++) {
			if(shop.getPage(page-1).getCosmetiques()[i] != null) {
				ItemStack item = Utils.CosmetiqueToItem(shop.getPage(page-1).getCosmetiques()[i]);
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
		lores.add(color + "Prix Humis: " + cosmetique.getHumisPrice());
		lores.add(color + "Prix Pixel: " + cosmetique.getPixelPrice());
		lores.add(color + "Prestige: " + cosmetique.getPrestige().toString().toLowerCase());
		if(cosmetique instanceof TemporaryParticleCosmetique) {
			lores.add(color + "Effet particule: " + ((TemporaryParticleCosmetique) cosmetique).getParticle().name().toLowerCase());
		}
		
		if(cosmetique instanceof TemporaryMaterialHatCosmetique) {
			lores.add(color + "Effet chapeau: " + ((TemporaryMaterialHatCosmetique) cosmetique).getMaterialHat().name().toLowerCase());
		}
		
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
}
