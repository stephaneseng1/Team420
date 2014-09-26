package ca.qc.collegeahuntsic.bibliotheque.service;
import java.sql.Date;
import java.sql.SQLException;

import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;

/**
 * Gestion des transactions de reliées aux prêts de livres
 * aux membres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transactions prêter,
 * renouveler et retourner.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class PretService extends Service {

	private LivreDAO livre;

	private MembreDAO membre;

	private ReservationDAO reservation;

	/**
	  * Creation d'une instance.
	  * La connection de l'instance de livre et de membre doit être la même que cx,
	  * afin d'assurer l'intégrité des transactions.
	  */
	public PretService(LivreDAO livre, MembreDAO membre, ReservationDAO reservation) throws BibliothequeException {
		if(livre.getConnexion() != membre.getConnexion()
			|| reservation.getConnexion() != membre.getConnexion())
			throw new BibliothequeException("Les instances de livre, de membre et de reservation n'utilisent pas la même connexion au serveur");
		setLivre(livre);
		setMembre(membre);
		setReservation(reservation);
	}

	private void setLivre(LivreDAO livre) {
		this.livre = livre;
	}

	private void setMembre(MembreDAO membre) {
		this.membre = membre;
	}

	private void setReservation(ReservationDAO reservation) {
		this.reservation = reservation;
	}

	/**
	  * Pret d'un livre à un membre.
	  * Le livre ne doit pas être prete.
	  * Le membre ne doit pas avoir dépassé sa limite de prêt.
	  */
	public void preter(int idLivre,
		int idMembre,
		String datePret) throws SQLException,
		BibliothequeException,
		Exception {
		try {
			/* Vérfier si le livre est disponible */
			LivreDTO tupleLivre = livre.getLivre(idLivre);
			if(tupleLivre == null)
				throw new BibliothequeException("Livre inexistant: "
					+ idLivre);
			if(tupleLivre.getIdMembre() != 0)
				throw new BibliothequeException("Livre "
					+ idLivre
					+ " deja prete a "
					+ tupleLivre.getIdMembre());

			/* Vérifie si le membre existe et sa limite de pret */
			MembreDTO tupleMembre = membre.getMembre(idMembre);
			if(tupleMembre == null)
				throw new BibliothequeException("Membre inexistant: "
					+ idMembre);
			if(tupleMembre.nbPret >= tupleMembre.limitePret)
				throw new BibliothequeException("Limite de pret du membre "
					+ idMembre
					+ " atteinte");

			/* Vérifie s'il existe une réservation pour le livre */
			ReservationDTO tupleReservation = reservation.getReservationLivre(idLivre);
			if(tupleReservation != null)
				throw new BibliothequeException("Livre réservé par : "
					+ tupleReservation.idMembre
					+ " idReservation : "
					+ tupleReservation.idReservation);

			/* Enregistrement du pret. */
			int nb1 = livre.preter(idLivre,
				idMembre,
				datePret);
			if(nb1 == 0)
				throw new BibliothequeException("Livre supprimé par une autre transaction");
			int nb2 = membre.preter(idMembre);
			if(nb2 == 0)
				throw new BibliothequeException("Membre supprimé par une autre transaction");
			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	  * Renouvellement d'un pret.
	  * Le livre doit être prêté.
	  * Le livre ne doit pas être réservé.
	  */
	public void renouveler(int idLivre,
		String datePret) throws SQLException,
		BibliothequeException,
		Exception {
		try {
			/* Verifier si le livre est pr�t� */
			LivreDTO tupleLivre = livre.getLivre(idLivre);
			if(tupleLivre == null)
				throw new BibliothequeException("Livre inexistant: "
					+ idLivre);
			if(tupleLivre.getIdMembre() == 0)
				throw new BibliothequeException("Livre "
					+ idLivre
					+ " n'est pas prete");

			/* Verifier si date renouvellement >= datePret */
			if(Date.valueOf(datePret).before(tupleLivre.getDatePret()))
				throw new BibliothequeException("Date de renouvellement inferieure � la date de pret");

			/* Vérifie s'il existe une réservation pour le livre */
			ReservationDTO tupleReservation = reservation.getReservationLivre(idLivre);
			if(tupleReservation != null)
				throw new BibliothequeException("Livre réservé par : "
					+ tupleReservation.idMembre
					+ " idReservation : "
					+ tupleReservation.idReservation);

			/* Enregistrement du pret. */
			int nb1 = livre.preter(idLivre,
				tupleLivre.getIdMembre(),
				datePret);
			if(nb1 == 0)
				throw new BibliothequeException("Livre supprime par une autre transaction");
			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	  * Retourner un livre prêté
	  * Le livre doit etre prêté.
	  */
	public void retourner(int idLivre,
		String dateRetour) throws SQLException,
		BibliothequeException,
		Exception {
		try {
			/* Verifier si le livre est pr�t� */
			LivreDTO tupleLivre = livre.getLivre(idLivre);
			if(tupleLivre == null)
				throw new BibliothequeException("Livre inexistant: "
					+ idLivre);
			if(tupleLivre.getIdMembre() == 0)
				throw new BibliothequeException("Livre "
					+ idLivre
					+ " n'est pas pr�t� ");

			/* Verifier si date retour >= datePret */
			if(Date.valueOf(dateRetour).before(tupleLivre.getDatePret()))
				throw new BibliothequeException("Date de retour inferieure � la date de pret");

			/* Retour du pret. */
			int nb1 = livre.retourner(idLivre);
			if(nb1 == 0)
				throw new BibliothequeException("Livre supprim� par une autre transaction");

			int nb2 = membre.retourner(tupleLivre.getIdMembre());
			if(nb2 == 0)
				throw new BibliothequeException("Livre supprim� par une autre transaction");
			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}
}
