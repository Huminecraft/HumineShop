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
import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;

public abstract class ItemShop {

	public static ItemStack blockHumisBuy(Cosmetique cosmetique, Player player) {
		ItemStack item = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + cosmetique.getName());
		
		List<String> lores = new ArrayList<String>();
		
		if(!(cosmetique instanceof CustomHeadCosmetique) && !(cosmetique instanceof TemporaryCustomHeadCosmetique)) {
			if(MainShop.getInstance().getInventories().containsStockOfPlayer(player.getName())) {
				if(MainShop.getInstance().getInventories().getStockOfPlayer(player.getName()).getCosmetique(cosmetique.getId()) != null) {
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
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Obtenir plus de " + MainShop.getInstance().getBankHumis().getNameValue()+" !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getHumisPrice());
		lores.add("Vous avez " + MainShop.getInstance().getBankHumis().getMoney(player) + " " + MainShop.getInstance().getBankHumis().getNameValue());

		meta.setLore(lores);

		item.setItemMeta(meta);
		return item;

	}
	
	public static ItemStack blockPixelBuy(Cosmetique cosmetique, Player player) {
		ItemStack item = new ItemStack(Material.PURPLE_WOOL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + cosmetique.getName());
		
		List<String> lores = new ArrayList<String>();
		
		if(!(cosmetique instanceof CustomHeadCosmetique) && !(cosmetique instanceof TemporaryCustomHeadCosmetique)) {
			if(MainShop.getInstance().getInventories().containsStockOfPlayer(player.getName())) {
				if(MainShop.getInstance().getInventories().getStockOfPlayer(player.getName()).getCosmetique(cosmetique.getId()) != null) {
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
			lores.add(ChatColor.BOLD + "" + ChatColor.RED + "Obtenir plus de " + MainShop.getInstance().getBankPixel().getNameValue()+" !");

		lores.add("Prix: " + ChatColor.GREEN + cosmetique.getPixelPrice());
		lores.add("Vous avez " + MainShop.getInstance().getBankPixel().getMoney(player) + " " + MainShop.getInstance().getBankPixel().getNameValue());

		meta.setLore(lores);

		item.setItemMeta(meta);

		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack itemNextArrow() {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner("MHF_ArrowRight");
		meta.setDisplayName(ChatColor.WHITE + "Suivant");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack itemPreviousArrow() {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner("MHF_ArrowLeft");
		meta.setDisplayName(ChatColor.WHITE + "Precedent");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack itemMinus() {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner("MHF_ArrowDown");
		meta.setDisplayName(ChatColor.WHITE + "Tout recuperer");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack itemPlus() {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner("MHF_ArrowUp");
		meta.setDisplayName(ChatColor.WHITE + "Prendre un custom head");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemDamiStick() {
		ItemStack item = new ItemStack(Material.STICK);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Dami-Stick");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemDisable() {
		ItemStack item = new ItemStack(Material.MILK_BUCKET);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Desactiver");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemStock() {
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + "Vos cosmetiques");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemRandomShop() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Boutique Aleatoire");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemQuit() {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Quitter");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemLink() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Galerie des cosmetiques");
		item.setItemMeta(meta);

		return item;
	}
	
	public static ItemStack itemCosmetiqueInfo(Cosmetique cosmetique) {
		ItemStack item = Cosmetique.cosmetiqueToItem(cosmetique);
		item.setType(Material.ENDER_EYE);
		
		return item;
	}
	
	public static ItemStack itemCosmetiqueInfo(Cosmetique cosmetique, int number) {
		ItemStack item = Cosmetique.cosmetiqueToItem(cosmetique);
		item.setAmount(number);
		
		return item;
	}
	
	public static ItemStack itemParticleStock() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Inventaire de particules");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemHatStock() {
		ItemStack item = new ItemStack(Material.TURTLE_HELMET);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Inventaire de chapeaux");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack itemCustomHeadStock() {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner("MHF_Present2");
		meta.setDisplayName(ChatColor.WHITE + "Inventaire de tetes decoratives");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemEmpereur() {
		ItemStack item = new ItemStack(Material.DRAGON_EGG);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.GOLD + "Boutique Empereur");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemPermanentShop() {
		ItemStack item = new ItemStack(Material.GLOWSTONE_DUST);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Boutique");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack itemTemporaryShop() {
		ItemStack item = new ItemStack(Material.ENDER_CHEST);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Boutique aleatoire");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
