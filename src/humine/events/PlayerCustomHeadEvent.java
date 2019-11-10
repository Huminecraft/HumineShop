package humine.events;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import humine.main.MainShop;
import humine.main.ShopUtils;
import humine.utils.Shopper;
import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.CustomHatLocation;
import humine.utils.cosmetiques.TypeCosmetique;

/**
 * Package regroupant les evenements "principals" du plugin HumineShop
 * Classe d'evenement permettant de faire les verifications pour securiser
 * les actions faite avec un cosmetique custom head
 * 
 * @author miza
 */
public class PlayerCustomHeadEvent implements Listener
{

	@EventHandler
	public void onPose(BlockPlaceEvent event) {
		if(event.getBlock().getType() != Material.PLAYER_HEAD)
			return;
		
		if(!event.getItemInHand().getItemMeta().getDisplayName().contains("#"))
			return;
		
		Shopper shopper = MainShop.getShopperManager().getShopper(event.getPlayer());
		if(shopper == null)
			return;
		
		Cosmetique c = ShopUtils.getCosmetiqueInStock(shopper, event.getItemInHand());
		if(c != null && c instanceof AbstractCustomHatCosmetique) {
			AbstractCustomHatCosmetique cosmetique = (AbstractCustomHatCosmetique) c;
			cosmetique.getCustomHatData().setInInventory(cosmetique.getCustomHatData().getInInventory() - 1);
			cosmetique.getCustomHatData().addLocation(new CustomHatLocation(event.getBlock().getLocation()));
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() != Material.PLAYER_HEAD)
			return;
		
		Shopper shopper = MainShop.getShopperManager().getShopper(event.getPlayer());
		if(shopper == null)
			return;
		
		if(belongToPlayer(event.getBlock())) {
			MainShop.sendMessage(shopper.getPlayer(), "Vous ne pouvez pas casser cette tete");
			event.setCancelled(true);
			return;
		}
		
		CustomHatLocation loc = new CustomHatLocation(event.getBlock().getLocation());
		List<Cosmetique> cosmetiques = shopper.getCosmetiques(TypeCosmetique.CUSTOM_HAT);
		AbstractCustomHatCosmetique cosmetique = null;
		
		for(Cosmetique c : cosmetiques) {
			AbstractCustomHatCosmetique ac = (AbstractCustomHatCosmetique) c;
			if(ac.getCustomHatData().getLocations().contains(loc))
				cosmetique = ac;
		}
		
		if(cosmetique == null)
			return;
		
		int slot = getFreeSlot(shopper.getPlayer());
		if(slot != -1) {
			event.getPlayer().getInventory().setItem(slot, cosmetique.convertToItem());
			cosmetique.getCustomHatData().setInInventory(cosmetique.getCustomHatData().getInInventory() + 1);
		}
		else {
			MainShop.sendMessage(shopper.getPlayer(), "Vous n'avez plus de place dans votre inventaire, le cosmetique a etait remis dans votre stock");
			cosmetique.getCustomHatData().setInStock(cosmetique.getCustomHatData().getInStock() + 1);
		}
			
		event.setCancelled(true);
		event.getBlock().setType(Material.AIR);
		cosmetique.getCustomHatData().getLocations().remove(loc);
	}
	
	private int getFreeSlot(Player player) {
		for(int i = 0; i < player.getInventory().getStorageContents().length; i++) {
			if(player.getInventory().getStorageContents()[i] == null || player.getInventory().getStorageContents()[i].getType() == Material.AIR) {
				return i;
			}
		}
		return -1;
	}
	
	
	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		for(int i = 0; i < event.blockList().size(); i++) {
			if(event.blockList().get(i).getType() == Material.PLAYER_HEAD) {
				if(belongToPlayer(event.blockList().get(i)))
					event.blockList().remove(i);
			}
		}
	}
	
	private boolean belongToPlayer(Block block) {
		CustomHatLocation loc = new CustomHatLocation(block.getLocation());
		
		for(Shopper shopper : MainShop.getShopperManager().getOnlyShoppers()) {
			List<Cosmetique> cosmetiques = shopper.getCosmetiques(TypeCosmetique.CUSTOM_HAT);
			
			for(Cosmetique c : cosmetiques) {
				AbstractCustomHatCosmetique ac = (AbstractCustomHatCosmetique) c;
				if(ac.getCustomHatData().getLocations().contains(loc))
					return true;
			}
		}
		return false;
	}
	
	@EventHandler
	public void onTransfer(InventoryClickEvent event) {
		if(event.getCurrentItem() == null)
			return;
		
		if(event.getCurrentItem().getType() != Material.PLAYER_HEAD)
			return;
		
		if(!event.getCurrentItem().getItemMeta().getDisplayName().contains("#"))
			return;
		
		Player player = (Player) event.getWhoClicked();
		Shopper shopper = MainShop.getShopperManager().getShopper(player);
		if(shopper == null)
			return;
		
		Cosmetique c = ShopUtils.getCosmetiqueInStock(shopper, event.getCurrentItem());
		if(c == null)
			return;
		
		if(!event.getView().getTitle().equals("Crafting"))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack().getType() != Material.PLAYER_HEAD)
			return;
		
		if(!event.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("#"))
			return;
		
		Player player = event.getPlayer();
		Shopper shopper = MainShop.getShopperManager().getShopper(player);
		if(shopper == null)
			return;
		
		Cosmetique c = ShopUtils.getCosmetiqueInStock(shopper, event.getItemDrop().getItemStack());
		if(c == null)
			return;
		
		player.sendMessage("Vous ne pouvez pas jeter cette tete");
		event.setCancelled(true);
	}
}
