// Fichier ReservationService.java
// Auteur : Gilles BÃ©nichou
// Date de crÃ©ation : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

/**
 * Service de la table <code>reservation</code>.
 * 
 * @author Gilles Benichou
 */
public class ReservationService extends Service {
    private static final long serialVersionUID = 1L;

    private ReservationDAO reservationDAO;

    private LivreDAO livreDAO;

    private MembreDAO membreDAO;

    /**
     * CrÃ©e le service de la table <code>reservation</code>.
     * 
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param livreDAO Le DAO de la table <code>livre</code>
     */
    public ReservationService(ReservationDAO reservationDAO,
        LivreDAO livreDAO,
        MembreDAO membreDAO) {
        super();
        setReservationDAO(reservationDAO);
        setMembreDAO(membreDAO);
        setLivreDAO(livreDAO);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @return La variable d'instance <code>this.reservationDAO</code>
     */
    private ReservationDAO getReservationDAO() {
        return this.reservationDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationDAO</code>.
     *
     * @param reservationDAO La valeur Ã  utiliser pour la variable d'instance <code>this.reservationDAO</code>
     */
    private void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @return La variable d'instance <code>this.livreDAO</code>
     */
    private LivreDAO getLivreDAO() {
        return this.livreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.livreDAO</code>.
     *
     * @param livreDAO La valeur Ã  utiliser pour la variable d'instance <code>this.livreDAO</code>
     */
    private void setLivreDAO(LivreDAO livreDAO) {
        this.livreDAO = livreDAO;
    }

    /**
     * Getter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @return La variable d'instance <code>this.membreDAO</code>
     */
    private MembreDAO getMembreDAO() {
        return this.membreDAO;
    }

    /**
     * Setter de la variable d'instance <code>this.membreDAO</code>.
     *
     * @param membreDAO La valeur Ã  utiliser pour la variable d'instance <code>this.membreDAO</code>
     */
    private void setMembreDAO(MembreDAO membreDAO) {
        this.membreDAO = membreDAO;
    }

    // EndRegion Getters and Setters

    /**
     * Ajoute une nouvelle rÃ©servation.
     * 
     * @param reservationDTO La rÃ©servation Ã  ajouter
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public void add(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().add(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Lit une rÃ©servation.
     * 
     * @param idReservation L'ID de la rÃ©servation Ã  lire
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public ReservationDTO read(int idReservation) throws ServiceException {
        try {
            return getReservationDAO().read(idReservation);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Met Ã  jour une rÃ©servation.
     * 
     * @param reservationDTO La rÃ©servation Ã  mettre Ã  jour
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public void update(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().update(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Supprime une rÃ©servation.
     * 
     * @param reservationDTO La rÃ©servation Ã  supprimer
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public void delete(ReservationDTO reservationDTO) throws ServiceException {
        try {
            getReservationDAO().delete(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve toutes les rÃ©servations.
     * 
     * @return La liste des rÃ©servations ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public List<ReservationDTO> getAll() throws ServiceException {
        try {
            return getReservationDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les rÃ©servations Ã  partir d'un livre.
     * 
     * @param livreDTO Le livre Ã  utiliser
     * @return La liste des rÃ©servations correspondantes, triÃ©e par date de rÃ©servation croissante ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public List<ReservationDTO> findByLivre(LivreDTO livreDTO) throws ServiceException {
        try {
            return getReservationDAO().findByLivre(livreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve les rÃ©servations Ã  partir d'un membre.
     * 
     * @param membreDTO Le membre Ã  utiliser
     * @return La liste des rÃ©servations correspondantes ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public List<ReservationDTO> findByMembre(MembreDTO membreDTO) throws ServiceException {
        try {
            return getReservationDAO().findByMembre(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * RÃ©serve un livre.
     * 
     * @param reservationDTO La rÃ©servation Ã  crÃ©er
     * @param membreDTO Le membre qui rÃ©serve
     * @param livreDTO Le livre Ã  rÃ©server
     * @throws ServiceException Si la rÃ©servation existe dÃ©jÃ , si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore
     *         Ã©tÃ© prÃªtÃ©, si le livre est dÃ©jÃ  prÃªtÃ© au membre, si le membre a dÃ©jÃ  rÃ©servÃ© ce livre ou s'il y a une erreur avec la base de
     *         donnÃ©es
     */
    public void reserver(ReservationDTO reservationDTO,
        MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
            ReservationDTO uneReservationDTO = read(reservationDTO.getIdReservation());
            if(uneReservationDTO != null) {
                throw new ServiceException("La rÃ©servation "
                    + reservationDTO.getIdReservation()
                    + " existe dÃ©jÃ ");
            }
            MembreDTO unMembreDTO = getMembreDAO().read(membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(livreDTO.getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }
            MembreDTO emprunteur = getMembreDAO().read(unLivreDTO.getIdMembre());
            if(emprunteur == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prÃªtÃ©");
            }
            if(unMembreDTO.getIdMembre() == emprunteur.getIdMembre()) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") est dÃ©jÃ  prÃªtÃ© Ã  "
                    + emprunteur.getNom()
                    + " (ID de membre : "
                    + emprunteur.getIdMembre()
                    + ")");
            }

            // Cas Ã©liminÃ© en utilisant la date de rÃ©servation comme Ã©tant la date systÃ¨me de la base de donnÃ©es

            /* Verifier si date reservation >= datePret */
            //			if(Date.valueOf(dateReservation).before(tupleLivre.getDatePret())) {
            //				throw new BibliothequeException("Date de rÃ©servation infÃ©rieure Ã  la date de prÃªt");
            //			}

            List<ReservationDTO> reservations = getReservationDAO().findByMembre(unMembreDTO);
            for(ReservationDTO uneAutreReservationDTO : reservations) {
                if(uneAutreReservationDTO.getIdLivre() == unLivreDTO.getIdLivre()) {
                    throw new ServiceException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est dÃ©jÃ  rÃ©servÃ© Ã  "
                        + emprunteur.getNom()
                        + " (ID de membre : "
                        + emprunteur.getIdMembre()
                        + ")");
                }
            }
            add(reservationDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Utilise une rÃ©servation.
     * 
     * @param reservationDTO La rÃ©servation Ã  utiliser
     * @param membreDTO Le membre qui utilise sa rÃ©servation
     * @param livreDTO Le livre Ã  emprunter
     * @throws ServiceException Si la rÃ©servation n'existe pas, si le membre n'existe pas, si le livre n'existe pas, si la rÃ©servation n'est pas
     *         la premiÃ¨re de la liste, si le livre est dÃ©jÃ  prÃ©tÃ©, si le membre a atteint sa limite de prÃªt ou s'il y a une erreur avec la base
     *         de donnÃ©es
     */
    public void utiliser(ReservationDTO reservationDTO,
        MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
            ReservationDTO uneReservationDTO = read(reservationDTO.getIdReservation());
            if(uneReservationDTO == null) {
                throw new ServiceException("La rÃ©servation "
                    + reservationDTO.getIdReservation()
                    + " n'existe pas");
            }
            MembreDTO unMembreDTO = getMembreDAO().read(membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            LivreDTO unLivreDTO = getLivreDAO().read(livreDTO.getIdLivre());
            if(unLivreDTO == null) {
                throw new ServiceException("Le livre "
                    + livreDTO.getIdLivre()
                    + " n'existe pas");
            }
            List<ReservationDTO> reservations = getReservationDAO().findByLivre(unLivreDTO);
            if(!reservations.isEmpty()) {
                uneReservationDTO = reservations.get(0);
                if(uneReservationDTO.getIdMembre() != unMembreDTO.getIdMembre()) {
                    MembreDTO booker = getMembreDAO().read(uneReservationDTO.getIdMembre());
                    throw new ServiceException("Le livre "
                        + unLivreDTO.getTitre()
                        + " (ID de livre : "
                        + unLivreDTO.getIdLivre()
                        + ") est rÃ©servÃ© pour "
                        + booker.getNom()
                        + " (ID de membre : "
                        + booker.getIdMembre()
                        + ")");
                }
            }
            MembreDTO emprunteur = getMembreDAO().read(livreDTO.getIdMembre());
            if(emprunteur != null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a Ã©tÃ© prÃªtÃ© Ã  "
                    + emprunteur.getNom()
                    + " (ID de membre : "
                    + emprunteur.getIdMembre()
                    + ")");
            }
            if(unMembreDTO.getNbPret() == unMembreDTO.getLimitePret()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a atteint sa limite de prÃªt ("
                    + unMembreDTO.getLimitePret()
                    + " emprunt(s) maximum)");
            }

            // Cas Ã©liminÃ© en utilisant la date de prÃªt et de rÃ©servation comme Ã©tant la date systÃ¨me de la base de donnÃ©es

            /* Verifier si datePret >= tupleReservation.dateReservation */
            //			if(Date.valueOf(datePret).before(tupleReservation.getDateReservation())) {
            //				throw new BibliothequeException("Date de prÃªt infÃ©rieure Ã  la date de rÃ©servation");
            //			}

            annuler(uneReservationDTO);
            // On voit le manque de la table prÃªt simulÃ©e en ce moment par les deux tables
            unLivreDTO.setIdMembre(unMembreDTO.getIdMembre());
            getLivreDAO().emprunter(unLivreDTO);
            getMembreDAO().emprunter(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Annule une rÃ©servation.
     * 
     * @param reservationDTO Le reservation Ã  annuler
     * @throws ServiceException Si la rÃ©servation n'existe pas ou s'il y a une erreur avec la base de donnÃ©es
     */
    public void annuler(ReservationDTO reservationDTO) throws ServiceException {
        ReservationDTO uneReservationDTO = read(reservationDTO.getIdReservation());
        if(uneReservationDTO == null) {
            throw new ServiceException("La rÃ©servation "
                + reservationDTO.getIdLivre()
                + " n'existe pas");
        }
        delete(uneReservationDTO);
    }
}
