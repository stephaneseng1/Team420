// Fichier ReservationFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliotheque.facade.implementations;

import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.facade.interfaces.IReservationFacade;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IReservationService;

/**
 * Facade pour interagir avec le service de réservations.
 * 
 * @author Gilles Benichou
 */
public class ReservationFacade extends Facade implements IReservationFacade {
    private IReservationService reservationService;

    /**
     * Crée la façade de la table <code>reservation</code>.
     * 
     * @param reservationService
     *            Le service de la table <code>reservation</code>
     * @throws InvalidServiceException
     *             Si le service de réservations est <code>null</code>
     */
    ReservationFacade(IReservationService reservationService) throws InvalidServiceException {
        super();
        if(reservationService == null) {
            throw new InvalidServiceException("Le service de réservations ne peut être null");
        }
        setReservationService(reservationService);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.reservationService</code>.
     * 
     * @return La variable d'instance <code>this.reservationService</code>
     */
    private IReservationService getReservationService() {
        return this.reservationService;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationService</code>.
     * 
     * @param reservationService
     *            La valeur à utiliser pour la variable d'instance
     *            <code>this.reservationService</code>
     */
    private void setReservationService(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void placer(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        ExistingReservationException,
        InvalidDTOClassException,
        FacadeException,
        InvalidCriterionValueException {
        try {
            getReservationService().placer(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DAOException
     */
    @Override
    public void utiliser(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingReservationException,
        ExistingLoanException,
        InvalidLoanLimitException,
        InvalidDTOClassException,
        FacadeException,
        InvalidCriterionValueException,
        DAOException {
        try {
            getReservationService().utiliser(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void annuler(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidDTOClassException,
        FacadeException {
        try {
            getReservationService().annuler(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }
}
