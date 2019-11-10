package humine.utils.shop;

import java.io.Serializable;
import java.util.Arrays;

import humine.utils.cosmetiques.Cosmetique;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un contenu de {@link Cosmetique}
 * 
 * @author miza
 */
public class Page implements Serializable
{

	private static final long serialVersionUID = 2350240758428783615L;
	private Cosmetique[]	cosmetiques;
	private int				size;


	/**
	 * Classe permettant de stocker et d'afficher une liste de cosmetique depuis
	 * un inventaire
	 * 
	 * @param name
	 *            le nom de la page
	 * @param size
	 *            la taille de la page (donc aussi de l'inventaire)
	 */
	public Page(int size)
	{
		this.size = size;

		while (this.size % 9 != 0)
			this.size++;

		this.cosmetiques = new Cosmetique[this.size];
	}

	/**
	 * Ajouter un cosmetique dans la page
	 * 
	 * @param cosmetique
	 *            le cosmetique a ajouter
	 * @return boolean renvoie vrai si le cosmetique a etait ajoute dans la
	 *         page, sinon false
	 */
	public boolean addCosmetique(Cosmetique cosmetique)
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] == null)
			{
				this.cosmetiques[i] = cosmetique;
				return true;
			}
		}
		return false;
	}

	/**
	 * Ajouter un cosmetique dans la page a un emplacement specifique
	 * 
	 * @param slot
	 *            l'emplacement ou doit se trouver le cosmetique
	 * @param cosmetique
	 *            le cosmetique a ajouter
	 * @return boolean renvoie vrai si le cosmetique a etait ajoute dans la
	 *         page, sinon false
	 */
	public boolean setCosmetique(int slot, Cosmetique cosmetique)
	{
		if (slot >= 0 && slot < this.size)
		{
			this.cosmetiques[slot] = cosmetique;
			return true;
		}
		return false;
	}

	/**
	 * supprime un cosmetique de la page
	 * 
	 * @param cosmetique
	 *            le cosmetique a supprimer
	 * @return boolean renvoie vrai si le cosmetique a etait supprime de la
	 *         page, sinon false
	 */
	public boolean removeCosmetique(Cosmetique cosmetique)
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
			{
				if (this.cosmetiques[i].getId().equals(cosmetique.getId()))
				{
					this.cosmetiques[i] = null;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * supprime un cosmetique de la page
	 * 
	 * @param id
	 *            l'id du cosmetique a supprimer
	 * @return boolean renvoie vrai si le cosmetique a etait supprime de la
	 *         page, sinon false
	 */
	public boolean removeCosmetique(long id)
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
			{
				if (this.cosmetiques[i].getId().equals("" + id))
				{
					this.cosmetiques[i] = null;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * supprime un cosmetique de la page
	 * 
	 * @param id
	 *            l'id du cosmetique a supprimer
	 * @return boolean renvoie vrai si le cosmetique a etait supprime de la
	 *         page, sinon false
	 */
	public boolean removeCosmetique(String id)
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
			{
				if (this.cosmetiques[i].getId().equals(id))
				{
					this.cosmetiques[i] = null;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * verifie si la page contient le cosmetique
	 * 
	 * @param cosmetique
	 *            le cosmetique a verifier
	 * @return boolean renvoie vrai si le cosmetique a etait trouve de la page,
	 *         sinon false
	 */
	public boolean containsCosmetique(Cosmetique cosmetique)
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
			{
				if (this.cosmetiques[i].getId().equals(cosmetique.getId()))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * verifie si la page contient le cosmetique
	 * 
	 * @param id
	 *            l'id du cosmetique a verifier
	 * @return boolean renvoie vrai si le cosmetique a etait trouve de la page,
	 *         sinon false
	 */
	public boolean containsCosmetique(String id)
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
			{
				if (this.cosmetiques[i].getId().equals(id))
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * verifie si la page est pleine
	 * 
	 * @return boolean renvoie vrai si la page est pleine, sinon false
	 */
	public boolean isFull()
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] == null)
				return false;
		}
		return true;
	}

	/**
	 * verifie si la page est vide
	 * 
	 * @return boolean renvoie vrai si la page est vide, sinon false
	 */
	public boolean isEmpty()
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
				return false;
		}
		return true;
	}
	
	/**
	 * Recupere le cosmetique contenu dans cetet page
	 * @param id du cosmetique a rechercher
	 * @return le cosmetique trouve, sinon null si introuvable
	 */
	public Cosmetique getCosmetique(String id) {
		for(int i = 0; i < this.cosmetiques.length; i++) {
			if(this.cosmetiques[i] != null) {
				if(this.cosmetiques[i].getId().equals(id)) {
					return this.cosmetiques[i];
				}
			}
		}
		
		return null;
	}
	

	/**
	 * @return Cosmetique[] les cosmetiques contenus dans la page
	 */
	public Cosmetique[] getCosmetiques()
	{
		return cosmetiques;
	}

	/**
	 * @param cosmetiques
	 *            les nouveaux cosmetiques de la page
	 */
	public void setCosmetiques(Cosmetique[] cosmetiques)
	{
		this.cosmetiques = cosmetiques;
	}

	/**
	 * @return int la taille de la page
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * @param size
	 *            la nouvelle taille de la page
	 */
	public void setSize(int size)
	{
		this.size = size;
		while (this.size % 9 != 0)
			this.size++;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cosmetiques);
		result = prime * result + size;
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
		Page other = (Page) obj;
		if (!Arrays.equals(cosmetiques, other.cosmetiques))
			return false;
		if (size != other.size)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Page [cosmetiques=" + Arrays.toString(cosmetiques) + ", size=" + size + "]";
	}
	
	

}
