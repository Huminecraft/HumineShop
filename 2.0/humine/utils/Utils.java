package humine.utils;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.aypi.utils.Timer;
import com.aypi.utils.inter.TimerFinishListener;

import humine.main.MainShop;
import humine.utils.randomshop.RandomShop;



public abstract class Utils {

	
	private static HashMap<Player, Cosmetique> tryList = new HashMap<Player, Cosmetique>();
	private static HashMap<Player, Cosmetique> BuyList = new HashMap<Player, Cosmetique>();
	
	private static int size = (9 * 4);
	/**
	 * Ouvrir la premiere page de la boutique au joueur, ne fait rien
	 * si la boutique est vide
	 * @param shop la boutique a ouvrir
	 * @param player a qui ouvrir la boutique
	 */
	public static void openShop(Shop shop, Player player) {
		if(shop.isEmpty()) {
			return;
		}
		
		Inventory inv = Bukkit.createInventory(player, shop.getFirstPage().getSize()+9, shop.getName());
		for(int i = 0; i < shop.getFirstPage().getSize(); i++) {
			if(shop.getFirstPage().getCosmetiques()[i] != null) {
				ItemStack item = CosmetiqueToItem(shop.getFirstPage().getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, itemStock());
		inv.setItem(inv.getSize() - 4, itemRandomShop());
		inv.setItem(inv.getSize() - 1, addArrow("Suivant"));
		
		shop.getPlayersOnShop().put(player, 1);
		player.openInventory(inv);
	}
	
	public static void openRandomShop(RandomShop shop, Player player) {
		if(shop.isEmpty()) {
			return;
		}
		
		Inventory inv = Bukkit.createInventory(player, shop.getFirstPage().getSize()+9, shop.getName());
		for(int i = 0; i < shop.getFirstPage().getSize(); i++) {
			if(shop.getFirstPage().getCosmetiques()[i] != null) {
				ItemStack item = CosmetiqueToItem(shop.getFirstPage().getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, itemStock());
		inv.setItem(inv.getSize() - 4, itemRandomShop());
		inv.setItem(inv.getSize() - 1, addArrow("Suivant"));
		
		shop.getPlayersOnShop().put(player, 1);
		player.openInventory(inv);
	}
	
	public static void openStock(Stock stock, Player player) {
		if(stock.isEmpty()) {
			return;
		}
		
		Inventory inv = Bukkit.createInventory(player, stock.getFirstPage().getSize()+9, "Inventaire");
		for(int i = 0; i < stock.getFirstPage().getSize(); i++) {
			if(stock.getFirstPage().getCosmetiques()[i] != null) {
				ItemStack item = CosmetiqueToItem(stock.getFirstPage().getCosmetiques()[i]);
				inv.addItem(item);
			}
		}
		
		inv.setItem(inv.getSize() - 9, addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, itemQuit());
		inv.setItem(inv.getSize() - 1, addArrow("Suivant"));
		
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
		
		inv.setItem(inv.getSize() - 9, addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, itemStock());
		inv.setItem(inv.getSize() - 4, itemRandomShop());
		inv.setItem(inv.getSize() - 1, addArrow("Suivant"));
		
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
		
		inv.setItem(inv.getSize() - 9, addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, itemStock());
		inv.setItem(inv.getSize() - 4, itemRandomShop());
		inv.setItem(inv.getSize() - 1, addArrow("Suivant"));
		
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
		
		inv.setItem(inv.getSize() - 9, addArrow("Retour"));
		inv.setItem(inv.getSize() - 5, itemStock());
		inv.setItem(inv.getSize() - 4, itemRandomShop());
		inv.setItem(inv.getSize() - 1, addArrow("Suivant"));
		
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
			Page page = new Page("Page 1", size);
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
		
		ArrayList<String> lores = new ArrayList<String>();
		lores.add("Prix: " + c.getPrice());
		if(c instanceof ParticleCosmetique) {
			lores.add("Effet particule: " + ((ParticleCosmetique) c).getParticleEffect().name().toLowerCase());
		}
		
		meta.setLore(lores);
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * Permet d'ouvrir la version presentation d'un cosmetique
	 * @param player
	 * @param cosmetique
	 */
	public static void openPresentation(Player player, Cosmetique cosmetique) {
		Inventory inv = Bukkit.createInventory(player, (9 * 3), "Presentation #" + cosmetique.getId());

		
		inv.setItem(14, blockBuy(cosmetique, player));
		inv.setItem(12, blockTest(cosmetique));
		inv.setItem(18, addArrow("Retour"));

		player.openInventory(inv);
	}
	
	private static ItemStack blockBuy(Cosmetique cosmetique, Player player) {
		ItemStack item = new ItemStack(Material.GREEN_WOOL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(cosmetique.getName());
		
		List<String> lores = new ArrayList<String>();
		
		if(MainShop.getInstance().getInventories().containsStockOfPlayer(player.getName())) {
			for(Page page : MainShop.getInstance().getInventories().getStockOfPlayer(player.getName()).getPages()) {
				if(page.containsCosmetique(cosmetique)) {
					lores.add("Vous avez deja ce cosmetique");
					meta.setLore(lores);
					item.setItemMeta(meta);
					return item;
				}
			}
		}
		
		if (MainShop.getInstance().getBank().getMoney(player) >= cosmetique.getPrice())
			lores.add(ChatColor.BOLD + "" + ChatColor.GREEN + "Acheter !");
		else
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Vous n'avez pas assez de "+MainShop.getInstance().getBank().getNameValue()+" !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getPrice());
		lores.add("Vous avez " + MainShop.getInstance().getBank().getMoney(player) + " " + MainShop.getInstance().getBank().getNameValue());
		lores.add("Buy");

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
	
	private static ItemStack itemQuit() {
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Quittez");
		item.setItemMeta(meta);

		return item;
	}
	
	private static ItemStack itemStock() {
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Vos cosmetiques");
		item.setItemMeta(meta);

		return item;
	}
	
	private static ItemStack itemRandomShop() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Boutique Aleatoire");
		item.setItemMeta(meta);

		return item;
	}
	
	public static void lauchTryCosmetique(Player player, Cosmetique cosmetique, Plugin plugin) {
		if(cosmetique instanceof ParticleCosmetique) {
			tryList.put(player, cosmetique);
			Timer timer = new Timer(plugin, 5, new TimerFinishListener() {
				
				@Override
				public void execute()
				{
					tryList.remove(player);
				}
			});
			timer.start();
		}
		
	}
	
	public static void lauchBuyCosmetique(Player player, Cosmetique cosmetique) {
		if(cosmetique instanceof ParticleCosmetique) {
			BuyList.put(player, cosmetique);
		}
	}
	
	public static void disableParticleCosmetique(Player player) {
		BuyList.remove(player);
		tryList.remove(player);
	}
	
	public static void schedulerTryCosmetique(Plugin plugin) {
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run()
			{
				for(Entry<Player, Cosmetique> entry : tryList.entrySet()) {
					ParticleCosmetique c = (ParticleCosmetique) entry.getValue();
					entry.getKey().getWorld().spawnParticle(c.getParticleEffect(), entry.getKey().getLocation().getX(), entry.getKey().getLocation().getY()+1.0, entry.getKey().getLocation().getZ(), 30, 0.3, 0.3, 0.3, 1.0, null);
				}
			}
		}, 0L, 15L);
	}
	
	public static void schedulerBuyCosmetique(Plugin plugin) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			public void run()
			{
				for(Entry<Player, Cosmetique> entry : tryList.entrySet()) {
					ParticleCosmetique c = (ParticleCosmetique) entry.getValue();
					entry.getKey().getWorld().spawnParticle(c.getParticleEffect(), entry.getKey().getLocation().getX(), entry.getKey().getLocation().getY()+1.0, entry.getKey().getLocation().getZ(), 30, 0.3, 0.3, 0.3, 1.0, null);
				}
			}
		}, 0L, 15L);
	}
	
	public static void addCosmetiqueToStock(Player player, Cosmetique cosmetique, Inventories inv) {
		Stock stock = inv.getStockOfPlayer(player.getName());
		if(stock == null)
			return;
		
		if(stock.isEmpty()) {
			Page page = new Page("Page 1", size);
			stock.addPage(page);
		}
		
		if(stock.getLastPage().isFull()) {
			Page page = new Page("Page " + stock.getPages().size() + 1, size);
			stock.addPage(page);
		}
		
		stock.getLastPage().addCosmetique(cosmetique);
	}
	
	
	public static void registerTemporaryCosmetique(Cosmetique cosmetique, LocalDate date) {
		File file = new File(MainShop.getInstance().getRandomShopFolder(), date.toString());
		
		Page page;
		if(!file.exists()) {
			file.mkdirs();
			page = new Page("Page 1", size);
			page.addCosmetique(cosmetique);
			page.save(file);
		}
		else {
			for(File f : file.listFiles()) {
				if(f.listFiles().length < size) {
					cosmetique.save(new File(f, cosmetique.getId() + ".yml"));
					break;
				}
			}
		}
		
	}
}
