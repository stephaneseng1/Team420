package ca.qc.collegeahuntsic.bibliotheque;
import java.sql.SQLException;

import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.facade.BiblioException;
import ca.qc.collegeahuntsic.bibliotheque.service.MembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.PretService;
import ca.qc.collegeahuntsic.bibliotheque.service.ReservationService;


/**
 * Système de gestion d'une bibliothèque
 *
 *<pre>
 * Ce programme permet de gérer les transaction de base d'une
 * bibliothèque.  Il gère des livres, des membres et des
 * réservations. Les données sont conservées dans une base de
 * données relationnelles accédée avec JDBC.
 *
 * Pré-condition
 *   la base de donnees de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */
public class GestionBibliotheque {
	public Connexion cx;

	public LivreDAO livre;

	public MembreDAO membre;

	public ReservationDAO reservation;

	public GestionLivre gestionLivre;

	public MembreService gestionMembre;

	public PretService gestionPret;

	public ReservationService gestionReservation;

	public GestionInterrogation gestionInterrogation;

	/**
	  * Ouvre une connexion avec la BD relationnelle et
	  * alloue les gestionnaires de transactions et de tables.
	  * <pre>
	  * 
	  * @param serveur SQL
	  * @param bd nom de la bade de données
	  * @param user user id pour établir une connexion avec le serveur SQL
	  * @param password mot de passe pour le user id
	  *</pre>
	  */
	public GestionBibliotheque(String serveur,
		String bd,
		String user,
		String password) throws BiblioException,
		SQLException {
		// allocation des objets pour le traitement des transactions
		cx = new Connexion(serveur,
			bd,
			user,
			password);
		livre = new LivreDAO(cx);
		membre = new MembreDAO(cx);
		reservation = new ReservationDAO(cx);
		gestionLivre = new GestionLivre(livre,
			reservation);
		gestionMembre = new MembreService(membre,
			reservation);
		gestionPret = new PretService(livre,
			membre,
			reservation);
		gestionReservation = new ReservationService(livre,
			membre,
			reservation);
		gestionInterrogation = new GestionInterrogation(cx);
	}

	public void fermer() throws SQLException {
		// fermeture de la connexion
		cx.fermer();
	}
}
