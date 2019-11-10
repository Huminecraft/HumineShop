package humine.main;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import humine.utils.Prestige;
import humine.utils.Shopper;
import humine.utils.cosmetiques.Cosmetique;

public abstract class ShopUtils
{

	public static Material getItem(String str) {
		for(Material m : Material.values()) {
			if(m.name().equalsIgnoreCase(str))
				return m;
		}
		return null;
	}
	
	public static Prestige getPrestige(String str) {
		for(Prestige p : Prestige.values()) {
			if(p.name().equalsIgnoreCase(str))
				return p;
		}
		return null;
	}

	public static boolean isNumber(String str) {
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) < '0' || str.charAt(i) > '9')
				return false;
		}
		return true;
	}
	
	public static Particle getParticle(String str) {
		for(Particle p : Particle.values()) {
			if(p.name().equalsIgnoreCase(str))
				return p;
		}
		return null;
	}
	
	public static Biome getBiome(String str) {
		for(Biome b : Biome.values()) {
			if(b.name().equalsIgnoreCase(str))
				return b;
		}
		return null;
	}
	
	public static EntityType getEntity(String str) {
		for(EntityType e : EntityType.values()) {
			if(e.name().equalsIgnoreCase(str))
				return e;
		}
		return null;
	}
	
	public static boolean isCosmetique(Shopper shopper, ItemStack item) {
		return getCosmetiqueInShop(item) != null || getCosmetiqueInStock(shopper, item) != null;
	}
	
	public static Cosmetique getCosmetiqueInShop(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(meta.getDisplayName().contains("#")) {
			if(meta.getDisplayName().split("#").length >= 2) {
				Cosmetique cShop = MainShop.getInstance().getShop().getCosmetique(meta.getDisplayName().split("#")[1]);
				Cosmetique cRShop = MainShop.getInstance().getRandomShop().getCosmetique(meta.getDisplayName().split("#")[1]);
				Cosmetique cEShop = MainShop.getInstance().getEmperorShop().getCosmetique(meta.getDisplayName().split("#")[1]);
				if(cShop != null)
					return cShop;
				else if(cRShop != null)
					return cRShop;
				else if(cEShop != null)
					return cEShop;
			}
		}
		return null;
	}
	
	public static Cosmetique getCosmetiqueInStock(Shopper shopper, ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(meta.getDisplayName().contains("#")) {
			if(meta.getDisplayName().split("#").length >= 2) {
				for(Cosmetique c : shopper.getCosmetiques()) {
					if(c.getId().equals(meta.getDisplayName().split("#")[1]))
						return c;
				}
			}
		}
		return null;
	}
}
