// Fichier IReservationService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces;

import java.util.List;
import org.hibernate.Session;
//Fichier IReservationService.java
//Auteur : Gilles Bénichou
//Date de création : 2014-09-01
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;

/**
 * Interface de service pour manipuler les réservations dans la base de données.
 *
 * @author Gilles Benichou
 */
public interface IReservationService extends IService {

    /**
     * Ajoute une nouvelle réservation dans la base de données.
     * 
     * @param session La session à utiliser
     * @param reservationDTO La réservation à ajouter
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    void add(Session session,
        ReservationDTO reservationDTO) throws ServiceException;

    /**
     * Lit une réservation à partir de la base de données.
     * 
     * @param session La session à utiliser
     * @param idReservation L'ID de la réservation à lire
     * @return La réservation
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    ReservationDTO get(Session session,
        String idReservation) throws ServiceException;

    /**
     * Met à jour une réservation dans la base de données.
     * 
     * @param session La session à utiliser
     * @param reservationDTO La réservation à mettre à jour
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    void update(Session session,
        ReservationDTO reservationDTO) throws ServiceException;

    /**
     * Supprime une réservation de la base de données.
     * 
     * @param session La session à utiliser
     * @param reservationDTO La réservation à supprimer
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    void delete(Session session,
        ReservationDTO reservationDTO) throws ServiceException;

    /**
     * Trouve toutes les réservations de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si
     * aucune réservation n'est trouvée, une {@link List} vide est retournée.
     * 
     * @param session La session à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de toutes les réservations ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    List<ReservationDTO> getAll(Session session,
        String sortByPropertyName) throws ServiceException;

    /**
     * Place une réservation.
     * 
     * @param session La session Hibernate à utiliser
     * @param reservationDTO La réservation à placer
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    void placer(Session session,
        ReservationDTO reservationDTO) throws ServiceException;

    /**
     * Utilise une réservation.
     * 
     * @param session La session Hibernate à utiliser
     * @param reservationDTO La réservation à utiliser
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    void utiliser(Session session,
        ReservationDTO reservationDTO) throws ServiceException;

    /**
     * Annule une réservation.
     * 
     * @param session La session Hibernate à utiliser
     * @param reservationDTO Le reservation à annuler
     * @throws ServiceException S'il y a une erreur à la couche Service
     */
    void annuler(Session session,
        ReservationDTO reservationDTO) throws ServiceException;
}
