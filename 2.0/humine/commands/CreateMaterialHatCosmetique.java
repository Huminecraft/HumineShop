package humine.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.cosmetiques.MaterialHatCosmetique;

public class CreateMaterialHatCosmetique implements CommandExecutor
{

	private static String command = "/ccmh <name> <material> <humis> <pixel> <materialHat>";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
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
		
		int ordinalMaterialHat = getMaterial(args[4]);
		if(ordinalMaterialHat == -1) {
			MainShop.sendMessage(sender, "Material Hat introuvable");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		
		Material material = Material.values()[ordinalMaterial];
		int humisPrice = Integer.parseInt(args[2]);
		int pixelPrice = Integer.parseInt(args[3]);
		Material materialHat = Material.values()[ordinalMaterialHat];
		
		MaterialHatCosmetique cosmetique = new MaterialHatCosmetique(args[0], new ItemStack(material), humisPrice, pixelPrice, materialHat);
		
		MainShop.getInstance().getShop().addCosmetique(cosmetique);
		
		MainShop.sendMessage(sender, "Cosmetique de Material Hat cree !");
		MainShop.sendMessage(sender, "nom: " + cosmetique.getName());
		MainShop.sendMessage(sender, "id: #" + cosmetique.getId());
		MainShop.sendMessage(sender, "prix humis: " + cosmetique.getHumisPrice());
		MainShop.sendMessage(sender, "prix pixel: " + cosmetique.getPixelPrice());
		MainShop.sendMessage(sender, "Item presentation: " + cosmetique.getItemShop());
		MainShop.sendMessage(sender, "Material hat: " + cosmetique.getMaterialHat());
		
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
}
