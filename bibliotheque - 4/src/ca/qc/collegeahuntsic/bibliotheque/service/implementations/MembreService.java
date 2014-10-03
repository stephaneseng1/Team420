package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

//Fichier LivreService.java
//Auteur : Gilles Bénichou
//Date de création : 2014-08-24



import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.ILivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IMembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IPretDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.interfaces.IReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidDAOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IMembreService;

/**
* Service de la table <code>livre</code>.
* 
* @author Gilles Benichou
*/
public class MembreService extends Service implements IMembreService {
	
	
	//?
 private ILivreDAO livreDAO;

 private IMembreDAO membreDAO;

 private IPretDAO pretDAO;

 private IReservationDAO reservationDAO;
 //??
 
 
 /**
  * Crée le service de la table <code>livre</code>.
  * 
  * @param livreDAO Le DAO de la table <code>livre</code>
  * @param membreDAO Le DAO de la table <code>membre</code>
  * @param pretDAO Le DAO de la table <code>pret</code>
  * @param reservationDAO Le DAO de la table <code>reservation</code>
  * @throws InvalidDAOException Si le DAO de livre est <code>null</code>, si le DAO de membre est <code>null</code>, si le DAO de prêt est
  *         <code>null</code> ou si le DAO de réservation est <code>null</code>
  */
 //livre,membre,pret,reservation
 public MembreService(IMembreDAO membreDAO,// TODO: Change to package when switching to Spring
     ILivreDAO livreDAO,
     IPretDAO pretDAO,
     IReservationDAO reservationDAO) throws InvalidDAOException {
     super();
     
     if(membreDAO == null) {
         throw new InvalidDAOException("Le DAO de membre ne peut être null");
     }
     if(livreDAO == null) {
         throw new InvalidDAOException("Le DAO de livre ne peut être null");
     }
     if(pretDAO == null) {
         throw new InvalidDAOException("Le DAO de prêt ne peut être null");
     }
     if(reservationDAO == null) {
         throw new InvalidDAOException("Le DAO de réservation ne peut être null");
     }
     setMembreDAO(membreDAO);
     setLivreDAO(livreDAO);
     setPretDAO(pretDAO);
     setReservationDAO(reservationDAO);
 }

 // Region Getters and Setters
 /**
  * Getter de la variable d'instance <code>this.livreDAO</code>.
  *
  * @return La variable d'instance <code>this.livreDAO</code>
  */
 
 private IMembreDAO getMembreDAO() {
     return this.membreDAO;
 }

 /**
  * Setter de la variable d'instance <code>this.membreDAO</code>.
  *
  * @param membreDAO La valeur à utiliser pour la variable d'instance <code>this.membreDAO</code>
  */
 private void setMembreDAO(IMembreDAO membreDAO) {
     this.membreDAO = membreDAO;
 }

 /**
  * Getter de la variable d'instance <code>this.pretDAO</code>.
  *
  * @return La variable d'instance <code>this.pretDAO</code>
  */
 private ILivreDAO getLivreDAO() {
     return this.livreDAO;
 }

 /**
  * Setter de la variable d'instance <code>this.livreDAO</code>.
  *
  * @param livreDAO La valeur à utiliser pour la variable d'instance <code>this.livreDAO</code>
  */
 private void setLivreDAO(ILivreDAO livreDAO) {
     this.livreDAO = livreDAO;
 }

 /**
  * Getter de la variable d'instance <code>this.membreDAO</code>.
  *
  * @return La variable d'instance <code>this.membreDAO</code>
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

 /**
  * Getter de la variable d'instance <code>this.reservationDAO</code>.
  *
  * @return La variable d'instance <code>this.reservationDAO</code>
  */
 private IReservationDAO getReservationDAO() {
     return this.reservationDAO;
 }

 /**
  * Setter de la variable d'instance <code>this.reservationDAO</code>.
  *
  * @param reservationDAO La valeur à utiliser pour la variable d'instance <code>this.reservationDAO</code>
  */
 private void setReservationDAO(IReservationDAO reservationDAO) {
     this.reservationDAO = reservationDAO;
 }

 // EndRegion Getters and Setters

 /**
  * {@inheritDoc}
  */
 @Override
 public void add(Connexion connexion,
     MembreDTO membreDTO) throws InvalidHibernateSessionException,
     InvalidDTOException,
     InvalidDTOClassException,
     InvalidPrimaryKeyRequestException,
     ServiceException {
     try {
         getMembreDAO().add(connexion,
             membreDTO);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }


 /**
  * {@inheritDoc}
  */
 @Override
 public void delete(Connexion connexion,
     MembreDTO membreDTO) throws InvalidHibernateSessionException,
     InvalidDTOException,
     InvalidDTOClassException,
     ServiceException {
     try {
         getMembreDAO().delete(connexion,
             membreDTO);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }

 
 /**
  * {@inheritDoc}
  */
 @Override
 public void desinscrire(Connexion connexion,
     MembreDTO membreDTO) throws InvalidHibernateSessionException,
     InvalidDTOException,
     InvalidDTOClassException,
     ServiceException {
     try {
         getMembreDAO().desinscrire(connexion,
             membreDTO);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }

 /**
  * {@inheritDoc}
  */
 @Override
 public void inscrire(Connexion connexion,
     MembreDTO membreDTO) throws InvalidHibernateSessionException,
     InvalidDTOException,
     InvalidDTOClassException,
     ServiceException {	
     try {
         getMembreDAO().inscrire(connexion,
             membreDTO);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }



 /**
  * {@inheritDoc}
  */
 @Override
 public List<MembreDTO> getAll(Connexion connexion,
     String sortByPropertyName) throws InvalidHibernateSessionException,
     InvalidSortByPropertyException,
     ServiceException {
     try {
         return (List<MembreDTO>) getMembreDAO().getAll(connexion,
             sortByPropertyName);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }

 /**
  * {@inheritDoc}
  */
 @Override
 public List<MembreDTO> findByNom(Connexion connexion,
     String nom,
     String sortByPropertyName) throws InvalidHibernateSessionException,
     InvalidCriterionException,
     InvalidSortByPropertyException,
     ServiceException {
     try {
         return getMembreDAO().findByNom(connexion,
             nom,
             sortByPropertyName);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }
 
 /**
  * {@inheritDoc}
  */
 
 /**
  * {@inheritDoc}
  */
 @Override
 public MembreDTO get(Connexion connexion,
     String idMembre) throws InvalidHibernateSessionException,
     InvalidPrimaryKeyException,
     ServiceException {
     try {
         return (MembreDTO) getMembreDAO().get(connexion,
             idMembre);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }

 @Override
 public void update(Connexion connexion,
     MembreDTO membreDTO) throws InvalidHibernateSessionException,
     InvalidDTOException,
     InvalidDTOClassException,
     ServiceException {
     try {
         getMembreDAO().update(connexion,
             membreDTO);
     } catch(DAOException daoException) {
         throw new ServiceException(daoException);
     }
 }


}


