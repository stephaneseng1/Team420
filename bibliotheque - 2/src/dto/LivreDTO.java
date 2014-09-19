package dto;
import java.sql.Date;

/**
 * Permet de repr�senter un tuple de la table livre.
 * 
*/

public class LivreDTO {
	private static final long serialVersionUID =1;

	public int idLivre;

	public String titre;

	public String auteur;

	public Date dateAcquisition;

	public int idMembre;

	public Date datePret;
}
