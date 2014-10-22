// Fichier ILivreDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-31

package ca.qc.collegeahuntsic.bibliotheque.dao.interfaces;

import java.util.List;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;

/**
 * Interface DAO pour manipuler les livres dans la base de données.
 * 
 * @author Gilles Benichou
 */
public interface ILivreDAO extends IDAO {
    /**
     * Trouve les livres à partir d'un titre. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun livre
     * n'est trouvé, une {@link List} vide est retournée.
     * 
     * @param session La session Hibernate a utiliser
     * @param titre Le titre à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des livres correspondants ; une liste vide sinon
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidCriterionException Si la propriete a utiliser est <code>null</code>
     * @throws InvalidCriterionValueException Si le titre a trouver est <code>null</code>
     * @throws InvalidSortByPropertyException Si la propriété à utiliser pour classer est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */
    List<LivreDTO> findByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException;
}
