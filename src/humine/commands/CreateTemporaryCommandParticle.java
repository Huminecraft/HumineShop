package humine.commands;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.main.ShopUtils;
import humine.utils.Prestige;
import humine.utils.cosmetiques.TemporaryCommandParticle;
import humine.utils.files.ShopLoader;
import humine.utils.files.ShopSaver;
import humine.utils.shop.RandomShop;

public class CreateTemporaryCommandParticle implements CommandExecutor{

	private static final String COMMAND = "/tcommandparticle <name> <material> <humis> <pixel> <prestige> <date> <cmd>";
	
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
		
		LocalDate date = null;
		if(ShopUtils.dateValid(args[5]))
			date = ShopUtils.getDate(args[5]);
		
		if(date == null) {
			MainShop.sendMessage(sender, "Date " + args[5] + "Invalide");
			MainShop.sendMessage(sender, "Date : JJ/MM/AAAA");
			return false;
		}
		
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
		
		TemporaryCommandParticle cosmetique = new TemporaryCommandParticle(args[0].replace("_", " "), new ItemStack(material), humisPrice, pixelPrice, prestige, particle, command, date);
		
		if(date.isEqual(LocalDate.now())) {
			MainShop.getInstance().getRandomShop().addCosmetique(cosmetique);
		}
		else {
			try {
				RandomShop rs = ShopLoader.loadRandomShop(date);
				rs.addCosmetique(cosmetique);
				ShopSaver.saveRandomShop(rs, date);
			} catch (ClassNotFoundException | IOException e1) {
				MainShop.getInstance().getServer().getLogger().log(Level.SEVERE, "Random Shop: sauvegarde impossible");
				e1.printStackTrace();
			}
		}
		
		MainShop.sendMessage(sender, "Cosmetique de Particule Command #"+cosmetique.getId()+" cree !");
				
		return true;
	}
}
