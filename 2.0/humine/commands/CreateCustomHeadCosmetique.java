package humine.commands;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Prestige;
import humine.utils.cosmetiques.CustomHeadCosmetique;

/**
 * Package regroupant les commandes du plugin HumineShop
 * Classe de commande permettant de creer des cosmetiques custom head
 * @author miza
 */
public class CreateCustomHeadCosmetique implements CommandExecutor{

	private static String command = "/ccch <name> <libelle> <custom head> <humis> <pixel> [prestige] [empereur]";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof BlockCommandSender)) {
			MainShop.sendMessage(sender, "Vous devez utiliser un command block");
			return false;
		}
		
		BlockCommandSender command_block = (BlockCommandSender) sender;
		Collection<Entity> entities = command_block.getBlock().getWorld().getNearbyEntities(command_block.getBlock().getLocation(), 2.0, 2.0, 2.0);
		Player player = null;
		
		for(Entity e : entities) {
			if(e instanceof Player)
				player = (Player) e;
		}
		
		if(player == null) {
			MainShop.sendMessage(sender, "joueur non trouve");
			return false;
		}
		
		if(!hasAFreeSlot(player)) {
			MainShop.sendMessage(player, "Vous n'avez aucune place libre dans votre inventaire");
			return false;
		}
		
		if(args.length < 5) {
			MainShop.sendMessage(player, "Argument insuffisant");
			MainShop.sendMessage(player, command);
			return false;
		}
		
		if(!verification(args[2])) {
			MainShop.sendMessage(player, "Custom head invalide");
			MainShop.sendMessage(player, command);
			return false;
		}
		
		if(!isNumber(args[3]) || !isNumber(args[4])) {
			MainShop.sendMessage(player, "Prix invalide");
			MainShop.sendMessage(player, command);
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
		
		ItemStack item = getItemStack(player, args[2]);
		if(item == null) {
			MainShop.sendMessage(player, "Erreur: l'item n'a pas pu etre recupere");
			return false;
		}
		
		int humisPrice = Integer.parseInt(args[3]);
		int pixelPrice = Integer.parseInt(args[4]);
		
		CustomHeadCosmetique cosmetique = new CustomHeadCosmetique(args[0], item, humisPrice, pixelPrice, prestige, args[1]);

		if(emperor) {
			MainShop.getInstance().getEmperorShop().addCosmetique(cosmetique);
		}
		else {
			MainShop.getInstance().getShop().addCosmetique(cosmetique);
			MainShop.getInstance().getCustomHeadShop().addCosmetique(cosmetique);
		}
		MainShop.sendMessage(player, "Cosmetique de Custom Head cree !");
		MainShop.sendMessage(player, "nom: " + cosmetique.getName());
		MainShop.sendMessage(player, "id: #" + cosmetique.getId());
		MainShop.sendMessage(player, "prix humis: " + cosmetique.getHumisPrice());
		MainShop.sendMessage(player, "prix pixel: " + cosmetique.getPixelPrice());
		MainShop.sendMessage(player, "Custom Head: " + cosmetique.getLibelle());
		MainShop.sendMessage(player, "prestige: " + cosmetique.getPrestige().name().toLowerCase());
		
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
		String command = "give " + player.getName() + " " + head + " 1";
		String name = head.split("\"")[4].substring(0, head.split("\"")[4].length() - 1);
		
		MainShop.getInstance().getServer().dispatchCommand(MainShop.getInstance().getServer().getConsoleSender(), command);
		
		ItemStack item = null;

		for(int i = 0; i < player.getInventory().getContents().length; i++) {
			if(player.getInventory().getContents()[i] != null && player.getInventory().getContents()[i].getType() != Material.AIR) {
				if(player.getInventory().getContents()[i].getItemMeta().getDisplayName().toLowerCase().equals(name.toLowerCase())) {
					item = player.getInventory().getContents()[i];
					player.getInventory().getContents()[i] = null;
					player.getInventory().setItem(i, null);
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
		
		if(head.split("\"")[4].substring(0, head.split("\"")[4].length() - 1).equals(""))
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
