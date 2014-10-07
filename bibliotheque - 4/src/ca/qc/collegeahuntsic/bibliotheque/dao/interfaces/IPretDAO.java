
package ca.qc.collegeahuntsic.bibliotheque.dao.interfaces;

import java.sql.Timestamp;
import java.util.List;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;

public interface IPretDAO extends IDAO {

    List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret,
        String sortByPropertyName)throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

    List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName)throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

    List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName)throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

    List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName)throws InvalidHibernateSessionException,
        InvalidCriterionException,
        InvalidSortByPropertyException,
        DAOException;

}
