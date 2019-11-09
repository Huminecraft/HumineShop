package humine.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import humine.main.MainShop;
import humine.main.ShopUtils;
import humine.utils.Shopper;

/**
 * Package regroupant les commandes du plugin HumineShop
 * Classe de commande permettant ajouter de l'argent a un joueur
 * @author miza
 */
public class AddMoney implements CommandExecutor
{

	private static String command = "/store <humis | pixel> <name> <somme>";
			
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length < 3) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(!args[0].equalsIgnoreCase("humis") && !args[0].equalsIgnoreCase("pixel")) {
			MainShop.sendMessage(sender, "Vous ne pouvez entrer que 'humis' ou 'pixel'");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		Player player = Bukkit.getPlayer(args[1]);
		if(player == null) {
			MainShop.sendMessage(sender, "Joueur inexistant");
			return false;
		}
		
		Shopper shopper = MainShop.getInstance().getShopperBank().getShopper(player);
		if(shopper == null) {
			MainShop.sendMessage(sender, args[1] + " n'a pas de compte Shopper, veuillez contactez l'administration");
			return false;
		}
		
		if(!ShopUtils.isNumber(args[2])) {
			MainShop.sendMessage(sender, "Somme invalide");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("humis")) {
			shopper.getHumis().addAmount(Integer.parseInt(args[2]));
			MainShop.sendMessage(sender, args[2] + " " + shopper.getHumis().getName() + " ajoute au compte de " + args[1]);
		}
		else {
			shopper.getPixel().addAmount(Integer.parseInt(args[2]));
			MainShop.sendMessage(sender, args[2] + " " + shopper.getPixel().getName() + " ajoute au compte de " + args[1]);
		}
		
		return true;
	}
	
}
