package humine.commands;



import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Prestige;
import humine.utils.cosmetiques.ParticleCosmetique;

/**
 * Package regroupant les commandes du plugin HumineShop
 * Classe de commande permettant de creer des cosmetiques particle
 * @author miza
 */
public class CreateParticleCosmetique implements CommandExecutor{

	private static String command = "/ccp <name> <material> <humis> <pixel> <particle> [prestige] [empereur]";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 5) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		int ordinalMaterial = getMaterial(args[1]);
		if(ordinalMaterial == -1) {
			MainShop.sendMessage(sender, "Material introuvable");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(!isNumber(args[2]) || !isNumber(args[3])) {
			MainShop.sendMessage(sender, "Prix invalide");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		int ordinalParticle = getParticle(args[4]);
		if(ordinalParticle == -1) {
			MainShop.sendMessage(sender, "Particle introuvable");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		Prestige prestige = Prestige.COMMUN;
		if(args.length >= 6) {
			prestige = getPrestige(args[5]);
		}
		
		boolean emperor = false;
		if(args.length >= 7) {
			if(args[6].equalsIgnoreCase("true"))
				emperor = true;
		}
		
		
		Material material = Material.values()[ordinalMaterial];
		int humisPrice = Integer.parseInt(args[2]);
		int pixelPrice = Integer.parseInt(args[3]);
		Particle particle = Particle.values()[ordinalParticle];
		
		ParticleCosmetique cosmetique = new ParticleCosmetique(args[0].replace("_", " "), new ItemStack(material), humisPrice, pixelPrice, particle, prestige);
		
		if(emperor) {
			MainShop.getInstance().getEmperorShop().addCosmetique(cosmetique);
		}
		else {
			MainShop.getInstance().getShop().addCosmetique(cosmetique);
		}
		
		MainShop.sendMessage(sender, "Cosmetique de Particule #"+cosmetique.getId()+" cree !");
		
		return true;
	}
	
	private Prestige getPrestige(String particle)
	{
		for(Prestige p : Prestige.values()) {
			if(p.name().equalsIgnoreCase(particle))
				return p;
		}
		
		return Prestige.COMMUN;
	}
	
	private int getMaterial(String material) {
		for(int i = 0; i < Material.values().length; i++) {
			if(Material.values()[i].name().equalsIgnoreCase(material)) {
				return i;
			}
		}
		return -1;
	}
	
	private boolean isNumber(String number) {
		for(int i = 0; i < number.length(); i++) {
			if(number.charAt(i) < '0' || number.charAt(i) > '9') {
				return false;
			}
		}
		return true;
	}
	
	private int getParticle(String particle) {
		for(int i = 0; i < Particle.values().length; i++) {
			if(Particle.values()[i].name().equalsIgnoreCase(particle)) {
				return i;
			}
		}
		return -1;
	}
	
}
