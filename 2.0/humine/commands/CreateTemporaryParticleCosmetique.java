package humine.commands;

import java.io.File;
import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import humine.main.MainShop;
import humine.utils.Prestige;
import humine.utils.Utils;
import humine.utils.randomshop.RandomShop;
import humine.utils.randomshop.TemporaryParticleCosmetique;

public class CreateTemporaryParticleCosmetique implements CommandExecutor {

	private static String command = "/tccp <name> <material> <humis> <pixel> <particle> <date> <prestige>";
	
	// /tccp <name> <material> <humis> <pixel> <particle> <date> <prestige> 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length < 7) {
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
		
		if(!dateValid(args[5])) {
			MainShop.sendMessage(sender, "Date invalide: JJ/MM/AAAA");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		int ordinalPrestige = getPrestige(args[6]);
		if(ordinalPrestige == -1) {
			MainShop.sendMessage(sender, "Prestige introuvable");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		Material material = Material.values()[ordinalMaterial];
		int humisPrice = Integer.parseInt(args[2]);
		int pixelPrice = Integer.parseInt(args[3]);
		Particle particle = Particle.values()[ordinalParticle];
		LocalDate date = getDate(args[4]);
		Prestige prestige = Prestige.values()[ordinalPrestige];
		
		TemporaryParticleCosmetique cosmetique = new TemporaryParticleCosmetique(args[0], material, humisPrice, pixelPrice, date, prestige, particle);
		
		
		if(date.isEqual(LocalDate.now())) {
			Utils.addCosmetique(MainShop.getInstance().getRandomShop(), cosmetique);
		}
		else {
			RandomShop rs = new RandomShop("partiel");
			File file = new File(MainShop.getInstance().getRandomShopFolder(), date  + ".yml");
			rs.load(file);
			Utils.addCosmetique(rs, cosmetique);
			rs.save(file);
			
		}
		
		MainShop.sendMessage(sender, "cosmetique de particule créée !");
		MainShop.sendMessage(sender, "nom: " + cosmetique.getName());
		MainShop.sendMessage(sender, "id: #" + cosmetique.getId());
		MainShop.sendMessage(sender, "prix humis: " + cosmetique.getHumisPrice());
		MainShop.sendMessage(sender, "prix pixel: " + cosmetique.getPixelPrice());
		MainShop.sendMessage(sender, "item presentation: " + cosmetique.getItemShop());
		MainShop.sendMessage(sender, "effet particule: " + cosmetique.getParticle());
		MainShop.sendMessage(sender, "date de presentation: " + args[4]);
		MainShop.sendMessage(sender, "prestige: " + cosmetique.getPrestige().toString());
		
		return true;
	}
	
	private int getPrestige(String prestige) {
		for(int i = 0; i < Prestige.values().length; i++) {
			if(Prestige.values()[i].name().equalsIgnoreCase(prestige)) {
				return i;
			}
		}
		return -1;
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
	
	// date valide: 01/02/2019
		private boolean dateValid(String date) {
			if(date.length() != 10)
				return false;
			
			String[] str = date.split("/");
			
			if(str.length != 3)
				return false;

			if(!isNumber(str[0]) || !isNumber(str[1]) || !isNumber(str[2]))
				return false;
			
			if(str[0].length() != 2 || str[1].length() != 2 || str[2].length() != 4)
				return false;
			
			return true;
		}
		
		private LocalDate getDate(String date) {
			LocalDate d = LocalDate.now();
			d = d.withYear(Integer.parseInt(date.split("/")[2]));
			d = d.withMonth(Integer.parseInt(date.split("/")[1]));
			d = d.withDayOfMonth(Integer.parseInt(date.split("/")[0]));
			return d;
		}
}
