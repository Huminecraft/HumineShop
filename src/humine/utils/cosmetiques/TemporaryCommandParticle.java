package humine.utils.cosmetiques;

import java.time.LocalDate;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import humine.main.MainShop;
import humine.utils.Prestige;

public class TemporaryCommandParticle extends AbstractParticleCosmetique implements TemporaryCosmetique{

	private static final long serialVersionUID = 1L;
	private LocalDate date;
	private String command;

	public TemporaryCommandParticle(String name, ItemStack itemShop, int humisPrice, int pixelPrice, Prestige prestige,
			Particle particle, String command, LocalDate date) {
		super(name, itemShop, humisPrice, pixelPrice, prestige, particle);
		this.date = date;
		this.command = command;
	}

	@Override
	public Cosmetique getCosmetique() {
		return this;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public void setDate(LocalDate date) {
		this.date = date;
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
