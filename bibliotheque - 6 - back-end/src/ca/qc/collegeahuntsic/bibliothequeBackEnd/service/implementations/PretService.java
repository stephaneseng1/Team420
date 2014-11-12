// Fichier PretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.implementations;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.IPretDAO;
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
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IPretService;
import org.hibernate.Session;

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
     * @param pretDAO Le DAO de la table <code>pret</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    PretService(IPretDAO pretDAO) throws ServiceException {
        super();
        if(pretDAO == null) {
            throw new ServiceException(new InvalidDAOException("Le DAO de prêt ne peut être null"));
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
        PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().add(session,
                pretDTO);
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
    public PretDTO get(Session session,
        String idPret) throws ServiceException {
        try {
            return (PretDTO) getPretDAO().get(session,
                idPret);
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
        PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().update(session,
                pretDTO);
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
        PretDTO pretDTO) throws ServiceException {
        try {
            getPretDAO().delete(session,
                pretDTO);
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
    public List<PretDTO> getAll(Session session,
        String sortByPropertyName) throws ServiceException {
        try {
            return (List<PretDTO>) getPretDAO().getAll(session,
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
    public List<PretDTO> findByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws ServiceException {
        try {
            return getPretDAO().findByDatePret(session,
                datePret,
                sortByPropertyName);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidCriterionException
            | InvalidSortByPropertyException
            | InvalidCriterionValueException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws ServiceException {
        try {
            return getPretDAO().findByDateRetour(session,
                dateRetour,
                sortByPropertyName);
        } catch(
            DAOException
            | InvalidHibernateSessionException
            | InvalidCriterionException
            | InvalidSortByPropertyException
            | InvalidCriterionValueException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void commencer(Session session,
        PretDTO pretDTO) throws ServiceException {
        if(session == null) {
            throw new ServiceException(new InvalidHibernateSessionException("La session ne peut être null"));
        }
        if(pretDTO == null) {
            throw new ServiceException(new InvalidDTOException("Le prêt ne peut être null"));
        }
        try {
            final MembreDTO unMembreDTO = pretDTO.getMembreDTO();
            final LivreDTO unLivreDTO = pretDTO.getLivreDTO();

            final List<PretDTO> prets = new ArrayList<>(unLivreDTO.getPrets());
            for(PretDTO unPretDTO : prets) {
                if(unPretDTO.getDateRetour() == null) {
                    final MembreDTO emprunteur = unPretDTO.getMembreDTO();
                    throw new ServiceException(new ExistingLoanException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") a été prêté à "
                        + emprunteur.getNom()
                        + " (ID de membre : "
                        + emprunteur.getIdMembre()
                        + ")"));
                }
            }
            if(unMembreDTO.getNbPret().equals(unMembreDTO.getLimitePret())) {
                throw new ServiceException(new InvalidLoanLimitException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prêt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)"));
            }

            final List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
            if(!reservations.isEmpty()) {
                final ReservationDTO uneReservationDTO = reservations.get(0);
                final MembreDTO booker = uneReservationDTO.getMembreDTO();
                throw new ServiceException(new ExistingReservationException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") est réservé pour "
                    + booker.getNom()
                    + " (ID de membre : "
                    + booker.getIdMembre()
                    + ")"));
            }

            pretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) + 1));
            pretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
            pretDTO.setDateRetour(null);
            getPretDAO().save(session,
                pretDTO);
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
    public void renouveler(Session session,
        PretDTO pretDTO) throws ServiceException {
        if(session == null) {
            throw new ServiceException(new InvalidHibernateSessionException("La session ne peut être null"));
        }
        if(pretDTO == null) {
            throw new ServiceException(new InvalidDTOException("Le prêt ne peut être null"));
        }
        final PretDTO unPretDTO = get(session,
            pretDTO.getIdPret());
        if(unPretDTO == null) {
            throw new ServiceException(new MissingDTOException("Le prêt "
                + pretDTO.getIdPret()
                + " n'existe pas"));
        }
        final MembreDTO unMembreDTO = unPretDTO.getMembreDTO();
        final LivreDTO unLivreDTO = unPretDTO.getLivreDTO();
        final Set<PretDTO> prets = unLivreDTO.getPrets();
        if(prets.isEmpty()) {
            throw new ServiceException(new MissingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'est pas encore prêté"));
        }
        boolean aEteEmprunteParMembre = false;
        for(PretDTO unAutrePretDTO : prets) {
            aEteEmprunteParMembre = unMembreDTO.equals(unAutrePretDTO.getMembreDTO());
        }
        if(!aEteEmprunteParMembre) {
            throw new ServiceException(new ExistingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'a pas été prêté à "
                + unMembreDTO.getNom()
                + " (ID de membre : "
                + unMembreDTO.getIdMembre()
                + ")"));
        }
        final List<ReservationDTO> reservations = new ArrayList<>(unLivreDTO.getReservations());
        if(!reservations.isEmpty()) {
            final ReservationDTO uneReservationDTO = reservations.get(0);
            final MembreDTO booker = uneReservationDTO.getMembreDTO();
            throw new ServiceException(new ExistingReservationException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") est réservé pour "
                + booker.getNom()
                + " (ID de membre : "
                + booker.getIdMembre()
                + ")"));
        }
        unPretDTO.setDatePret(new Timestamp(System.currentTimeMillis()));
        unPretDTO.setDateRetour(null);
        update(session,
            unPretDTO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminer(Session session,
        PretDTO pretDTO) throws ServiceException {
        if(session == null) {
            throw new ServiceException(new InvalidHibernateSessionException("La connexion ne peut être null"));
        }
        if(pretDTO == null) {
            throw new ServiceException(new InvalidDTOException("Le prêt ne peut être null"));
        }
        final PretDTO unPretDTO = get(session,
            pretDTO.getIdPret());
        if(unPretDTO == null) {
            throw new ServiceException(new MissingDTOException("Le prêt "
                + pretDTO.getIdPret()
                + " n'existe pas"));
        }
        if(unPretDTO.getDateRetour() != null) {
            throw new ServiceException(new MissingLoanException("Le pret "
                + pretDTO.getIdPret()
                + " est déjà retourné"));
        }

        final MembreDTO unMembreDTO = unPretDTO.getMembreDTO();
        final LivreDTO unLivreDTO = unPretDTO.getLivreDTO();

        final Set<PretDTO> prets = unMembreDTO.getPrets();
        if(prets.isEmpty()) {
            throw new ServiceException(new MissingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'est pas encore prêté"));
        }
        boolean aEteEmprunteParMembre = false;
        for(PretDTO unAutrePretDTO : prets) {
            aEteEmprunteParMembre = unMembreDTO.equals(unAutrePretDTO.getMembreDTO());
        }
        if(!aEteEmprunteParMembre) {
            throw new ServiceException(new ExistingLoanException("Le livre "
                + unLivreDTO.getTitre()
                + " (ID de livre : "
                + unLivreDTO.getIdLivre()
                + ") n'a pas été prêté à "
                + unMembreDTO.getNom()
                + " (ID de membre : "
                + unMembreDTO.getIdMembre()
                + ")"));
        }
        unPretDTO.getMembreDTO().setNbPret(Integer.toString(Integer.parseInt(unMembreDTO.getNbPret()) - 1));
        unPretDTO.setDateRetour(new Timestamp(System.currentTimeMillis()));
        update(session,
            unPretDTO);
    }
}
