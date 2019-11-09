package humine.utils.shop;

import java.time.LocalDate;

import humine.utils.Shopper;
import humine.utils.cosmetiques.TemporaryCosmetique;

/**
 * Package regroupant les outils de HumineShop <br />
 * Classe stockant des {@link TemporaryCosmetique} <br />
 * l'un des {@link Shop} les utilise avec {@link Shop}
 * et EmperorShop
 * 
 * @author miza
 */
public class RandomShop extends Shop {

	private LocalDate currentDate;
	public static final String RANDOMSHOPNAME = "RandomShop";
	/**
	 * Constructeur
	 * @param name le nom du shop
	 * @param multiPage possibilite de voyager sur plusieurs pages ou non
	 */
	public RandomShop(Shopper owner, boolean multiPage) {
		super(owner, multiPage);
		this.currentDate = LocalDate.now();
	}

	/**
	 * Constructeur
	 * @param name le nom de la page
	 * le parametre multiPage est par defaut a <b>true</b>
	 */
	public RandomShop(Shopper owner) {
		super(owner);
		this.currentDate = LocalDate.now();
	}

	/**
	 * Permet de mettre a jour le RandomShop <br />
	 * 
	 * Exemple: nous somme le 2 janvier 2019 : 02/01/2019 <br />
	 * et le random Shop lui sa date courante est du 1 janvier 2019 : 01/01/2019 <br />
	 * en executant cette fonction le random Shop se mettra a jour de la date
	 * courante et de recuperer les {@link TemporaryCosmetique} du jour
	 */
	public void update() {
		super.resetShop();
		//TODO
		this.currentDate = LocalDate.now();
	}

	/**
	 * @return la date courante a laquelle se trouve le random Shop
	 */
	public LocalDate getCurrentDate() {
		return currentDate;
	}

}
