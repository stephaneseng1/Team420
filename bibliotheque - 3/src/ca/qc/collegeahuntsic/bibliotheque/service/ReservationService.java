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
 * Gestion des transactions de reliées aux réservations de livres
 * par les membres dans une bibliothèque.
 *
 * Ce programme permet de gérer les transactions réserver,
 * prendre et annuler.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class ReservationService extends Service {

	private LivreDAO livre;

	private MembreDAO membre;

	private ReservationDAO reservation;
	/**
	  * Creation d'une instance.
	  * La connection de l'instance de livre et de membre doit être la même que cx,
	  * afin d'assurer l'intégrité des transactions.
	  */
	public ReservationService(LivreDAO livre,
		MembreDAO membre,
		ReservationDAO reservation) throws BibliothequeException {
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
	  * Réservation d'un livre par un membre.
	  * Le livre doit être prêté.
	  */
	public void reserver(int idReservation,
		int idLivre,
		int idMembre,
		String dateReservation) throws SQLException,
		BibliothequeException,
		Exception {
		try {
			/* Vérifier que le livre est preté */
			LivreDTO tupleLivre = livre.getLivre(idLivre);
			if(tupleLivre == null)
				throw new BibliothequeException("Livre inexistant: "
					+ idLivre);
			if(tupleLivre.getIdMembre() == 0)
				throw new BibliothequeException("Livre "
					+ idLivre
					+ " n'est pas prete");
			if(tupleLivre.getIdMembre() == idMembre)
				throw new BibliothequeException("Livre "
					+ idLivre
					+ " deja prete a ce membre");

			/* Vérifier que le membre existe */
			MembreDTO tupleMembre = membre.getMembre(idMembre);
			if(tupleMembre == null)
				throw new BibliothequeException("Membre inexistant: "
					+ idMembre);

			/* Vérifier si date réservation >= datePret */
			if(Date.valueOf(dateReservation).before(tupleLivre.getDatePret()))
				throw new BibliothequeException("Date de reservation inferieure � la date de pret");

			/* Vérifier que la réservation n'existe pas */
			if(reservation.existe(idReservation))
				throw new BibliothequeException("R�servation "
					+ idReservation
					+ " existe deja");

			/* Creation de la réservation */
			reservation.reserver(idReservation,
				idLivre,
				idMembre,
				dateReservation);
			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	  * Prise d'une réservation.
	  * Le livre ne doit pas être prêté.
	  * Le membre ne doit pas avoir dépassé sa limite de pret.
	  * La réservation doit la être la première en liste.
	  */
	public void prendreRes(int idReservation,
		String datePret) throws SQLException,
		BibliothequeException,
		Exception {
		try {
			/* Vérifie s'il existe une réservation pour le livre */
			ReservationDTO tupleReservation = reservation.getReservation(idReservation);
			if(tupleReservation == null)
				throw new BibliothequeException("R�servation inexistante : "
					+ idReservation);

			/* Vérifie que c'est la première réservation pour le livre */
			ReservationDTO tupleReservationPremiere = reservation.getReservationLivre(tupleReservation.idLivre);
			if(tupleReservation.idReservation != tupleReservationPremiere.idReservation)
				throw new BibliothequeException("La r�servation n'est pas la premi�re de la liste "
					+ "pour ce livre; la premiere est "
					+ tupleReservationPremiere.idReservation);

			/* Vérifier si le livre est disponible */
			LivreDTO tupleLivre = livre.getLivre(tupleReservation.idLivre);
			if(tupleLivre == null)
				throw new BibliothequeException("Livre inexistant: "
					+ tupleReservation.idLivre);
			if(tupleLivre.getIdMembre() != 0)
				throw new BibliothequeException("Livre "
					+ tupleLivre.getIdLivre()
					+ " deja pr�t� � "
					+ tupleLivre.getIdMembre());

			/* Vérifie si le membre existe et sa limite de pret */
			MembreDTO tupleMembre = membre.getMembre(tupleReservation.idMembre);
			if(tupleMembre == null)
				throw new BibliothequeException("Membre inexistant: "
					+ tupleReservation.idMembre);
			if(tupleMembre.nbPret >= tupleMembre.limitePret)
				throw new BibliothequeException("Limite de pr�t du membre "
					+ tupleReservation.idMembre
					+ " atteinte");

			/* Vérifier si datePret >= tupleReservation.dateReservation */
			if(Date.valueOf(datePret).before(tupleReservation.dateReservation))
				throw new BibliothequeException("Date de pr�t inf�rieure � la date de r�servation");

			/* Enregistrement du pret. */
			if(livre.preter(tupleReservation.idLivre,
				tupleReservation.idMembre,
				datePret) == 0)
				throw new BibliothequeException("Livre supprim� par une autre transaction");
			if(membre.preter(tupleReservation.idMembre) == 0)
				throw new BibliothequeException("Membre supprim� par une autre transaction");
			/* Eliminer la réservation */
			reservation.annulerRes(idReservation);
			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}

	/**
	  * Annulation d'une réservation.
	  * La réservation doit exister.
	  */
	public void annulerRes(int idReservation) throws SQLException,
		BibliothequeException,
		Exception {
		try {

			/* Vérifier que la réservation existe */
			if(reservation.annulerRes(idReservation) == 0)
				throw new BibliothequeException("Reservation "
					+ idReservation
					+ " n'existe pas");

			cx.commit();
		} catch(Exception e) {
			cx.rollback();
			throw e;
		}
	}
}
