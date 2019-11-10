package humine.utils.cosmetiques;

import java.time.LocalDate;

import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique temporaire de custom head cosmetique
 * meme chose que {@link CustomHeadCosmetique} mais avec une date d'apparition
 * 
 * @author miza
 */
public class TemporaryCustomHeadCosmetique extends AbstractCustomHatCosmetique implements TemporaryCosmetique {

	private static final long serialVersionUID = 5204533781996595113L;
	private LocalDate date;
	
	public TemporaryCustomHeadCosmetique(String name, ItemStack customHead, int humisPrice, int pixelPrice, LocalDate date, Prestige prestige, String libelle)
	{
		super(name, customHead, humisPrice, pixelPrice, prestige, libelle);
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
