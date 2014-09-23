package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.SQLException;

import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;


/**
 * Gestion des transactions de reliées à la création et
 * suppresion de membres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transaction reliées à la 
 * création et suppresion de membres.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class MembreService extends Service {

	private Connexion cx;

	private MembreDAO membre;

	private ReservationDAO reservation;

	/**
	  * Creation d'une instance
	  */
	public MembreService(MembreDAO membre,
		ReservationDAO reservation) {

		this.cx = membre.getConnexion();
		this.membre = membre;
		this.reservation = reservation;
	}

	/**
	  * Ajout d'un nouveau membre dans la base de donnees.
	  * S'il existe deja, une exception est levee.
	  */
	public void inscrire(int idMembre,
		String nom,
		long telephone,
		int limitePret) throws SQLException,
		BibliothequeException,
		Exception {
		try {
			/* Vérifie si le membre existe déja */
			if(membre.existe(idMembre))
				throw new BibliothequeException("Membre existe deja: "
					+ idMembre);

			/* Ajout du membre. */
			membre.inscrire(idMembre,
				nom,
				telephone,
				limitePret);
			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	  * Suppression d'un membre de la base de donnees.
	  */
	public void desinscrire(int idMembre) throws SQLException,
		BibliothequeException,
		Exception {
		try {
			/* Verifie si le membre existe et son nombre de pret en cours */
			MembreDTO tupleMembre = membre.getMembre(idMembre);
			if(tupleMembre == null)
				throw new BibliothequeException("Membre inexistant: "
					+ idMembre);
			if(tupleMembre.nbPret > 0)
				throw new BibliothequeException("Le membre "
					+ idMembre
					+ " a encore des prets.");
			if(reservation.getReservationMembre(idMembre) != null)
				throw new BibliothequeException("Membre "
					+ idMembre
					+ " a des r�servations");

			/* Suppression du membre */
			int nb = membre.desinscrire(idMembre);
			if(nb == 0)
				throw new BibliothequeException("Membre "
					+ idMembre
					+ " inexistant");
			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}
}//class
