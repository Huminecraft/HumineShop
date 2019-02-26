package humine.commands;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import humine.main.MainShop;

public class HelpList implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if(args.length < 1) {
			MainShop.sendMessage(sender, "Argument insuffisant");
			MainShop.sendMessage(sender, "/chelp <material | particle>");
			return false;
		}
		
		if(args[0].equalsIgnoreCase("material")) {
			showMaterial(sender);
			return true;
		}
		else if(args[0].equalsIgnoreCase("particle")) {
			showParticle(sender);
			return true;
		}
		
		return false;
	}
	
	private void showParticle(CommandSender sender)
	{
		String str = "";
		for(int i = 0; i < Particle.values().length; i++) {
			str += Particle.values()[i].name() + ", ";
		}
		sender.sendMessage(str);
	}

	private void showMaterial(CommandSender sender) {
		String str = "";
		for(int i = 0; i < Material.values().length; i++) {
			str += Material.values()[i].name() + ", ";
		}
		sender.sendMessage(str);
	}
}
