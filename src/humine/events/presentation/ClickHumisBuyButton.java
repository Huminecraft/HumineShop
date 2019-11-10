package humine.events.presentation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import humine.main.MainShop;
import humine.utils.ItemShop;
import humine.utils.Presentation;
import humine.utils.cosmetiques.AbstractCustomHatCosmetique;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.events.ClickItemPresentationEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

/**
 * Package regroupant les evenements du menu de presentation du plugin HumineShop
 * Classe d'evenement permettant d'acheter si possible avec les humis
 * 
 * @author miza
 */
public class ClickHumisBuyButton implements Listener
{
	
	@EventHandler
	public void onClick(ClickItemPresentationEvent event) {
		Cosmetique c = Presentation.getCosmetiques().get(event.getShopper().getPlayer());
		if(c == null)
			return;
		
		if(event.getItem().isSimilar(ItemShop.blockHumisBuy(c, event.getShopper().getPlayer()))) {
			if(!(c instanceof AbstractCustomHatCosmetique) && event.getShopper().getStock().containsCosmetique(c.getId()))
				return;
			
			if(c.getHumisPrice() <= event.getShopper().getHumis().getAmount()) {
				if(c instanceof AbstractCustomHatCosmetique) {
					AbstractCustomHatCosmetique custom = (AbstractCustomHatCosmetique) event.getShopper().getStock().getCosmetique(c.getId());
					if(custom != null)
						custom.setAmount((custom.getAmount() + 1));
					else
						event.getShopper().getCosmetiques().add(custom);
				}
				
				event.getShopper().getCosmetiques().add(c);
				event.getShopper().getHumis().removeAmount(c.getHumisPrice());
				event.getShopper().getPlayer().closeInventory();
				MainShop.sendMessage(event.getShopper().getPlayer(), "Cosmetique achete !");
			}
			else {
				event.getShopper().getPlayer().closeInventory();
				sendLink(event.getShopper().getPlayer());
			}
		}
	}
	
	private void sendLink(Player player) {
		TextComponent text = new TextComponent("Huminecraft");
		text.setColor(ChatColor.GOLD);
		text.setBold(true);
		text.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Clique ici").create()));
		text.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, "https://playecorentin.wixsite.com/huminecraft"));
		
		MainShop.sendMessage(player, ChatColor.YELLOW + "Tu n'as pas assez de Humis. Pour en obtenir clic sur ce lien :");
		player.spigot().sendMessage(text);
	}
}
