package humine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import humine.main.MainShop;

public class RemoveMoney implements CommandExecutor
{

	private static String command = "/storedelete <humis | pixel> <name> <somme>";
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if(args.length < 3) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(!args[0].equalsIgnoreCase("humis") && !args[0].equalsIgnoreCase("pixel")) {
			MainShop.sendMessage(sender, "Vous ne pouvez entrer que 'humis' ou 'pixel'");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("humis") && !MainShop.getInstance().getBankHumis().containsPlayer(args[1])) {
			MainShop.sendMessage(sender, "Compte bancaire Humis du joueur inexistant");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("pixel") && !MainShop.getInstance().getBankPixel().containsPlayer(args[1])) {
			MainShop.sendMessage(sender, "Compte bancaire Pixel du joueur inexistant");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(!isNumber(args[2])) {
			MainShop.sendMessage(sender, "Somme invalide");
			MainShop.sendMessage(sender, command);
			return false;
		}
		
		if(args[0].equalsIgnoreCase("humis")) {
			MainShop.getInstance().getBankHumis().removeMoney(args[1], Integer.parseInt(args[2]));
			MainShop.sendMessage(sender, args[2] + " " + MainShop.getInstance().getBankHumis().getNameValue() + " supprimés du compte de " + args[1]);
		}
		else {
			MainShop.getInstance().getBankPixel().removeMoney(args[1], Integer.parseInt(args[2]));
			MainShop.sendMessage(sender, args[2] + " " + MainShop.getInstance().getBankPixel().getNameValue() + " supprimés du compte de " + args[1]);
		}
		
		
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
}
