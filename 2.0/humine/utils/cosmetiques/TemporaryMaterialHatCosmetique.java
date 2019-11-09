package humine.utils.cosmetiques;

import java.time.LocalDate;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import humine.utils.Prestige;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un cosmetique temporaire de material hat
 * meme chose que {@link MaterialHatCosmetique} mais avec une date d'apparition
 * 
 * @author miza
 */
public class TemporaryMaterialHatCosmetique extends AbstractMaterialHatCosmetique implements TemporaryCosmetique
{

	private LocalDate date;
	private static final long serialVersionUID = -1272788625822168734L;
	
	public TemporaryMaterialHatCosmetique(String name, ItemStack itemShop, int humisPrice, int pixelPrice, LocalDate date, Prestige prestige, Material materialHat)
	{
		super(name, itemShop, humisPrice, pixelPrice, materialHat, prestige);
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
