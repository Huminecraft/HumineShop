package humine.utils.cosmetiques;

import java.time.LocalDate;

public interface TemporaryCosmetique
{
	public Cosmetique getCosmetique();
	
	public LocalDate getDate();
	
	public void setDate(LocalDate date);
}
