package com.humine.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.humine.main.ShopMain;

public class GetGemmeCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(ShopMain.getInstance().getGemmeManager().containsPlayer(player))
			{
				ShopMain.sendMessage(player, "Nombre de gemme: " + ChatColor.GREEN + ShopMain.getInstance().getGemmeManager().getGemme(player).getGemme());
				return true;
			}
			else
				ShopMain.sendMessage(player, "Vous n'avez pas de compte avec des gemmes, veuillez le signaler aux administrateurs");
		}
		
		return false;
	}

}
