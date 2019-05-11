package humine.events.menuaccueil;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import humine.main.MainShop;
import humine.utils.ItemShop;

/**
 * Package regroupant les evenements du menu d'accueil du plugin HumineShop
 * Classe d'evenement permettant d'envoyer le lien vers le site de humineCraft
 * 
 * @author miza
 */
public class ClickLinkButton implements Listener{

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory().getName().equals(MainShop.getInstance().getMenuAccueil().getName())) {
			if(event.getCurrentItem() != null) {
				if(event.getCurrentItem().isSimilar(ItemShop.itemLink())) {
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
