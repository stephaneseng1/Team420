// Fichier IPretDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-31

package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import org.hibernate.Session;

/**
 * Interface DAO pour manipuler les prêts dans la base de données.
 *
 * @author Gilles Benichou
 */
public interface IPretDAO extends IDAO {
    /**
     * Trouve les prêts non retournés d'un membre. La liste est classée par
     * ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param session
     *            La session à utiliser
     * @param idMembre
     *            L'ID du membre à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du membre recherché est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException
     *             S'il y a une erreur avec la base de données
     * @throws InvalidCriterionValueException
     *             Si l'ID du livre est <code>null</code>
     */
    List<PretDTO> findByMembre(Session session,
        String idMembre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException,
        InvalidCriterionValueException;

    /**
     * Trouve les livres en cours d'emprunt. La liste est classée par ordre
     * croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param session
     *            La session à utiliser
     * @param idLivre
     *            L'ID du livre à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si l'ID du livre recherché est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException
     *             S'il y a une erreur avec la base de données
     * @throws InvalidCriterionValueException
     *             Si l'ID du livre est <code>null</code>
     */
    List<PretDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException,
        InvalidCriterionValueException;

    /**
     * Trouve les prêts à partir d'une date de prêt. La liste est classée par
     * ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param session
     *            La session à utiliser
     * @param datePret
     *            La date de prêt à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si la date de prêt recherché est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException
     *             S'il y a une erreur avec la base de données
     * @throws InvalidCriterionValueException
     *             Si l'ID du livre est <code>null</code>
     */
    List<PretDTO> findByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException,
        InvalidCriterionValueException;

    /**
     * Trouve les prêts à partir d'une date de retour. La liste est classée par
     * ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt n'est
     * trouvé, une {@link List} vide est retournée.
     * 
     * @param session
     *            La session à utiliser
     * @param dateRetour
     *            La date de retour à trouver
     * @param sortByPropertyName
     *            The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException
     *             Si la session est <code>null</code>
     * @throws InvalidCriterionException
     *             Si la propriete a utiliser est <code>null</code>
     * @throws InvalidCriterionValueException
     *             Si la date de retour est <code>null</code>
     * @throws InvalidSortByPropertyException
     *             Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException
     *             S'il y a une erreur avec la base de données
     */
    List<PretDTO> findByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException,
        InvalidCriterionValueException;

}
