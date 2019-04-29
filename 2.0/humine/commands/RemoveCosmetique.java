package humine.commands;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import humine.main.MainShop;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.MaterialHatCosmetique;
import humine.utils.cosmetiques.ParticleCosmetique;
import humine.utils.shop.Page;

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
					Cosmetique c = page.getCosmetiques()[i];
					page.getCosmetiques()[i] = null;
					
					if(c instanceof ParticleCosmetique)
						MainShop.getInstance().getParticleShop().filter(MainShop.getInstance().getShop());
					else if(c instanceof MaterialHatCosmetique)
						MainShop.getInstance().getHatShop().filter(MainShop.getInstance().getShop());
					else if(c instanceof CustomHeadCosmetique)
						MainShop.getInstance().getCustomHeadShop().filter(MainShop.getInstance().getShop());
					
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
