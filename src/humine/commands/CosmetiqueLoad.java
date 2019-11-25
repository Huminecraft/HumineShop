package humine.commands;

import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import humine.main.MainShop;
import humine.utils.files.CommandFile;

public class CosmetiqueLoad implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			MainShop.sendMessage(sender, "Vous devez etre un joueur");
			return false;
		}
		
		Player player = (Player) sender;
		
		try
		{
			List<String> commands = CommandFile.loadCommandFile(MainShop.getInstance().getCosmetiqueFile());
			MainShop.getInstance().getShop().resetShop();
			MainShop.getInstance().getEmperorShop().resetShop();
			for(String str : commands) {
				MainShop.getInstance().getServer().dispatchCommand(player, str);
			}
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		return false;
	}
}
