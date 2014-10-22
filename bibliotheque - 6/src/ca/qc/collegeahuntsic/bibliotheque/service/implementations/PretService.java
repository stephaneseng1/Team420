// Fichier PretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
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
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IPretService;

/**
 * Service de la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public class PretService extends Service implements IPretService {
    private IPretDAO pretDAO;

    /**
     * Crée le service de la table <code>pret</code>.
     * 
     * @param pretDAO
     *            Le DAO de la table <code>pret</code>
     * @param membreDAO
     *            Le DAO de la table <code>membre</code>
     * @param livreDAO
     *            Le DAO de la table <code>livre</code>
     * @param reservationDAO
     *            Le DAO de la table <code>reservation</code>
     * @throws InvalidDAOException
     *             Si le DAO de prêt est <code>null</code>, si le DAO de membre
     *             est <code>null</code>, si le DAO de livre est
     *             <code>null</code> ou si le DAO de réservation est
     *             <code>null</code>
     */
    PretService(IPretDAO pretDAO) throws InvalidDAOException {
        super();
        if(pretDAO == null) {
            throw new InvalidDAOException("Le DAO de prêt ne peut être null");
        }
        setPretDAO(pretDAO);
    }

    // Region Getters and Setters
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
    public void addPret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().add(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public PretDTO getPret(Session session,
        String idPret) throws InvalidHibernateSessionException,
        ServiceException,
        InvalidPrimaryKeyException {
        try {
            return (PretDTO) getPretDAO().get(session,
                idPret);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().update(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePret(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException {
        try {
            getPretDAO().delete(session,
                pretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> getAllPrets(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException {
        try {
            return (List<PretDTO>) getPretDAO().getAll(session,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<PretDTO> findByMembre(Session session,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getPretDAO().findByMembre(session,
                idMembre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<PretDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getPretDAO().findByLivre(session,
                idLivre,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<PretDTO> findByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getPretDAO().findByDatePret(session,
                datePret,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<PretDTO> findByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ServiceException,
        InvalidCriterionValueException {
        try {
            return getPretDAO().findByDateRetour(session,
                dateRetour,
                sortByPropertyName);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws DAOException
     * 
     */
    @Override
    public void commencer(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        ExistingLoanException,
        InvalidLoanLimitException,
        ExistingReservationException,
        InvalidDTOClassException,
        ServiceException,
        InvalidCriterionValueException,
        DAOException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        PretDTO unPretDTO = getPret(session,
            pretDTO.getIdPret());

        MembreDTO unMembreDTO = unPretDTO.getMembreDTO();
        if(unMembreDTO == null) {
            throw new MissingDTOException("Le membre "
                + pretDTO.getMembreDTO().getIdMembre()
                + " n'existe pas");
        }

        LivreDTO unLivreDTO = unPretDTO.getLivreDTO();
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + pretDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }

        Set<PretDTO> prets = unLivreDTO.getPrets();
        if(!prets.isEmpty()) {
            for(PretDTO pret : prets) {
                if(pret.getDateRetour() == null) {
                    MembreDTO emprunteur = pret.getMembreDTO();

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
        List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
        if(!reservations.isEmpty()) {
            ReservationDTO reservationDTO = reservations.get(0);
            MembreDTO booker = reservationDTO.getMembreDTO();
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
        pretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) + 1));
        pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        pretDTO.setDateRetour(null);
        addPret(session,
            pretDTO);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void renouveler(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
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
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        PretDTO unPretDTO = getPret(session,
            pretDTO.getIdPret());
        if(unPretDTO == null) {
            throw new MissingDTOException("Le prêt "
                + pretDTO.getIdPret()
                + " n'existe pas");
        }
        MembreDTO unMembreDTO = unPretDTO.getMembreDTO();
        if(unMembreDTO == null) {
            throw new MissingDTOException("Le membre "
                + unPretDTO.getMembreDTO().getIdMembre()
                + " n'existe pas");
        }
        LivreDTO unLivreDTO = unPretDTO.getLivreDTO();
        if(unLivreDTO == null) {
            throw new MissingDTOException("Le livre "
                + unPretDTO.getLivreDTO().getIdLivre()
                + " n'existe pas");
        }

        Set<PretDTO> prets = unLivreDTO.getPrets();
        if(prets.isEmpty()) {
            throw new MissingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'est pas encore prêté");
        }
        boolean aEteEmprunteParMembre = false;
        for(PretDTO unAutrePretDTO : prets) {
            aEteEmprunteParMembre = unMembreDTO.equals(unAutrePretDTO.getMembreDTO());
        }
        if(!aEteEmprunteParMembre) {
            throw new ExistingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'a pas été prêté à "
                + unMembreDTO.getNom()
                + " (ID de membre : "
                + unMembreDTO.getIdMembre()
                + ")");
        }
        List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
        if(!reservations.isEmpty()) {
            ReservationDTO reservationDTO = reservations.get(0);
            MembreDTO booker = reservationDTO.getMembreDTO();
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
        unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        unPretDTO.setDateRetour(null);
        updatePret(session,
            unPretDTO);
    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public void terminer(Session session,
        PretDTO pretDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidPrimaryKeyException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        MissingLoanException,
        ExistingLoanException,
        InvalidDTOClassException,
        ServiceException,
        InvalidCriterionValueException {
        if(session == null) {
            throw new InvalidHibernateSessionException("La session ne peut être null");
        }
        if(pretDTO == null) {
            throw new InvalidDTOException("Le prêt ne peut être null");
        }
        try {
            PretDTO unPretDTO = getPret(session,
                pretDTO.getIdPret());
            if(unPretDTO == null) {
                throw new MissingDTOException("Le prêt "
                    + pretDTO.getIdPret()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = unPretDTO.getMembreDTO();
            if(unMembreDTO == null) {
                throw new MissingDTOException("Le membre "
                    + unPretDTO.getMembreDTO().getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = unPretDTO.getLivreDTO();
            if(unLivreDTO == null) {
                throw new MissingDTOException("Le livre "
                    + unPretDTO.getLivreDTO().getIdLivre()
                    + " n'existe pas");
            }
            List<PretDTO> prets = findByMembre(session,
                unMembreDTO.getIdMembre(),
                PretDTO.DATE_PRET_COLUMN_NAME);
            if(prets.isEmpty()) {
                throw new MissingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prêté");
            }
            boolean aEteEmprunteParMembre = false;
            for(PretDTO unAutrePretDTO : prets) {
                aEteEmprunteParMembre = unMembreDTO.equals(unAutrePretDTO.getMembreDTO());
            }
            if(!aEteEmprunteParMembre) {
                throw new ExistingLoanException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'a pas été prêté à "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ")");
            }
            unMembreDTO.setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) - 1));
            getPretDAO().update(session,
                unMembreDTO);
            unPretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));
            updatePret(session,
                unPretDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

}
