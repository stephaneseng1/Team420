// Fichier ILivreService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces;

import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
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
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import org.hibernate.Session;

/**
 * Interface de base pour les services.<br />
 * Toutes les interfaces de service devrait en hériter.
 * 
 * @author Gilles Benichou
 */
public interface ILivreService extends IService {
    /**
     * Ajoute un nouveau livre dans la base de données.
     * 
     * @param livreDTO Le livre à ajouter
     * @param session La session a utiliser
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void addLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Lit un livre à partir de la base de données.
     * 
     * @param idLivre L'ID du livre à lire
     * @param session La session a utiliser
     * @return Le livre
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     * @throws InvalidPrimaryKeyException Si la primary key est invalid
     */
    LivreDTO getLivre(Session session,
        String idLivre) throws InvalidHibernateSessionException,
        InvalidPrimaryKeyException,
        ServiceException;

    /**
     * Met à jour un livre dans la base de données.
     * 
     * @param livreDTO Le livre à mettre à jour
     * @param session La session a utiliser
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void updateLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Supprime un livre de la base de données.
     * 
     * @param session La session à utiliser
     * @param livreDTO Le livre à supprimer
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void deleteLivre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Trouve tous les livres de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * livre n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param session La session à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de tous les livres ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<LivreDTO> getAllLivre(Session session,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Trouve les livres à partir d'un titre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun livre
     * n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param session La session à utiliser
     * @param titre Le titre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des livres correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si la propriete a utiliser est <code>null</code>
     * @throws InvalidCriterionValueException Si le titre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    List<LivreDTO> findLivreByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        ServiceException;

    /**
     * Acquiert un livre.
     * 
     * @param session La session à utiliser
     * @param livreDTO Le livre à acquérir
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws ServiceException S'il y a une erreur avec la base de données
     */
    void acquerir(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        ServiceException;

    /**
     * Vend un livre.
     * 
     * @param session La session à utiliser
     * @param livreDTO Le livre à vendre
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidDTOException Si le livre est <code>null</code>
     * @throws InvalidDTOClassException Si la classe du livre n'est pas celle que prend en charge le DAO
     * @throws MissingDTOException Si le livre n'existe pas
     * @throws InvalidCriterionException Si l'ID du membre est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws ExistingLoanException Si le livre a été prêté
     * @throws ExistingReservationException Si le livre a été réservé
     * @throws ServiceException S'il y a une erreur avec la base de données
     * @throws InvalidPrimaryKeyException Si la primary key est invalid
     */

    void vendre(Session session,
        LivreDTO livreDTO) throws InvalidHibernateSessionException,
        InvalidDTOException,
        InvalidDTOClassException,
        MissingDTOException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        InvalidPrimaryKeyException,
        ExistingLoanException,
        ExistingReservationException,
        ServiceException;
}
