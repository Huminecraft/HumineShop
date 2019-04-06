package humine.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import humine.main.MainShop;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Page;

public abstract class ItemShop {

	public static ItemStack blockHumisBuy(Cosmetique cosmetique, Player player) {
		ItemStack item = new ItemStack(Material.GOLD_BLOCK);
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
		
		if (MainShop.getInstance().getBankHumis().getMoney(player) >= cosmetique.getHumisPrice())
			lores.add(ChatColor.BOLD + "" + ChatColor.GREEN + "Acheter ! (Humis)");
		else
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Vous n'avez pas assez de "+MainShop.getInstance().getBankHumis().getNameValue()+" !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getHumisPrice());
		lores.add("Vous avez " + MainShop.getInstance().getBankHumis().getMoney(player) + " " + MainShop.getInstance().getBankHumis().getNameValue());
		lores.add("Buy with humis");

		meta.setLore(lores);

		item.setItemMeta(meta);
		return item;

	}
	
	public static ItemStack blockPixelBuy(Cosmetique cosmetique, Player player) {
		ItemStack item = new ItemStack(Material.PURPLE_WOOL);
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
		
		if (MainShop.getInstance().getBankPixel().getMoney(player) >= cosmetique.getPixelPrice())
			lores.add(ChatColor.BOLD + "" + ChatColor.GREEN + "Acheter ! (Pixel)");
		else
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Vous n'avez pas assez de "+MainShop.getInstance().getBankPixel().getNameValue()+" !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getPixelPrice());
		lores.add("Vous avez " + MainShop.getInstance().getBankPixel().getMoney(player) + " " + MainShop.getInstance().getBankPixel().getNameValue());
		lores.add("Buy with pixel");

		meta.setLore(lores);

		item.setItemMeta(meta);
		return item;

	}
	
	public static ItemStack itemNextArrow() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Suivant");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemPreviousArrow() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Precedent");
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
	
	public static ItemStack itemQuit() {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "Quittez");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemLink() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "" + ChatColor.ITALIC + "galerie des cosmetiques");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemCosmetiqueInfo(Cosmetique cosmetique) {
		ItemStack item = Cosmetique.cosmetiqueToItem(cosmetique);
		item.setType(Material.ENDER_EYE);
		
		return item;
	}
	
	public static ItemStack itemParticleStock() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Inventaire de particule");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemHatStock() {
		ItemStack item = new ItemStack(Material.TURTLE_HELMET);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Inventaire de chapeau");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemCustomHeadStock(Player player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Inventaire de tete decorative");
		meta.setOwningPlayer(player);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemEmpereur() {
		ItemStack item = new ItemStack(Material.DRAGON_EGG);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Inventaire d'empereur");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemPermanentShop() {
		ItemStack item = new ItemStack(Material.CHEST);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Boutique");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemTemporaryShop() {
		ItemStack item = new ItemStack(Material.ENDER_CHEST);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Boutique aleatoire");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
