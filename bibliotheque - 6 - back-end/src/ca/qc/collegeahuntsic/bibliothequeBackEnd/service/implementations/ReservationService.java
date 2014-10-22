// Fichier ReservationService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IReservationService;

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
     * @param reservationDAO
     *            Le DAO de la table <code>reservation</code>
     * @param pretDAO
     *            Le DAO de la table <code>pret</code>
     * @throws InvalidDAOException
     *             Si le DAO de réservation est <code>null</code>, si le DAO de
     *             membre est <code>null</code>, si le DAO de livre est
     *             <code>null</code> ou si le DAO de prêt est <code>null</code>
     */
    ReservationService(IReservationDAO reservationDAO,
        IPretDAO pretDAO) throws InvalidDAOException {
        super();
        if(reservationDAO == null) {
            throw new InvalidDAOException("Le DAO de réservation ne peut être null");
        }
        if(pretDAO == null) {
            throw new InvalidDAOException("Le DAO de prêt ne peut être null");
        }
        setReservationDAO(reservationDAO);
        setPretDAO(pretDAO);
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
     * @param reservationDAO
     *            La valeur à utiliser pour la variable d'instance
     *            <code>this.reservationDAO</code>
     */
    private void setReservationDAO(IReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.pretDAO</code>.
     * 
     * @return La variable d'instance <code>this.pretDAO</code>
     */
    private IPretDAO getPretDAO() {
        return this.pretDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.pretDAO</code>.
     * 
     * @param pretDAO
     *            La valeur à utiliser pour la variable d'instance
     *            <code>this.pretDAO</code>
     */
    private void setPretDAO(IPretDAO pretDAO) {
        this.pretDAO = pretDAO;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void addReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getReservationDAO().add(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReservationDTO getReservation(Session session,
        String idReservation) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException {
        try {
            return (ReservationDTO) getReservationDAO().get(session,
                idReservation);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getReservationDAO().update(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteReservation(Session session,
        ReservationDTO reservationDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getReservationDAO().delete(session,
                reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReservationDTO> getAllReservations(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<ReservationDTO>) getReservationDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
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
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getReservationDAO().findByMembre(session,
                idMembre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
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
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getReservationDAO().findByLivre(session,
                idLivre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

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
        ServiceException,
        InvalidCriterionValueException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        ReservationDTO uneReservationDTO = getReservation(session,
            reservationDTO.getIdReservation());
        if(uneReservationDTO == null) {
            throw new MissingDTOException("La reservation "
                + reservationDTO.getIdReservation()
                + " n'existe pas");
        }
        MembreDTO unMembreDTO = uneReservationDTO.getMembreDTO();
        if(unMembreDTO == null) {
            throw new MissingDTOException("Le membre "
                + reservationDTO.getMembreDTO().getIdMembre()
                + " n'existe pas");
        }
        LivreDTO unLivreDTO = uneReservationDTO.getLivreDTO();
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + reservationDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }
        Set<PretDTO> prets = unLivreDTO.getPrets();
        if(prets.isEmpty()) {
            for(PretDTO pretDTO : prets) {
                if(pretDTO.getDateRetour() == null) {
                    throw new MissingLoanException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") n'est pas encore prêté");
                }
            }
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
        List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
        if(!reservations.isEmpty()) {
            for(ReservationDTO uneAutreReservationDTO : reservations) {
                if(unLivreDTO.equals(uneAutreReservationDTO.getLivreDTO())) {
                    throw new ExistingReservationException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est déjà réservé pour quelqu'un d'autre");
                }
            }
        }
        reservationDTO.setDateReservation(new Timestamp(System.currentTimeMillis()));
        addReservation(session,
            reservationDTO);
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
        ServiceException,
        InvalidCriterionValueException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        ReservationDTO uneReservationDTO = getReservation(session,
            reservationDTO.getIdReservation());
        if(uneReservationDTO == null) {
            throw new MissingDTOException("La réservation "
                + reservationDTO.getIdReservation()
                + " n'existe pas");
        }
        MembreDTO unMembreDTO = uneReservationDTO.getMembreDTO();
        if(unMembreDTO == null) {
            throw new MissingDTOException("Le membre "
                + uneReservationDTO.getMembreDTO().getIdMembre()
                + " n'existe pas");
        }
        LivreDTO unLivreDTO = uneReservationDTO.getLivreDTO();
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + uneReservationDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }
        List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
        if(!reservations.isEmpty()) {
            uneReservationDTO = reservations.get(0);
            MembreDTO booker = uneReservationDTO.getMembreDTO();
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
        Set<PretDTO> prets = unLivreDTO.getPrets();
        if(!prets.isEmpty()) {
            for(PretDTO pretDTO : prets) {
                if(pretDTO.getDateRetour() == null) {
                    MembreDTO emprunteur = pretDTO.getMembreDTO();
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
        annuler(session,
            uneReservationDTO);
        unMembreDTO.setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) + 1));
        getPretDAO().update(session,
            unMembreDTO);
        PretDTO unPretDTO = new PretDTO();
        unPretDTO.setMembreDTO(unMembreDTO);
        unPretDTO.setLivreDTO(unLivreDTO);
        unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        unPretDTO.setDateRetour(null);
        getPretDAO().add(session,
            unPretDTO);
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
        ServiceException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(reservationDTO == null) {
            throw new InvalidDTOException("La réservation ne peut être null");
        }
        ReservationDTO uneReservationDTO = getReservation(session,
            reservationDTO.getIdReservation());
        if(uneReservationDTO == null) {
            throw new MissingDTOException("La réservation "
                + reservationDTO.getIdReservation()
                + " n'existe pas");
        }
        deleteReservation(session,
            uneReservationDTO);
    }
}
