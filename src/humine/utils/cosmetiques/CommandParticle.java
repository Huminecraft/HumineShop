package humine.utils.cosmetiques;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Prestige;

public class CommandParticle extends AbstractParticleCosmetique{

	private static final long serialVersionUID = 1L;
	private String command;
	
	public CommandParticle(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Prestige prestige, Particle particle, String command) {
		super(name, itemShop, humisPrice, pixelPrice, prestige, particle);
		this.command = command;
	}

	@Override
	public void playEffect(Player player) {
		MainShop.getInstance().getServer().dispatchCommand(MainShop.getInstance().getServer().getConsoleSender(), this.command.replace("%p", player.getName()));
	}

	@Override
	public void playDemo(Player player) {
		MainShop.getInstance().getServer().dispatchCommand(MainShop.getInstance().getServer().getConsoleSender(), this.command.replace("%p", player.getName()) + " " + player.getName());
	}

}
