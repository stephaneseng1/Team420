// Fichier MembreService.java
// Auteur : Gilles BÃ©nichou
// Date de crÃ©ation : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.service;

import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ServiceException;

/**
 * Service de la table <code>membre</code>.
 * 
 * @author Gilles Benichou
 */
public class MembreService extends Service {
    private static final long serialVersionUID = 1L;

    private MembreDAO membreDAO;

    private LivreDAO livreDAO;

    private ReservationDAO reservationDAO;

    /**
     * CrÃ©e le service de la table <code>membre</code>.
     * 
     * @param membreDAO Le DAO de la table <code>membre</code>
     * @param livreDAO Le DAO de la table <code>livre</code>
     * @param reservationDAO Le DAO de la table <code>reservation</code>
     */
    public MembreService(MembreDAO membreDAO,
        LivreDAO livreDAO,
        ReservationDAO reservationDAO) {
        super();
        setMembreDAO(membreDAO);
        setLivreDAO(livreDAO);
        setReservationDAO(reservationDAO);
    }

    // Region Getters and Setters
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

    // EndRegion Getters and Setters

    /**
     * Ajoute un nouveau membre.
     * 
     * @param membreDTO Le membre Ã  ajouter
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public void add(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().add(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Lit un membre.
     * 
     * @param idMembre L'ID du membre Ã  lire
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public MembreDTO read(int idMembre) throws ServiceException {
        try {
            return getMembreDAO().read(idMembre);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Met Ã  jour un membre.
     * 
     * @param membreDTO Le membre Ã  mettre Ã  jour
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public void update(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().update(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Supprime un membre.
     * 
     * @param membreDTO Le membre Ã  supprimer
     * @throws ServiceException Si le membre a encore des prÃªts, s'il a des rÃ©servations ou s'il y a une erreur avec la base de donnÃ©es
     */
    public void delete(MembreDTO membreDTO) throws ServiceException {
        try {
            getMembreDAO().delete(membreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Trouve tous les membres.
     * 
     * @return La liste des membres ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur avec la base de donnÃ©es
     */
    public List<MembreDTO> getAll() throws ServiceException {
        try {
            return getMembreDAO().getAll();
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Inscrit un membre.
     * 
     * @param membreDTO Le membre Ã  ajouter
     * @throws ServiceException Si le membre existe dÃ©jÃ  ou s'il y a une erreur avec la base de donnÃ©es
     */
    public void inscrire(MembreDTO membreDTO) throws ServiceException {
        if(read(membreDTO.getIdMembre()) != null) {
            throw new ServiceException("Le membre "
                + membreDTO.getIdMembre()
                + " existe dÃ©jÃ ");
        }
        add(membreDTO);
    }

    /**
     * Emprunte un livre.
     * 
     * @param membreDTO Le membre qui emprunte
     * @param livreDTO Le livre Ã  emprunter
     * @throws ServiceException Si le membre n'existe pas, si le livre n'existe pas, si le livre a Ã©tÃ© prÃªtÃ©, si le livre a Ã©tÃ© rÃ©servÃ©, si le
     *         membre a atteint sa limite de prÃªt ou s'il y a une erreur avec la base de donnÃ©es
     */
    public void emprunter(MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = read(membreDTO.getIdMembre());
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
            MembreDTO emprunteur = read(unLivreDTO.getIdMembre());
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
            if(!getReservationDAO().findByLivre(unLivreDTO).isEmpty()) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des rÃ©servations");
            }
            // ProblÃ¨me de la date de prÃªt
            // On voit Ã©galement le manque de la table prÃªt simulÃ©e en ce moment par les deux tables
            unLivreDTO.setIdMembre(unMembreDTO.getIdMembre());
            getLivreDAO().emprunter(unLivreDTO);
            getMembreDAO().emprunter(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Renouvelle le prÃªt d'un livre.
     * 
     * @param membreDTO Le membre qui renouvelle
     * @param livreDTO Le livre Ã  renouveler
     * @throws ServiceException Si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore Ã©tÃ© prÃªtÃ©, si le livre a Ã©tÃ©
     *         prÃªtÃ© Ã  quelqu'un d'autre, si le livre a Ã©tÃ© rÃ©servÃ© ou s'il y a une erreur avec la base de donnÃ©es
     */
    public void renouveler(MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = read(membreDTO.getIdMembre());
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
            MembreDTO emprunteur = read(unLivreDTO.getIdMembre());
            if(emprunteur == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prÃªtÃ©");
            }
            if(unMembreDTO.getIdMembre() != emprunteur.getIdMembre()) {
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
            if(!getReservationDAO().findByLivre(unLivreDTO).isEmpty()) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") a des rÃ©servations");
            }

            // Cas Ã©liminÃ© en utilisant la date de prÃªt comme Ã©tant la date systÃ¨me de la base de donnÃ©es

            /* Verifier si date renouvellement >= datePret */
            //			if(Date.valueOf(datePret).before(tupleLivre.getDatePret())) {
            //				throw new BibliothequeException("Date de renouvellement infÃ©rieure Ã  la date de prÃªt");
            //			}

            getLivreDAO().emprunter(unLivreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * Retourne un livre.
     * 
     * @param membreDTO Le membre qui retourne
     * @param livreDTO Le livre Ã  retourner
     * @throws ServiceException Si le membre n'existe pas, si le livre n'existe pas, si le livre n'a pas encore Ã©tÃ© prÃªtÃ©, si le livre a Ã©tÃ©
     *         prÃªtÃ© Ã  quelqu'un d'autre ou s'il y a une erreur avec la base de donnÃ©es
     */
    public void retourner(MembreDTO membreDTO,
        LivreDTO livreDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = read(membreDTO.getIdMembre());
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
            MembreDTO emprunteur = read(unLivreDTO.getIdMembre());
            if(emprunteur == null) {
                throw new ServiceException("Le livre "
                    + unLivreDTO.getTitre()
                    + " (ID de livre : "
                    + unLivreDTO.getIdLivre()
                    + ") n'est pas encore prÃªtÃ©");
            }
            if(unMembreDTO.getIdMembre() != emprunteur.getIdMembre()) {
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

            // Cas Ã©liminÃ© en utilisant la date de prÃªt comme Ã©tant la date systÃ¨me de la base de donnÃ©es

            /* Verifier si date retour >= datePret */
            //			if(Date.valueOf(dateRetour).before(tupleLivre.getDatePret())) {
            //				throw new BibliothequeException("Date de retour infÃ©rieure Ã  la date de prÃªt");
            //			}

            // On voit le manque de la table prÃªt simulÃ©e en ce moment par les deux tables
            getLivreDAO().retourner(unLivreDTO);
            getMembreDAO().retourner(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }

    /**
     * DÃ©sincrit un membre.
     * 
     * @param membreDTO Le membre Ã  dÃ©sinscrire
     * @throws ServiceException Si le membre n'existe pas, si le membre a encore des prÃªts, s'il a des rÃ©servations ou s'il y a une erreur avec
     *         la base de donnÃ©es
     */
    public void desincrire(MembreDTO membreDTO) throws ServiceException {
        try {
            MembreDTO unMembreDTO = read(membreDTO.getIdMembre());
            if(unMembreDTO == null) {
                throw new ServiceException("Le membre "
                    + membreDTO.getIdMembre()
                    + " n'existe pas");
            }
            if(unMembreDTO.getNbPret() > 0) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a encore des prÃªts");
            }
            if(!getReservationDAO().findByMembre(unMembreDTO).isEmpty()) {
                throw new ServiceException("Le membre "
                    + unMembreDTO.getNom()
                    + " (ID de membre : "
                    + unMembreDTO.getIdMembre()
                    + ") a des rÃ©servations");
            }
            delete(unMembreDTO);
        } catch(DAOException daoException) {
            throw new ServiceException(daoException);
        }
    }
}
