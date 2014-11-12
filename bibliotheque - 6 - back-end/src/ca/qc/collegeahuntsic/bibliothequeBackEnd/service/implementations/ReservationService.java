// Fichier ReservationService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IReservationService;
import org.hibernate.Session;

/**
 * Service de la table <code>reservation</code>.
 *
 * @author Gilles Benichou
 */
public class ReservationService extends Service implements IReservationService {

    private IReservationDAO reservationDAO;

    private IPretDAO pretDAO;

    /**
     * Crée le service de la table <code>reservation</code>.
     *
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     * @param pretDAO Le DAO de la table <code>reservation</code>
     * @throws ServiceException Si le DAO de réservation est <code>null</code>, si le DAO de membre est <code>null</code>, si le DAO de livre
     *         est <code>null</code> ou si le DAO de prêt est <code>null</code>
     */

    ReservationService(IReservationDAO reservationDAO,
        IPretDAO pretDAO) throws ServiceException {
        super();
        try {
            if(reservationDAO == null) {
                throw new InvalidDAOException("Le DAO de réservation ne peut être null");
            }
            if(pretDAO == null) {
                throw new InvalidDAOException("Le DAO de pret ne peut être null");
            }
            setReservationDAO(reservationDAO);
            setPretDAO(pretDAO);
        } catch(InvalidDAOException exception) {
            throw new ServiceException(exception);
        }
    }

    // Region Getters and Setters

    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    private IReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO La valeur à utiliser pour la variable d'instance <code>this.reservationDAO</code>
     */
    private void setReservationDAO(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    private IPretDAO getPretDAO() {
        return this.pretDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param pretDAO La valeur à utiliser pour la variable d'instance <code>this.pretDAO</code>
     */
    private void setPretDAO(IPretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Session session,
        ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().add(session,
                reservationDTO);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidDTOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReservationDTO get(Session session,
        String idReservation) throws ServiceException {
        try {
            return (ReservationDTO) getReservationDAO().get(session,
                idReservation);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidPrimaryKeyException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Session session,
        ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().update(session,
                reservationDTO);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidDTOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Session session,
        ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().delete(session,
                reservationDTO);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidDTOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservationDTO> getAll(Session session,
        String sortByPropertyName) throws ServiceException {
        try {
            return (List<ReservationDTO>) getReservationDAO().getAll(session,
                sortByPropertyName);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidSortByPropertyException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placer(Session session,
        ReservationDTO reservationDTO) throws ServiceException {
        try {
            if(session == null) {
                throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
            }
            if(reservationDTO == null) {
                throw new InvalidDTOException("La réservation ne peut être null");
            }
            final LivreDTO unLivreDTO = reservationDTO.getLivreDTO();
            final MembreDTO unMembreDTO = reservationDTO.getMembreDTO();
            final Set<PretDTO> prets = unLivreDTO.getPrets();
            if(prets.isEmpty()) {
                throw new MissingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            boolean aEteEmprunteParMembre = false;
            for(PretDTO pretDTO : prets) {
                aEteEmprunteParMembre = unMembreDTO.equals(pretDTO.getMembreDTO());
            }
            if(aEteEmprunteParMembre) {
                throw new ExistingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") est déjà prêté à "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ")");
            }
            final Set<ReservationDTO> reservations = unMembreDTO.getReservations();
            for(ReservationDTO uneAutreReservationDTO : reservations) {
                if(unLivreDTO.equals(uneAutreReservationDTO.getLivreDTO())) {
                    throw new ExistingReservationException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est déjà réservé pour quelqu'un d'autre");
                }
            }
            reservationDTO.setDateReservation(new Timestamp(System.currentTimeMillis()));
            getReservationDAO().add(session,
                reservationDTO);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | MissingLoanException
            | ExistingLoanException
            | ExistingReservationException exception) {
            throw new ServiceException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void utiliser(Session session,
        ReservationDTO reservationDTO) throws ServiceException {
        try {
            if(session == null) {
                throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
            }
            if(reservationDTO == null) {
                throw new InvalidDTOException("La réservation ne peut être null");
            }
            final LivreDTO unLivreDTO = reservationDTO.getLivreDTO();
            final MembreDTO unMembreDTO = reservationDTO.getMembreDTO();
            ReservationDTO uneReservationDTO = null;
            final List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
            if(!reservations.isEmpty()) {
                uneReservationDTO = reservations.get(0);
                if(!unMembreDTO.equals(uneReservationDTO.getMembreDTO())) {
                    final MembreDTO booker = uneReservationDTO.getMembreDTO();
                    throw new ExistingReservationException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est réservé pour "
                        + booker.getNom()
                        + " (ID de membre : "
                        + booker.getIdMembre()
                        + ")");
                }
            }
            for(PretDTO unPretDTO : unLivreDTO.getPrets()) {
                if(unPretDTO.getDateRetour() == null) {
                    final MembreDTO emprunteur = unPretDTO.getMembreDTO();
                    throw new ExistingLoanException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") a été prêté à "
                        + emprunteur.getNom()
                        + " (ID de membre : "
                        + emprunteur.getIdMembre()
                        + ")");
                }
            }
            if(unMembreDTO.getNbPret().equals(unMembreDTO.getLimitePret())) {
                throw new InvalidLoanLimitException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }
            unMembreDTO.setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) + 1));
            final PretDTO unPretDTO = new PretDTO();
            unPretDTO.setMembreDTO(unMembreDTO);
            unPretDTO.setLivreDTO(unLivreDTO);
            unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            unPretDTO.setDateRetour(null);
            getPretDAO().add(session,
                unPretDTO);
            unLivreDTO.getReservations().remove(uneReservationDTO);
            annuler(session,
                uneReservationDTO);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidDTOException
            | ExistingReservationException
            | ExistingLoanException
            | InvalidLoanLimitException exception) {
            throw new ServiceException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void annuler(Session session,
        ReservationDTO reservationDTO) throws ServiceException {
        try {
            if(session == null) {
                throw new InvalidHibernateSessionException("La session Hibernate ne peut être null");
            }
            if(reservationDTO == null) {
                throw new InvalidDTOException("La réservation ne peut être null");
            }
            delete(session,
                reservationDTO);
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException exception) {
            throw new ServiceException(exception);
        }
    }
}
