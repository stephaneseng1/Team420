// Fichier IReservationFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import org.hibernate.Session;

/**
 * Interface de façade pour manipuler les réservations dans la base de données.
 *
 * @author Gilles Benichou
 */
public interface IReservationFacade extends IFacade {

    /**
     * Lit une réservation à partir de la base de données.
     * 
     * @param session La session à utiliser
     * @param idReservation L'ID de la réservation à lire
     * @return La réservation
     * @throws FacadeException S'il y a une erreur avec la couche Facade
     */
    ReservationDTO getReservation(Session session,
        String idReservation) throws FacadeException;

    /**
     * Place une réservation.
     * 
     * @param session La session Hibernate à utiliser
     * @param reservationDTO La réservation à placer
     * @throws FacadeException S'il y a une erreur avec la couche Service
     */
    void placer(Session session,
        ReservationDTO reservationDTO) throws FacadeException;

    /**
     * Utilise une réservation.
     * 
     * @param session La session Hibernate à utiliser
     * @param reservationDTO La réservation à utiliser
     * @throws FacadeException S'il y a une erreur avec la couche Service
     */
    void utiliser(Session session,
        ReservationDTO reservationDTO) throws FacadeException;

    /**
     * Annule une réservation.
     * 
     * @param session La session Hibernate à utiliser
     * @param reservationDTO Le reservation à annuler
     * @throws FacadeException Si la connexion est <code>null</code>, si la réservation est <code>null</code>, si la réservation n'existe pas
     *         ou s'il y a une erreur avec la base de données
     */
    void annuler(Session session,
        ReservationDTO reservationDTO) throws FacadeException;
}
