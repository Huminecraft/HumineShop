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



public abstract class Utils {

	
	private static HashMap<Player, Cosmetique> tryList = new HashMap<Player, Cosmetique>();
	private static HashMap<Player, Cosmetique> BuyList = new HashMap<Player, Cosmetique>();
	
	private static int size = (9 * 4);
	
	
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
		
		if (MainShop.getInstance().getBankHumis().getMoney(player) >= cosmetique.getPrice())
			lores.add(ChatColor.BOLD + "" + ChatColor.GREEN + "Acheter !");
		else
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Vous n'avez pas assez de "+MainShop.getInstance().getBankHumis().getNameValue()+" !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getPrice());
		lores.add("Vous avez " + MainShop.getInstance().getBankHumis().getMoney(player) + " " + MainShop.getInstance().getBankHumis().getNameValue());
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

	public static ItemStack addArrow(String name) {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + name);
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemQuit() {
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Quittez");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemDisable() {
		ItemStack item = new ItemStack(Material.MILK_BUCKET);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Desactiver");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemStock() {
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Vos cosmetiques");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemRandomShop() {
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
		BuyList.put(player, cosmetique);
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
				for(Entry<Player, Cosmetique> entry : BuyList.entrySet()) {
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
