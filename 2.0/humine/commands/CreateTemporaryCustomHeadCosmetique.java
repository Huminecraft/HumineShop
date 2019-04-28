package humine.commands;

import java.io.File;
import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Prestige;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;
import humine.utils.shop.RandomShop;

public class CreateTemporaryCustomHeadCosmetique implements CommandExecutor{

	private static String command = "/ccch <name> <libelle> <custom head> <humis> <pixel> <date> [prestige]";
	
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
		
		if(args.length < 6) {
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
		
		if(!dateValid(args[5])) {
			MainShop.sendMessage(sender, "Date invalide: JJ/MM/AAAA");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		Prestige prestige = Prestige.COMMUN;
		if(args.length >= 7) {
			prestige = getPrestige(args[6]);
		}
		
		ItemStack item = getItemStack(player, args[1]);
		if(item == null) {
			MainShop.sendMessage(sender, "Erreur: l'item n'a pas pu etre recupere");
			return false;
		}
		
		int humisPrice = Integer.parseInt(args[2]);
		int pixelPrice = Integer.parseInt(args[3]);
		LocalDate date = getDate(args[5]);
		
		TemporaryCustomHeadCosmetique cosmetique = new TemporaryCustomHeadCosmetique(args[0], item, humisPrice, pixelPrice, date, prestige, args[1]);
				
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
		MainShop.sendMessage(sender, "custom head: " + cosmetique.getLibelle());
		MainShop.sendMessage(sender, "date de presentation: " + args[5]);
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
