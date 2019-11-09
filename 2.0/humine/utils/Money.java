package humine.utils;

import java.io.Serializable;

/**
 * Permet de contenir une valeur monetaire
 * @author allan
 */
public class Money implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private int amount;

	/**
	 * Constructeur
	 * @param amount le montant de depart
	 */
	public Money(int amount, String name){
		this.amount = amount;
		this.name = name;
	}
	
	/**
	 * Constructeur <br />
	 * le montant de depart est par defaut a 0
	 */
	public Money(String name){
		this(0, name);
	}
	
	/**
	 * retourne la valeur monetaire
	 * @return la valeur
	 */
	public int getAmount()
	{
		return amount;
	}
	
	/**
	 * definie la valeur monetaire
	 * @param amount la valeur de definition
	 */
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
	
	/**
	 * Rajoute une quantitee a la valeur monetaire de base
	 * @param amount la quantitee a ajouter
	 */
	public void addAmount(int amount) {
		this.amount += amount;
	}
	
	/**
	 * Supprime une quantitee a la valeur monetaire de base
	 * @param amount la quantite a enlever
	 */
	public void removeAmount(int amount) {
		this.amount -= amount;
	}

	/**
	 * Le nom de la monnaie
	 * @return le nom de la monnaie
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * redefini le nom de la monnaie
	 * @param name le nouveau nom
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Money other = (Money) obj;
		if (amount != other.amount)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Money [name=" + name + ", amount=" + amount + "]";
	}
	
	
}
