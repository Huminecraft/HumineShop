package humine.events.stocks;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.ParticleScheduler;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.MaterialHatCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryMaterialHatCosmetique;
import humine.utils.shop.HatStock;
import humine.utils.shop.ParticleStock;
import humine.utils.shop.Shop;

public class ClickCosmetiqueButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		
		if(event.getInventory().getName().startsWith(ParticleStock.getParticleStockName())) {
			ParticleStock pStock = MainShop.getInstance().getParticleStockList().get(player.getName());
			if(pStock != null) {
				clickParticleCosmetique(pStock, event.getCurrentItem(), player);
			}
		}
		else if(event.getInventory().getName().startsWith(HatStock.getHatStockName())) {
			HatStock pStock = MainShop.getInstance().getHatStockList().get(player.getName());
			if(pStock != null) {
				clickHatCosmetique(pStock, event.getCurrentItem(), player);
			}
		}
	}
	
	public void clickParticleCosmetique(Shop shop, ItemStack item, Player player) {
		if(item != null && item.getType() != Material.AIR) {
			if(item.getItemMeta().getDisplayName().contains("#")) {
				Cosmetique c = shop.getCosmetique(item.getItemMeta().getDisplayName().split("#")[1]);
				
				if(c != null) {
					ParticleScheduler.enableParticleCosmetique(player, c);
					player.closeInventory();
				}
			}
		}
	}
	
	public void clickHatCosmetique(Shop shop, ItemStack item, Player player) {
		if(item != null && item.getType() != Material.AIR) {
			if(item.getItemMeta().getDisplayName().contains("#")) {
				Cosmetique c = shop.getCosmetique(item.getItemMeta().getDisplayName().split("#")[1]);
				
				if(c != null) {
					if(player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getType() != Material.AIR) {
						if(!player.getInventory().getHelmet().getItemMeta().getDisplayName().contains("#")) {
							MainShop.sendMessage(player, "Veuillez retirer votre casque d'abord");
						}
						else {
							player.getInventory().setHelmet(cosmetiqueToItem(c));
						}
					}
					else {
						player.getInventory().setHelmet(cosmetiqueToItem(c));
					}
					
					player.closeInventory();
				}
			}
		}
	}
	
	private ItemStack cosmetiqueToItem(Cosmetique cosmetique) {
		ItemStack item = Cosmetique.cosmetiqueToItem(cosmetique);
		
		if(cosmetique instanceof MaterialHatCosmetique)
			item.setType(((MaterialHatCosmetique) cosmetique).getMaterialHat());
		else if(cosmetique instanceof TemporaryMaterialHatCosmetique)
			item.setType(((TemporaryMaterialHatCosmetique) cosmetique).getMaterialHat());
		
		return item;
	}
}
