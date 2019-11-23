package humine.commands;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.main.ShopUtils;
import humine.utils.Prestige;
import humine.utils.cosmetiques.CommandParticle;

public class CreateCommandParticle implements CommandExecutor{

	private static final String COMMAND = "/commandparticle <name> <material> <humis> <pixel> <prestige> <empereur> <cmd>";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 7) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, COMMAND);
			return false;
		}
		
		Material material = ShopUtils.getItem(args[1]);
		if(material == null) {
			MainShop.sendMessage(sender, "Material introuvable");
			MainShop.sendMessage(sender, COMMAND);
			return false;
		}
		
		if(!ShopUtils.isNumber(args[2]) || !ShopUtils.isNumber(args[3])) {
			MainShop.sendMessage(sender, "Prix invalide");
			MainShop.sendMessage(sender, COMMAND);
			return false;
		}
		
		Prestige prestige = ShopUtils.getPrestige(args[4]);
		if(prestige == null) {
			MainShop.sendMessage(sender, "Prestige introuvable");
			MainShop.sendMessage(sender, COMMAND);
			return false;
		}
		
		boolean emperor = false;
		if(args[5].equalsIgnoreCase("true"))
			emperor = true;
		
		String command = args[6];
		for(int i = 7; i < args.length; i++) 
			command += " " + args[i];
		
		Particle particle = ShopUtils.getParticle(command.split(" ")[5].split(":")[1]);
		if(particle == null) {
			MainShop.sendMessage(sender, "Particle introuvable");
			MainShop.sendMessage(sender, COMMAND);
			return false;
		}
		
		int humisPrice = Integer.parseInt(args[2]);
		int pixelPrice = Integer.parseInt(args[3]);
		
		CommandParticle cosmetique = new CommandParticle(args[0].replace("_", " "), new ItemStack(material), humisPrice, pixelPrice, prestige, particle, command);
		
		if(emperor) {
			MainShop.getInstance().getEmperorShop().addCosmetique(cosmetique);
		}
		else {
			MainShop.getInstance().getShop().addCosmetique(cosmetique);
		}
		
		MainShop.sendMessage(sender, "Cosmetique de Particule Command #"+cosmetique.getId()+" cree !");
				
		return true;
	}
}
