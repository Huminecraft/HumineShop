package humine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import humine.main.MainShop;
import humine.utils.shop.Page;

/**
 * Package regroupant les commandes du plugin HumineShop
 * Classe de commande permettant de supprimer un cosmetique
 * grace a son <b>id</b> (numero unique de chaque cosmetique)
 * 
 * @author miza
 */
public class RemoveCosmetique implements CommandExecutor{

	private static String command = "/rc <id>";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length != 1) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		for(Page page : MainShop.getInstance().getShop().getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(args[0])) {
					page.getCosmetiques()[i] = null;
					MainShop.sendMessage(sender, "Cosmetique #" + args[0] + " supprime !");
					return true;
				}
			}
		}
		
		for(Page page : MainShop.getInstance().getRandomShop().getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(args[0])) {
					page.getCosmetiques()[i] = null;
					MainShop.sendMessage(sender, "Cosmetique #" + args[0] + " supprime !");
					return true;
				}
			}
		}
		
		for(Page page : MainShop.getInstance().getEmperorShop().getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(args[0])) {
					page.getCosmetiques()[i] = null;
					MainShop.sendMessage(sender, "Cosmetique #" + args[0] + " supprime !");
					return true;
				}
			}
		}
		
		MainShop.sendMessage(sender, "Cosmetique introuvable");
		return false;
	}
}
