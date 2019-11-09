package humine.commands;

import java.io.File;
import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Prestige;
import humine.utils.cosmetiques.TemporaryMaterialHatCosmetique;
import humine.utils.shop.RandomShop;

/**
 * Package regroupant les commandes du plugin HumineShop
 * Classe de commande permettant de creer des cosmetiques temporaire material hat
 * @author miza
 */
public class CreateTemporaryMaterialHatCosmetique implements CommandExecutor
{
	
	private static String command = "/tccmh <name> <material> <humis> <pixel> <materialHat> <date> [prestige]";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		
		if(args.length < 6) {
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
		
		int ordinalMaterialHat = getMaterial(args[4]);
		if(ordinalMaterialHat == -1) {
			MainShop.sendMessage(sender, "Material Hat introuvable");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(!dateValid(args[5])) {
			MainShop.sendMessage(sender, "Date invalide: JJ/MM/AAAA");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		Prestige prestige = Prestige.COMMUN;
		if(args.length >= 7) {
			prestige = getPrestige(args[6]);
		}
		
		Material material = Material.values()[ordinalMaterial];
		int humisPrice = Integer.parseInt(args[2]);
		int pixelPrice = Integer.parseInt(args[3]);
		Material materialHat = Material.values()[ordinalMaterialHat];
		LocalDate date = getDate(args[5]);
		
		TemporaryMaterialHatCosmetique cosmetique = new TemporaryMaterialHatCosmetique(args[0], new ItemStack(material), humisPrice, pixelPrice, date, prestige, materialHat);
		
		
		if(date.isEqual(LocalDate.now())) {
			MainShop.getInstance().getRandomShop().addCosmetique(cosmetique);
		}
		else {
			RandomShop rs = new RandomShop("partiel");
			File file = new File(MainShop.getInstance().getRandomShopFolder(), date.toString());
			rs.load(file);
			rs.addCosmetique(cosmetique);
			rs.save(file);
		}
		
		
		MainShop.sendMessage(sender, "cosmetique de particule créée !");
		MainShop.sendMessage(sender, "nom: " + cosmetique.getName());
		MainShop.sendMessage(sender, "id: #" + cosmetique.getId());
		MainShop.sendMessage(sender, "prix humis: " + cosmetique.getHumisPrice());
		MainShop.sendMessage(sender, "prix pixel: " + cosmetique.getPixelPrice());
		MainShop.sendMessage(sender, "item presentation: " + cosmetique.getItemShop());
		MainShop.sendMessage(sender, "Material Hat: " + cosmetique.getMaterialHat());
		MainShop.sendMessage(sender, "date de presentation: " + args[5]);
		MainShop.sendMessage(sender, "prestige: " + cosmetique.getPrestige().name().toLowerCase());
		
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
	
	// 24/03/2019
	private LocalDate getDate(String date) {
		LocalDate d = LocalDate.now();
		d = d.withYear(Integer.parseInt(date.split("/")[2]));
		d = d.withMonth(Integer.parseInt(date.split("/")[1]));
		d = d.withDayOfMonth(Integer.parseInt(date.split("/")[0]));
		return d;
	}
}
