package ca.qc.collegeahuntsic.bibliotheque.service.implementations;

import java.sql.Timestamp;
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
import ca.qc.collegeahuntsic.bibliotheque.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.service.interfaces.IReservationService;

public class PretService extends Service implements IPretService {
	private IPretDAO pretDAO;

	private IReservationDAO reservationDAO;

	private IMembreDAO membreDAO;

	private ILivreDAO livreDAO;

	/**
	 * Crée le service de la table <code>reservation</code>.
	 * 
	 * @param reservationDAO
	 *            Le DAO de la table <code>reservation</code>
	 * @param membreDAO
	 *            Le DAO de la table <code>membre</code>
	 * @param livreDAO
	 *            Le DAO de la table <code>livre</code>
	 * @param pretDAO
	 *            Le DAO de la table <code>pret</code>
	 * @throws InvalidDAOException
	 *             Si le DAO de réservation est <code>null</code>, si le DAO de
	 *             membre est <code>null</code>, si le DAO de livre est
	 *             <code>null</code> ou si le DAO de prêt est <code>null</code>
	 */
	public PretService(
			IPretDAO pretDAO, // TODO: Change to package when switching to
								// Spring
			IMembreDAO membreDAO, ILivreDAO livreDAO,
			IReservationDAO reservationDAO) throws InvalidDAOException {
		super();
		if (reservationDAO == null) {
			throw new InvalidDAOException(
					"Le DAO de réservation ne peut être null");
		}
		if (membreDAO == null) {
			throw new InvalidDAOException("Le DAO de membre ne peut être null");
		}
		if (livreDAO == null) {
			throw new InvalidDAOException("Le DAO de livre ne peut être null");
		}
		if (pretDAO == null) {
			throw new InvalidDAOException("Le DAO de prêt ne peut être null");
		}
		setPretDAO(pretDAO);
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
	private IReservationDAO getReservationDAO() {
		return this.reservationDAO;
	}

	/**
	 * Setter de la variable d'instance <code>this.reservationDAO</code>.
	 * 
	 * @param reservationDAO
	 *            La valeur à utiliser pour la variable d'instance
	 *            <code>this.reservationDAO</code>
	 */
	private void setReservationDAO(IReservationDAO reservationDAO) {
		this.reservationDAO = reservationDAO;
	}

	/**
	 * Getter de la variable d'instance <code>this.membreDAO</code>.
	 * 
	 * @return La variable d'instance <code>this.membreDAO</code>
	 */
	private IMembreDAO getMembreDAO() {
		return this.membreDAO;
	}

	/**
	 * Setter de la variable d'instance <code>this.membreDAO</code>.
	 * 
	 * @param membreDAO
	 *            La valeur à utiliser pour la variable d'instance
	 *            <code>this.membreDAO</code>
	 */
	private void setMembreDAO(IMembreDAO membreDAO) {
		this.membreDAO = membreDAO;
	}

	/**
	 * Getter de la variable d'instance <code>this.livreDAO</code>.
	 * 
	 * @return La variable d'instance <code>this.livreDAO</code>
	 */
	private ILivreDAO getLivreDAO() {
		return this.livreDAO;
	}

	/**
	 * Setter de la variable d'instance <code>this.livreDAO</code>.
	 * 
	 * @param livreDAO
	 *            La valeur à utiliser pour la variable d'instance
	 *            <code>this.livreDAO</code>
	 */
	private void setLivreDAO(ILivreDAO livreDAO) {
		this.livreDAO = livreDAO;
	}

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
	 * @param pretDAO
	 *            La valeur à utiliser pour la variable d'instance
	 *            <code>this.pretDAO</code>
	 */
	private void setPretDAO(IPretDAO pretDAO) {
		this.pretDAO = pretDAO;
	}

	// EndRegion Getters and Setters

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Connexion connexion, PretDTO pretDTO)
			throws InvalidHibernateSessionException, InvalidDTOException,
			InvalidDTOClassException, InvalidPrimaryKeyRequestException,
			ServiceException {
		try {
			getPretDAO().add(connexion, pretDTO);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PretDTO get(Connexion connexion, String idPret)
			throws InvalidHibernateSessionException,
			InvalidPrimaryKeyException, ServiceException {
		try {
			return (PretDTO) getPretDAO().get(connexion,
					idPret);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Connexion connexion, PretDTO pretDTO)
			throws InvalidHibernateSessionException, InvalidDTOException,
			InvalidDTOClassException, ServiceException {
		try {
			getPretDAO().update(connexion, pretDTO);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Connexion connexion, PretDTO pretDTO)
			throws InvalidHibernateSessionException, InvalidDTOException,
			InvalidDTOClassException, ServiceException {
		try {
			getPretDAO().delete(connexion, pretDTO);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PretDTO> getAll(Connexion connexion,
			String sortByPropertyName) throws InvalidHibernateSessionException,
			InvalidSortByPropertyException, ServiceException {
		try {
			return (List<PretDTO>) getPretDAO().getAll(connexion,
					sortByPropertyName);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PretDTO> findByMembre(Connexion connexion,
			String idMembre, String sortByPropertyName)
			throws InvalidHibernateSessionException, InvalidCriterionException,
			InvalidSortByPropertyException, ServiceException {
		try {
			return getPretDAO().findByMembre(connexion, idMembre,
					sortByPropertyName);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PretDTO> findByLivre(Connexion connexion,
			String idLivre, String sortByPropertyName)
			throws InvalidHibernateSessionException, InvalidCriterionException,
			InvalidSortByPropertyException, ServiceException {
		try {
			return getReservationDAO().findByLivre(connexion, idLivre,
					sortByPropertyName);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PretDTO> findByDatePret(Connexion connexion,
			Timestamp datePret, String sortByPropertyName)
			throws InvalidHibernateSessionException, InvalidCriterionException,
			InvalidSortByPropertyException, ServiceException {
		try {
			return getReservationDAO().findByDatePret(connexion, datePret,
					sortByPropertyName);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PretDTO> findByDateRetour(Connexion connexion,
			Timestamp dateRetour, String sortByPropertyName)
			throws InvalidHibernateSessionException, InvalidCriterionException,
			InvalidSortByPropertyException, ServiceException {
		try {
			return getReservationDAO().findByDateRetour(connexion, dateRetour,
					sortByPropertyName);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	
	//doesn't exist in DAO
	public void commencer(Connexion connexion, PretDTO pretDTO)
			throws InvalidHibernateSessionException, InvalidDTOException,
			InvalidDTOClassException, InvalidPrimaryKeyRequestException,
			ServiceException {
		try {
			//if livre pas deja empreunter
			//if limitePret du membre <
			//if deja reservé
			//else
			getPretDAO().commencer(connexion, pretDTO);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}
	
	public void renouveler(Connexion connexion, PretDTO pretDTO)
			throws InvalidHibernateSessionException, InvalidDTOException,
			InvalidDTOClassException, InvalidPrimaryKeyRequestException,
			ServiceException {
		try {
			getPretDAO().renouveler(connexion, pretDTO);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}
	
	public void terminer(Connexion connexion, PretDTO pretDTO)
			throws InvalidHibernateSessionException, InvalidDTOException,
			InvalidDTOClassException, InvalidPrimaryKeyRequestException,
			ServiceException {
		try {
			getPretDAO().terminer(connexion, pretDTO);
		} catch (DAOException daoException) {
			throw new ServiceException(daoException);
		}
	}




	//,  , , renouveler, terminer.
//	get membre, get livre (+gestion error), si dja reserver,empreunter, limitPret
}
