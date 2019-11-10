package humine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import humine.main.MainShop;
import humine.utils.menus.MenuAccueil;

/**
 * Package regroupant les commandes du plugin HumineShop
 * Classe de commande permettant d'ouvrir le menu d'accueil de HumineShop
 * 
 * Il existe:
 * Le menu d'accueil (menu de depart)
 * Le menu intermediaire (menu quand on clique sur la partie boutique)
 * le randomShop (les particules temporaires)
 * les sous-stocks (particles, material hats, custom heads)
 * quitter (bouton pour quitter)
 * 
 * @author miza
 */
public class OpenShopCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		
		if(!(sender instanceof Player)) {
			MainShop.sendMessage(sender, "Vous devez etre un joueur");
			return false;
		}
		
		Player player = (Player) sender;
		MenuAccueil.openMenu(player);
		return true;
	}
}
