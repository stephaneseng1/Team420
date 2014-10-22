// Fichier ReservationDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.implementations;

import java.util.List;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>reservation</code>.
 *
 * @author Gilles Benichou
 */
public class ReservationDAO extends DAO implements IReservationDAO {
    /**
     * Crée le DAO de la table <code>reservation</code>.
     *
     * @param reservationDTOClass
     *            The classe de réservation DTO to use
     * @throws InvalidDTOClassException
     *             Si la classe de DTO est <code>null</code>
     */
    ReservationDAO(Class<ReservationDTO> reservationDTOClass) throws InvalidDTOClassException {
        super(reservationDTOClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservationDTO> findByMembre(Session session,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException,
        InvalidCriterionValueException {
        return (List<ReservationDTO>) super.find(session,
            ReservationDTO.ID_MEMBRE_COLUMN_NAME,
            idMembre,
            sortByPropertyName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservationDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException,
        InvalidCriterionValueException {
        return (List<ReservationDTO>) super.find(session,
            ReservationDTO.ID_LIVRE_COLUMN_NAME,
            idLivre,
            sortByPropertyName);
    }
}
