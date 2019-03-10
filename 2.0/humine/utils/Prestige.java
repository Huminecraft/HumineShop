package humine.utils;


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
