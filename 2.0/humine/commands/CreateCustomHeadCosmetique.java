package humine.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Prestige;
import humine.utils.cosmetiques.CustomHeadCosmetique;

public class CreateCustomHeadCosmetique implements CommandExecutor{

	private static String command = "/ccch <name> <libelle> <custom head> <humis> <pixel> [prestige] [empereur]";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			MainShop.sendMessage(sender, "Vous devez etre un joueur");
			return false;
		}
		
		Player player = (Player) sender;
		
		if(!hasAFreeSlot(player)) {
			MainShop.sendMessage(sender, "Vous n'avez aucune place libre dans votre inventaire");
			return false;
		}
		
		if(args.length < 5) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(!verification(args[2])) {
			MainShop.sendMessage(sender, "Custom head invalide");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(!isNumber(args[3]) || !isNumber(args[4])) {
			MainShop.sendMessage(sender, "Prix invalide");
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
		
		ItemStack item = getItemStack(player, args[1]);
		if(item == null) {
			MainShop.sendMessage(sender, "Erreur: l'item n'a pas pu etre recupere");
			return false;
		}
		
		int humisPrice = Integer.parseInt(args[2]);
		int pixelPrice = Integer.parseInt(args[3]);
		
		CustomHeadCosmetique cosmetique = new CustomHeadCosmetique(args[0], item, humisPrice, pixelPrice, prestige, args[1]);
				
		if(emperor) {
			MainShop.getInstance().getEmperorShop().addCosmetique(cosmetique);
		}
		else {
			MainShop.getInstance().getShop().addCosmetique(cosmetique);
			//TODO ajouter aussi a la customHead shop
			//MainShop.getInstance().getParticleShop().addCosmetique(cosmetique);
		}
		
		MainShop.sendMessage(sender, "Cosmetique de Custom Head cree !");
		MainShop.sendMessage(sender, "nom: " + cosmetique.getName());
		MainShop.sendMessage(sender, "id: #" + cosmetique.getId());
		MainShop.sendMessage(sender, "prix humis: " + cosmetique.getHumisPrice());
		MainShop.sendMessage(sender, "prix pixel: " + cosmetique.getPixelPrice());
		MainShop.sendMessage(sender, "Custom Head: " + cosmetique.getLibelle());
		MainShop.sendMessage(sender, "prestige: " + cosmetique.getPrestige().name().toLowerCase());
		
		return true;
	}
	
	private boolean hasAFreeSlot(Player player) {
		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			if(player.getInventory().getContents()[i] == null || player.getInventory().getContents()[i].getType() == Material.AIR)
				return true;
		}
		
		return false;
	}

	private ItemStack getItemStack(Player player, String head) {
		MainShop.getInstance().getServer().dispatchCommand(MainShop.getInstance().getServer().getConsoleSender(), "give " + player.getName() + " " + head + " 1");
		String name = head.split("\\")[3].substring(1);
		ItemStack item = null;

		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			if(player.getInventory().getContents()[i] != null && player.getInventory().getContents()[i].getType() != Material.AIR) {
				if(player.getInventory().getContents()[i].getItemMeta().getDisplayName().contains(name)) {
					item = player.getInventory().getContents()[i];
					player.getInventory().getContents()[i] = null;
					break;
				}
			}
		}
		
		return item;
	}

	private boolean verification(String head) {
		if(!head.toLowerCase().contains("minecraft:player_head"))
			return false;
		
		if(!head.toLowerCase().contains("skullowner:"))
			return false;
		
		if(!head.toLowerCase().contains("name:"))
			return false;
		
		return true;
	}

	private boolean isNumber(String number) {
		for(int i = 0; i < number.length(); i++) {
			if(number.charAt(i) < '0' || number.charAt(i) > '9') {
				return false;
			}
		}
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
}
