package humine.commands;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import humine.main.MainShop;
import humine.utils.ParticleCosmetique;
import humine.utils.Utils;

public class CreateParticleCosmetique implements CommandExecutor{

	// /ccp <name> <material> <price> <particle>
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 3) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, "/ccp <name> <material> <price> <particle>");
			return false;
		}
		
		int ordinalMaterial = getMaterial(args[1]);
		if(ordinalMaterial == -1) {
			MainShop.sendMessage(sender, "Material introuvable");
			MainShop.sendMessage(sender, "/ccp <name> <material> <price> <particle>");
			return false;
		}
		
		if(!isNumber(args[2])) {
			MainShop.sendMessage(sender, "Prix invalide");
			MainShop.sendMessage(sender, "/ccp <name> <material> <price> <particle>");
			return false;
		}
		
		int ordinalParticle = getParticle(args[3]);
		if(ordinalParticle == -1) {
			MainShop.sendMessage(sender, "Particle introuvable");
			MainShop.sendMessage(sender, "/ccp <name> <material> <price> <particle>");
			return false;
		}
		
		Material material = Material.values()[ordinalMaterial];
		int price = Integer.parseInt(args[2]);
		Particle particle = Particle.values()[ordinalParticle];
		
		ParticleCosmetique cosmetique = new ParticleCosmetique(args[0], material, price, particle);
		Utils.addCosmetique(MainShop.getInstance().getShop(), cosmetique);
		
		MainShop.sendMessage(sender, "Cosmetique de Particule créée !");
		MainShop.sendMessage(sender, "nom: " + cosmetique.getName());
		MainShop.sendMessage(sender, "id: #" + cosmetique.getId());
		MainShop.sendMessage(sender, "prix: " + cosmetique.getPrice());
		MainShop.sendMessage(sender, "Item presentation: " + cosmetique.getItemShop());
		MainShop.sendMessage(sender, "effet particule: " + cosmetique.getParticleEffect());
		
		return true;
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
