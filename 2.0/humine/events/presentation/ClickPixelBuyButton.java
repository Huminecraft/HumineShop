package humine.events.presentation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.shop.Stock;

public class ClickPixelBuyButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null) {
				Player player = (Player) event.getWhoClicked();
				Cosmetique c = Presentation.getCosmetiques().get(player);
				
				if(c != null) {
					if(event.getCurrentItem().isSimilar(ItemShop.blockPixelBuy(c, player))) {
						Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
						int pixel = MainShop.getInstance().getBankPixel().getMoney(player);
						
						if(stock.getCosmetique(c.getId()) != null)
							return;
						
						if(c.getPixelPrice() <= pixel) {
							stock.addCosmetique(c);
							MainShop.getInstance().getBankPixel().removeMoney(player, c.getHumisPrice());
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
		MainShop.sendMessage(player, "t'es fauche mon vieu (pixel)");
	}
}
