// Fichier ReservationFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.implementations;

import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IReservationFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IReservationService;

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
     * @param reservationService Le service de la table <code>reservation</code>
     * @throws FacadeException Si le service de réservations est <code>null</code>
     */
    ReservationFacade(IReservationService reservationService) throws FacadeException {
        super();
        try {
            if(reservationService == null) {
                throw new InvalidServiceException("Le service de réservations ne peut être null");
            }
            setReservationService(reservationService);
        } catch(InvalidServiceException exception) {
            throw new FacadeException(exception);
        }
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
     * @param reservationService La valeur à utiliser pour la variable d'instance <code>this.reservationService</code>
     */
    private void setReservationService(IReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public ReservationDTO getReservation(Session session,
        String idReservation) throws FacadeException {
        try {
            return getReservationService().get(session,
                idReservation);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placer(Session session,
        ReservationDTO reservationDTO) throws FacadeException {
        try {
            getReservationService().placer(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void utiliser(Session session,
        ReservationDTO reservationDTO) throws FacadeException {
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
        ReservationDTO reservationDTO) throws FacadeException {
        try {
            getReservationService().annuler(session,
                reservationDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }
}
