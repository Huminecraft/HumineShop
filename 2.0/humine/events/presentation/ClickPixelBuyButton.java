package humine.events.presentation;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;
import humine.utils.shop.Stock;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant d'acheter si possible avec les pixels
 * 
 * @author miza
 */
public class ClickPixelBuyButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				Player player = (Player) event.getWhoClicked();
				Cosmetique c = Presentation.getCosmetiques().get(player);
				
				if(c != null) {
					if(event.getCurrentItem().isSimilar(ItemShop.blockPixelBuy(c, player))) {
						Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
						int pixel = MainShop.getInstance().getBankPixel().getMoney(player);
						
						if(!(c instanceof CustomHeadCosmetique) && !(c instanceof TemporaryCustomHeadCosmetique)) {
							if(stock.getCosmetique(c.getId()) != null)
								return;
						}
						
						if(c.getPixelPrice() <= pixel) {
							if(c instanceof CustomHeadCosmetique) {
								Cosmetique custom = stock.getCosmetique(c.getId());
								if(custom != null) {
									((CustomHeadCosmetique) custom).setAmount(((CustomHeadCosmetique) custom).getAmount() + 1);
								}
								else
									stock.addCosmetique(c);
							}
							else if(c instanceof TemporaryCustomHeadCosmetique) {
								Cosmetique custom = stock.getCosmetique(c.getId());
								if(custom != null) {
									((TemporaryCustomHeadCosmetique) custom).setAmount(((TemporaryCustomHeadCosmetique) custom).getAmount() + 1);
								}
								else
									stock.addCosmetique(c);
							}
							else {
								stock.addCosmetique(c);
							}
							
							MainShop.getInstance().getBankPixel().removeMoney(player, c.getPixelPrice());
							player.closeInventory();
							MainShop.sendMessage(player, "Cosmetique achete !");
						}
						else {
							player.closeInventory();
							sendLink(player);
						}
					}
				}
			}
		}
	}
	
	private void sendLink(Player player) {
		MainShop.sendMessage(player, ChatColor.LIGHT_PURPLE + "Tu n�as pas assez de pixels. Pour en obtenir tu dois compl�ter les d�fis quotidiens et hebdomadaires. /showmissions");
	}
}
