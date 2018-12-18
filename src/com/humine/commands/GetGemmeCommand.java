package com.humine.commands;

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
				ShopMain.sendMessage(player, "Nombre de gemme: " + ShopMain.getInstance().getGemmeManager().getGemme(player));
				return true;
			}
		}
		
		return false;
	}

}
