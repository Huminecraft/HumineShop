package humine.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import humine.main.MainShop;

public class Money implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(args.length >= 1) {
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target != null) {
				if(MainShop.getInstance().getBankHumis().containsPlayer(target) && MainShop.getInstance().getBankPixel().containsPlayer(target)) {
					int moneyHumis = MainShop.getInstance().getBankHumis().getMoney(target);
					int moneyPixel = MainShop.getInstance().getBankPixel().getMoney(target);
					MainShop.sendMessage(sender, target.getName() + ": " + moneyHumis + " " + MainShop.getInstance().getBankHumis().getNameValue());
					MainShop.sendMessage(sender, target.getName() + ": " + moneyPixel + " " + MainShop.getInstance().getBankPixel().getNameValue());
					return true;
				}
				else
					MainShop.sendMessage(sender, "Ce joueur n'a pas de compte bancaire");
			}
			else
				MainShop.sendMessage(sender, "Joueur introuvable");
		}
		else {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				if(MainShop.getInstance().getBankHumis().containsPlayer(player)) {
					int money = MainShop.getInstance().getBankHumis().getMoney(player);
					MainShop.sendMessage(sender, player.getName() + ": " + money + " " + MainShop.getInstance().getBankHumis().getNameValue());
					return true;
				}
				else
					MainShop.sendMessage(sender, "vous n'avez pas de compte bancaire");
			}
			else
				MainShop.sendMessage(sender, "Vous devez etre un joueur");
		}
		
		return false;
	}
}
