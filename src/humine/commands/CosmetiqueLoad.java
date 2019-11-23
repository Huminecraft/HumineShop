package humine.commands;

import java.io.IOException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.humine.main.ChallengeMain;
import fr.humine.utils.files.ChallengeFile;
import humine.main.MainShop;

public class CosmetiqueLoad implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			ChallengeMain.sendMessage(sender, "Vous devez etre un joueur");
			return false;
		}
		
		Player player = (Player) sender;
		
		try
		{
			List<String> commands = ChallengeFile.loadCommandFile(MainShop.getInstance().getCosmetiqueFile());
			MainShop.getInstance().getShop().resetShop();
			MainShop.getInstance().getEmperorShop().resetShop();
			for(String str : commands) {
				ChallengeMain.getInstance().getServer().dispatchCommand(player, str);
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
