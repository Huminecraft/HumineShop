package humine.utils;

/**
 * Package regroupant les outils de HumineShop
 * Classe regroupant les prestiges des cosmetiques et leurs couleurs
 * 
 * @author miza
 */
public enum Prestige {
	COMMUN ("GRAY"),
	PARTICULIER ("GREEN"),
	RARE ("AQUA"),
	EPIQUE ("LIGHT_PURPLE"),
	LEGENDAIRE ("GOLD");
	
	private String color;
	
	Prestige(String color) {
		this.color = color;
	}
	
	public String getColor() {
		return color;
	}
}
