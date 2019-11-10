package humine.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import humine.main.MainShop;
import humine.utils.Shopper;

/**
 * Package regroupant les commandes du plugin HumineShop
 * Classe de commande permettant voir son argent et celui de quelqu'un d'autre
 * (via une permission)
 * @author miza
 */
public class ShowMoney implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(!(sender instanceof Player)) {
			MainShop.sendMessage(sender, "Vous devez etre un joueur");
			return false;
		}
		
		Player target = null;
		if(args.length >= 1)
			target = Bukkit.getPlayer(args[0]);
		
		if(args.length >= 1 && target == null) {
			MainShop.sendMessage(sender, "Joueur instrouvable");
			return false;
		}
		else {
			target = (Player) sender;
		}
		
		Shopper shopper = MainShop.getShopperManager().getShopper(target);
		if(shopper == null) {
			MainShop.sendMessage(sender, args[1] + " n'a pas de compte Shopper, veuillez contactez l'administration");
			return false;
		}
		
		MainShop.sendMessage(sender, target.getName() + " : " + shopper.getHumis().getAmount() + " " + shopper.getHumis().getName() + " / " + shopper.getPixel().getAmount() + " " + shopper.getPixel().getName());

		return true;
	}
}
