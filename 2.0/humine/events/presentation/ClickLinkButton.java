package humine.events.presentation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.utils.Presentation;

public class ClickLinkButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().startsWith(Presentation.getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(Presentation.itemApercu())) {
					Player player = (Player) event.getWhoClicked();
					sendLink(player);
					player.closeInventory();
				}
			}
		}
	}
	
	private void sendLink(Player player) {
		//TODO envoyer lien cliquable au joueur
	}
}
