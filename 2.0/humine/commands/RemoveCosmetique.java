package humine.commands;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import humine.main.MainShop;
import humine.utils.Page;

public class RemoveCosmetique implements CommandExecutor{

	private static String command = "/rc <id>";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length != 1) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, command);
		}
		
		for(Page page : MainShop.getInstance().getShop().getPages()) {
			for(int i = 0; i < page.getCosmetiques().length; i++) {
				if(page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(args[0])) {
					page.getCosmetiques()[i] = null;
					MainShop.sendMessage(sender, "Cosmetique #" + args[0] + " supprimé !");
					return true;
				}
			}
		}
		
		Page page = new Page("", 0);
		for(File dateFolder : MainShop.getInstance().getRandomShopFolder().listFiles()) {
			for(File pageFolder : dateFolder.listFiles()) {
				page.load(pageFolder);
				for(int i = 0; i < page.getCosmetiques().length; i++) {
					if(page.getCosmetiques()[i] != null && page.getCosmetiques()[i].getId().equals(args[0])) {
						page.getCosmetiques()[i] = null;
						MainShop.sendMessage(sender, "Cosmetique #" + args[0] + " supprimé !");
						page.save(pageFolder);
						return true;
					}
				}
			}
		}
		
		MainShop.sendMessage(sender, "Cosmetique introuvable");
		return false;
	}
}
