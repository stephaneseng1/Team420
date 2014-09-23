// Fichier Service.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

//import ca.qc.collegeahuntsic.bibliotheque.GestionInterrogation;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.ConnexionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

/**
 * Classe de base pour tous les services.
 *
 * @author Gilles Benichou
 */
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    Connexion cx = null;

    private Connexion getCx() {
		return cx;
	}

	private void setCx(Connexion cx) {
		this.cx = cx;
	}

	/**
     * Crée un Service à partir d'une connexion à la base de données.
     */
    public Service() {
        
    }
    
	public Service(String serveur,String bd,String user,String password) throws ServiceException,
			SQLException {
			// allocation des objets pour le traitement des transactions
			cx = new Connexion(serveur,
				bd,
				user,
				password);
//			livre = new LivreDAO(cx);
//			membre = new MembreDAO(cx);
//			reservation = new ReservationDAO(cx);
//			gestionLivre = new GestionLivre(livre,
//				reservation);
//			gestionMembre = new MembreService(membre,
//				reservation);
//			gestionPret = new PretService(livre,
//				membre,
//				reservation);
//			gestionReservation = new ReservationService(livre,
//				membre,
//				reservation);
//			gestionInterrogation = new GestionInterrogation(cx);
		}

		public void fermer() throws SQLException {
			// fermeture de la connexion
			cx.fermer();
		}
}