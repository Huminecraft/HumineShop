package humine.events;

import java.io.File;

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
import org.bukkit.event.player.PlayerJoinEvent;

import humine.main.MainShop;
import humine.utils.CustomHeadBlockInfo;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Stock;

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
	public void onJoin(PlayerJoinEvent event) {
		if(MainShop.getInstance().getPlayerCustomHeadList().containsKey(event.getPlayer().getName()))
			return;
		
		File file = new File(MainShop.getInstance().getCustomHeadBlockFile(), event.getPlayer().getName() + ".yml");
		CustomHeadBlockInfo chb = CustomHeadBlockInfo.load(file);
		
		if(chb == null)
			MainShop.getInstance().getPlayerCustomHeadList().put(event.getPlayer().getName(), new CustomHeadBlockInfo(event.getPlayer().getName()));
	
	}
	
	@EventHandler
	public void onPose(BlockPlaceEvent event) {
		if(event.getBlock().getType() != Material.PLAYER_HEAD)
			return;
		
		if(!event.getItemInHand().getItemMeta().getDisplayName().contains("#"))
			return;
		
		Player player = event.getPlayer();
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		
		if(stock == null)
			return;
		Cosmetique c = stock.getCosmetique(event.getItemInHand().getItemMeta().getDisplayName().split("#")[1]);
		
		if(c == null)
			return;
		CustomHeadBlockInfo chb = MainShop.getInstance().getPlayerCustomHeadList().get(player.getName());
		
		if(chb == null)
			return;
		
		chb.addBlock(event.getBlock(), event.getItemInHand());
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() != Material.PLAYER_HEAD)
			return;
		
		Player player = event.getPlayer();
		CustomHeadBlockInfo chb = MainShop.getInstance().getPlayerCustomHeadList().get(player.getName());
		
		if(chb == null)
			return;
		
		if(chb.contains(event.getBlock())) {
			
			if(hasFreeSpace(event.getPlayer()))
				event.getPlayer().getInventory().addItem(chb.getItemStack(event.getBlock()));
			else {
				MainShop.sendMessage(player, "Vous n'avez plus de place dans votre inventaire, le cosmetique a etait remis dans votre stock");
			}
			
			chb.removeBlock(event.getBlock());
			event.setCancelled(true);
			event.getBlock().setType(Material.AIR);
			
			return;
		}
		
		if(belongToPlayer(event.getBlock())) {
			player.sendMessage("Vous ne pouvez pas casser cette tete");
			event.setCancelled(true);
			return;
		}
	}
	
	private boolean hasFreeSpace(Player player) {
		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			if(player.getInventory().getContents()[i] == null || player.getInventory().getContents()[i].getType() == Material.AIR) {
				return true;
			}
		}
		return false;
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
		for(CustomHeadBlockInfo chb : MainShop.getInstance().getPlayerCustomHeadList().values()) {
			if(chb.contains(block))
				return true;
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
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		
		if(stock == null)
			return;
		
		Cosmetique c = stock.getCosmetique(event.getCurrentItem().getItemMeta().getDisplayName().split("#")[1]);
		
		if(c == null)
			return;
		
		if(!event.getInventory().getName().equals("Crafting"))
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if(event.getItemDrop().getItemStack().getType() != Material.PLAYER_HEAD)
			return;
		
		if(!event.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("#"))
			return;
		
		Player player = event.getPlayer();
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		
		if(stock == null)
			return;
		
		Cosmetique c = stock.getCosmetique(event.getItemDrop().getItemStack().getItemMeta().getDisplayName().split("#")[1]);
	
		if(c == null)
			return;
		
		player.sendMessage("Vous ne pouvez pas jeter cette tete");
		event.setCancelled(true);
	}
}
