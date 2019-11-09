package humine.utils.cosmetiques;

import java.time.LocalDate;

import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique temporaire de particle
 * meme chose que {@link ParticleCosmetique} mais avec une date d'apparition
 * 
 * @author miza
 */
public class TemporaryParticleCosmetique extends AbstractParticleCosmetique implements TemporaryCosmetique{

	private static final long serialVersionUID = 5927129520500340997L;
	private LocalDate date;
	
	public TemporaryParticleCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, LocalDate date, Prestige prestige, Particle particle)
	{
		super(name, itemShop, humisPrice, pixelPrice, prestige, particle);
		this.date = date;
	}

	@Override
	public Cosmetique getCosmetique()
	{
		return this;
	}


	@Override
	public LocalDate getDate()
	{
		return date;
	}


	@Override
	public void setDate(LocalDate date)
	{
		this.date = date;
	}
}
