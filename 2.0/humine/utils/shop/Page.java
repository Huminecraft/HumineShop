package humine.utils.shop;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import humine.utils.Prestige;
import humine.utils.cosmetiques.Cosmetique;
import humine.utils.cosmetiques.CustomHeadCosmetique;
import humine.utils.cosmetiques.MaterialHatCosmetique;
import humine.utils.cosmetiques.ParticleCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryCustomHeadCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryMaterialHatCosmetique;
import humine.utils.cosmetiques.temporary.TemporaryParticleCosmetique;

/**
 * Package regroupant les outils de HumineShop
 * Classe representant un contenu de {@link Cosmetique}
 * 
 * @author miza
 */
public class Page
{

	private String			name;
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
	public Page(String name, int size)
	{
		this.name = name;
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
	public boolean containsCosmetique(long id)
	{
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
			{
				if (this.cosmetiques[i].getId().equals("" + id))
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
	 * sauvegarder la page dans un <b>dossier</b>
	 * 
	 * @param folder
	 *            le dossier dans lequel la page sera enregistrer
	 */
	public void save(File folder)
	{
		if(folder.exists()) {
			for(File f : folder.listFiles())
				f.delete();
			folder.delete();
		}
		
		folder.mkdirs();

		File index = new File(folder, "index.yml");
		if (!index.exists())
		{
			try
			{
				index.createNewFile();
			}
			catch (IOException e)
			{
				System.err.println("Erreur dans la creation de index.yml");
				e.printStackTrace();
				return;
			}
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		config.set("name", this.name);
		config.set("size", this.size);
		try
		{
			config.save(index);
		}
		catch (IOException e)
		{
			System.err.println("Erreur dans l'enregistrement de index.yml");
			e.printStackTrace();
			return;
		}

		File filec;
		for (int i = 0; i < this.cosmetiques.length; i++)
		{
			if (this.cosmetiques[i] != null)
			{
				Cosmetique c = this.cosmetiques[i];
				filec = new File(folder, c.getId() + ".yml");
				c.save(filec);
			}
		}
	}

	/**
	 * charger une page a partir d'un <b>dossier</b>
	 * 
	 * @param folder
	 *            le dossier dans lequel la page sera chargee
	 */
	public void load(File folder)
	{
		if (!folder.exists())
			return;

		File index = new File(folder, "index.yml");
		if (!index.exists())
		{
			System.err.println("Erreur index.yml inexistant");
			return;
		}

		FileConfiguration config = YamlConfiguration.loadConfiguration(index);
		if (!config.contains("name") || !config.contains("size"))
		{
			System.err.println("Erreur parametre manquant dans le fichier " + index.getName());
			return;
		}

		this.name = config.getString("name");
		this.size = config.getInt("size");
		index.delete();

		this.cosmetiques = new Cosmetique[this.size];
		for (int i = 0; i < folder.listFiles().length; i++)
		{
			config = YamlConfiguration.loadConfiguration(folder.listFiles()[i]);
			
			if (config.contains("particle")) {
				if(config.contains("date")) {
					TemporaryParticleCosmetique c = new TemporaryParticleCosmetique("", null, 0, 0, LocalDate.now(), null, null);
					c.load(folder.listFiles()[i]);
					this.cosmetiques[i] = c;
				}
				else {
					ParticleCosmetique c = new ParticleCosmetique("", null, 0, 0, null, Prestige.COMMUN);
					c.load(folder.listFiles()[i]);
					this.cosmetiques[i] = c;
				}
			}
			else if(config.contains("materialHat")) {
				if(config.contains("date")) {
					TemporaryMaterialHatCosmetique c = new TemporaryMaterialHatCosmetique("", null, 0, 0, LocalDate.now(), null, null);
					c.load(folder.listFiles()[i]);
					this.cosmetiques[i] = c;
				}
				else {
					MaterialHatCosmetique c = new MaterialHatCosmetique("", null, 0, 0, null, Prestige.COMMUN);
					c.load(folder.listFiles()[i]);
					this.cosmetiques[i] = c;
				}
			}
			else if(config.contains("libelle")) {
				if(config.contains("date")) {
					TemporaryCustomHeadCosmetique c = new TemporaryCustomHeadCosmetique("", null, 0, 0, LocalDate.now(), Prestige.COMMUN, null);
					c.load(folder.listFiles()[i]);
					this.cosmetiques[i] = c;
				}
				else {
					CustomHeadCosmetique c = new CustomHeadCosmetique("", null, 0, 0, Prestige.COMMUN, null);
					c.load(folder.listFiles()[i]);
					this.cosmetiques[i] = c;
				}
			}
		}
	}

	/**
	 * @return String le nom de la page
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            le nouveau nom
	 */
	public void setName(String name)
	{
		this.name = name;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(cosmetiques);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Page))
			return false;
		Page other = (Page) obj;
		if (!Arrays.equals(cosmetiques, other.cosmetiques))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	
	

}
