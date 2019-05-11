package humine.events.presentation;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.CustomHeadBlockInfo;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;
import humine.utils.shop.Stock;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant ajouter une cosmetique custom head dans
 * l'inventaire du joueur si il lui reste des custom heads en stock
 * 
 * @author miza
 */
public class ClickPlusButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				if(event.getCurrentItem().getItemMeta().getDisplayName().equals(ItemShop.itemPlus().getItemMeta().getDisplayName())) {
					giveToPlayer((Player) event.getWhoClicked());
				}
			}
		}
	}

	private void giveToPlayer(Player player) {
		Cosmetique c = Presentation.getCosmetiques().get(player);
		int amountInStock, amountOutStock;
		if(c == null || (!(c instanceof CustomHeadCosmetique) && !(c instanceof TemporaryCustomHeadCosmetique)))
			return;
		
		
		if(c instanceof CustomHeadCosmetique)
			amountInStock = ((CustomHeadCosmetique) c).getAmount();
		else
			amountInStock = ((TemporaryCustomHeadCosmetique) c).getAmount();
		
		amountOutStock = getAmountExternToStock(player);
		if(amountOutStock == -1)
			return;
		
		if(amountOutStock < amountInStock) {
			player.getInventory().addItem(Cosmetique.cosmetiqueToItem(c));
		}
		else {
			MainShop.sendMessage(player, "Vous ne pouvez pas prendre plus de tete");
		}
	}
	
	public int getAmountExternToStock(Player player) {
		CustomHeadBlockInfo chb = MainShop.getInstance().getPlayerCustomHeadList().get(player.getName());
		Cosmetique c = Presentation.getCosmetiques().get(player);
		Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
		int somme = 0;
		
		if(chb == null || c == null || stock == null)
			return -1;
		
		somme += chb.getBlocks().size();
		
		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			ItemStack item = player.getInventory().getContents()[i];
			if(item == null)
				continue;
			if(!item.getItemMeta().getDisplayName().contains("#"))
				continue;
			
			Cosmetique cosmetique = stock.getCosmetique(item.getItemMeta().getDisplayName().split("#")[1]);
			if(cosmetique == null)
				continue;
			
			if(c.equals(cosmetique))
				somme += item.getAmount();
			
		}
		
		return somme;
	}
}
