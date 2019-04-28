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
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;

public class ClickHumisBuyButton implements Listener
{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null) {
				Player player = (Player) event.getWhoClicked();
				Cosmetique c = Presentation.getCosmetiques().get(player);
				
				if(c != null) {
					if(event.getCurrentItem().isSimilar(ItemShop.blockHumisBuy(c, player))) {
						Stock stock = MainShop.getInstance().getInventories().getStockOfPlayer(player.getName());
						int humis = MainShop.getInstance().getBankHumis().getMoney(player);
						
						if(stock.getCosmetique(c.getId()) != null)
							return;
						
						if(c.getHumisPrice() <= humis) {
							stock.addCosmetique(c);
							MainShop.getInstance().getBankHumis().removeMoney(player, c.getHumisPrice());
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
		TextComponent text = new TextComponent("Huminecraft");
		text.setColor(ChatColor.GOLD);
		text.setBold(true);
		text.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Clique ici").create()));
		text.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, "https://playecorentin.wixsite.com/huminecraft"));
		
		MainShop.sendMessage(player, ChatColor.YELLOW + "Tu n’as pas assez de Humis. Pour en obtenir clic sur ce lien :");
		player.spigot().sendMessage(text);
	}
}
