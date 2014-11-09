// Fichier LivreDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.implementations;

import java.util.List;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>livre</code>.
 *
 * @author Gilles Benichou
 */
public class LivreDAO extends DAO implements ILivreDAO {

    /**
     * Crée le DAO de la table <code>livre</code>.
     *
     * @param livreDTOClass
     *            The classe de livre DTO to use
     * @throws InvalidDTOClassException
     *             Si la classe de DTO est <code>null</code>
     */
    LivreDAO(Class<LivreDTO> livreDTOClass) throws InvalidDTOClassException {
        super(livreDTOClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LivreDTO> findByTitre(Session session,
        String titre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidCriterionValueException,
        InvalidSortByPropertyException,
        DAOException {
        return (List<LivreDTO>) super.find(session,
            LivreDTO.TITRE_COLUMN_NAME,
            titre,
            sortByPropertyName);
    }
}
