// Fichier PretDAO.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-26

package ca.qc.collegeahuntsic.bibliotheque.dao.implementations;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;

/**
 * DAO pour effectuer des CRUDs avec la table <code>pret</code>.
 * 
 * @author Gilles Benichou
 */
public class PretDAO extends DAO implements IPretDAO {
    /**
     * Crée le DAO de la table <code>pret</code>.
     * 
     * @param pretDTOClass The classe de prêt DTO to use
     * @throws InvalidDTOClassException Si la classe de DTO est <code>null</code>
     */
    public PretDAO(Class<PretDTO> pretDTOClass) throws InvalidDTOClassException { // TODO: Change to package when switching to Spring
        super(pretDTOClass);
    }

    /**
     * Crée une nouvelle clef primaire pour la table <code>pret</code>.
     * 
     * @param connexion La connexion à utiliser
     * @return La nouvelle clef primaire
     * @throws InvalidHibernateSessionException Si la connexion est <code>null</code>
     * @throws InvalidPrimaryKeyRequestException Si la requête de la clef primaire du livre est <code>null</code>
     * @throws DAOException S'il y a une erreur avec la base de données
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PretDTO> findByMembre(Session session,
            String idMembre,
            String sortByPropertyName) throws InvalidHibernateSessionException,
            InvalidCriterionException,
            InvalidSortByPropertyException,
            DAOException, InvalidCriterionValueException {
        	
        	return (List<PretDTO>) super.find(session, 
        			MembreDTO.NOM_COLUMN_NAME, 
        			idMembre, 
        			sortByPropertyName);
        }

    /**
     * {@inheritDoc}
     * @throws InvalidCriterionValueException 
     */
    @Override
    public List<PretDTO> findByLivre(Session session,
        String idLivre,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException, InvalidCriterionValueException {
    	
    	return (List<PretDTO>) super.find(session, 
    			MembreDTO.NOM_COLUMN_NAME, 
    			idLivre, 
    			sortByPropertyName);
    	
    }

    /**
     * {@inheritDoc}
     * @throws InvalidCriterionValueException 
     */
    @Override
    public List<PretDTO> findByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException, InvalidCriterionValueException {
    	
    	return (List<PretDTO>) super.find(session, 
    			MembreDTO.NOM_COLUMN_NAME, 
    			datePret, 
    			sortByPropertyName);
    }

    /**
     * {@inheritDoc}
     * @throws InvalidCriterionValueException 
     */
    @Override
    public List<PretDTO> findByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException, InvalidCriterionValueException {
    	
    	return (List<PretDTO>) super.find(session, 
    			MembreDTO.NOM_COLUMN_NAME, 
    			dateRetour, 
    			sortByPropertyName);
    }
}
