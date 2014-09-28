package ca.qc.collegeahuntsic.bibliotheque.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.DTO;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.DAOException;

public class PretDAO extends DAO {
	 private static final long serialVersionUID = 1L;

	    private static final String ADD_REQUEST = "INSERT INTO pret (idPret, idlivre, idMembre, datePret) "
	        + "VALUES (?, ?, ?, SYSTIMESTAMP)";

	    private static final String READ_REQUEST = "SELECT idPret, idLivre, idMembre, datePret "
	        + "FROM pret "
	        + "WHERE idPret = ?";

	    private static final String UPDATE_REQUEST = "UPDATE pret "
	        + "SET idPret = ?, idLivre = ?,  idMembre = ?, datePret = ? "
	        + "WHERE idPret = ?";

	    private static final String DELETE_REQUEST = "DELETE FROM pret "
	        + "WHERE idPret = ?";

	    private static final String GET_ALL_REQUEST = "SELECT idPret, idLivre, idMembre, datePret "
	        + "FROM pret";

	    private static final String FIND_BY_LIVRE_REQUEST = "SELECT idPret, idLivre, idMembre, datePret "
	        + "FROM pret "
	        + "WHERE idLivre = ? "
	        + "ORDER BY datePret ASC";

	    private static final String FIND_BY_MEMBRE_REQUEST = "SELECT idPret, idLivre, idMembre, datePret "
	        + "FROM pret "
	        + "WHERE idMembre = ?";

	    /**
	     * Créer un DAO à  partir d'une connexion à  la base de données.
	     * 
	     * @param connexion La connexion à utiliser
	     */
	    public PretDAO(Connexion connexion) {
	        super(connexion);
	    }

	    /**
	     * Ajoute une nouvelle réservation.
	     * 
	     * @param pretDTO La réservation à  ajouter
	     * @throws DAOException S'il y a une erreur avec la base de données
	     */
	    public void add(PretDTO pretDTO) throws DAOException {
	        try(
	            PreparedStatement addPreparedStatement = getConnection().prepareStatement(PretDAO.ADD_REQUEST)) {
	            addPreparedStatement.setInt(1,
	                pretDTO.getIdPret());
	            addPreparedStatement.setInt(2,
	                pretDTO.getIdLivre());
	            addPreparedStatement.setInt(3,
	                pretDTO.getIdMembre());
	            addPreparedStatement.executeUpdate();
	        } catch(SQLException sqlException) {
	            throw new DAOException(sqlException);
	        }
	    }

	    /**
	     * Lit une réservation.
	     * 
	     * @param idPret La réservation Ã  lire
	     * @throws DAOException S'il y a une erreur avec la base de données
	     */
	    public PretDTO read(int idPret) throws DAOException {
	        PretDTO pretDTO = null;
	        try(
	            PreparedStatement readPreparedStatement = getConnection().prepareStatement(PretDAO.READ_REQUEST)) {
	            readPreparedStatement.setInt(1,
	                idPret);
	            try(
	                ResultSet resultSet = readPreparedStatement.executeQuery()) {
	                if(resultSet.next()) {
	                    pretDTO = new PretDTO();
	                    pretDTO.setIdPret(resultSet.getInt(1));
	                    pretDTO.setIdLivre(resultSet.getInt(2));
	                    pretDTO.setIdMembre(resultSet.getInt(3));
	                    pretDTO.setDatePret(resultSet.getTimestamp(4));
	                }
	            }
	        } catch(SQLException sqlException) {
	            throw new DAOException(sqlException);
	        }
	        return pretDTO;
	    }

	    /**
	     * Met à  jour une réservation.
	     * 
	     * @param pretDTO La réservation à  mettre à  jour
	     * @throws DAOException S'il y a une erreur avec la base de données
	     */
	    public void update(PretDTO pretDTO) throws DAOException {
	        try(
	            PreparedStatement updatePreparedStatement = getConnection().prepareStatement(PretDAO.UPDATE_REQUEST)) {
	            updatePreparedStatement.setInt(1,
	                pretDTO.getIdPret());
	            updatePreparedStatement.setInt(2,
	                pretDTO.getIdLivre());
	            updatePreparedStatement.setInt(3,
	                pretDTO.getIdMembre());
	            updatePreparedStatement.setTimestamp(4,
	                pretDTO.getDatePret());
	            updatePreparedStatement.setInt(5,
	                pretDTO.getIdPret());
	            updatePreparedStatement.executeUpdate();
	        } catch(SQLException sqlException) {
	            throw new DAOException(sqlException);
	        }
	    }

	    /**
	     * Supprime une réservation.
	     * 
	     * @param pretDTO La réservation à  supprimer
	     * @throws DAOException S'il y a une erreur avec la base de données
	     */
	    public void delete(PretDTO pretDTO) throws DAOException {
	        try(
	            PreparedStatement deletePreparedStatement = getConnection().prepareStatement(PretDAO.DELETE_REQUEST)) {
	            deletePreparedStatement.setInt(1,
	                pretDTO.getIdPret());
	            deletePreparedStatement.executeUpdate();
	        } catch(SQLException sqlException) {
	            throw new DAOException(sqlException);
	        }
	    }

	    /**
	     * Trouve toutes les réservations.
	     * 
	     * @return La liste des réservations ; une liste vide sinon
	     * @throws DAOException S'il y a une erreur avec la base de données
	     */
	    public List<PretDTO> getAll() throws DAOException {
	        List<PretDTO> prets = Collections.EMPTY_LIST;
	        try(
	            PreparedStatement getAllPreparedStatement = getConnection().prepareStatement(PretDAO.GET_ALL_REQUEST)) {
	            try(
	                ResultSet resultSet = getAllPreparedStatement.executeQuery()) {
	                PretDTO pretDTO = null;
	                if(resultSet.next()) {
	                    prets = new ArrayList<>();
	                    do {
	                        pretDTO = new PretDTO();
	                        pretDTO.setIdPret(resultSet.getInt(1));
	                        pretDTO.setIdLivre(resultSet.getInt(2));
	                        pretDTO.setIdMembre(resultSet.getInt(3));
	                        pretDTO.setDatePret(resultSet.getTimestamp(4));
	                        prets.add(pretDTO);
	                    } while(resultSet.next());
	                }
	            }
	        } catch(SQLException sqlException) {
	            throw new DAOException(sqlException);
	        }
	        return prets;
	    }

	    /**
	     * Trouve les réservations à  partir d'un livre.
	     * 
	     * @param livreDTO Le livre à  utiliser
	     * @return La liste des réservations correspondantes, triée par date de réservation croissante ; une liste vide sinon
	     * @throws DAOException S'il y a une erreur avec la base de données
	     */
	    public List<PretDTO> findByLivre(LivreDTO livreDTO) throws DAOException {
	        List<PretDTO> prets = Collections.EMPTY_LIST;
	        try(
	            PreparedStatement findByLivrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_LIVRE_REQUEST)) {
	            findByLivrePreparedStatement.setInt(1,
	                livreDTO.getIdLivre());
	            try(
	                ResultSet resultSet = findByLivrePreparedStatement.executeQuery()) {
	                PretDTO pretDTO = null;
	                if(resultSet.next()) {
	                    prets = new ArrayList<>();
	                    do {
	                        pretDTO = new PretDTO();
	                        pretDTO.setIdPret(resultSet.getInt(1));
	                        pretDTO.setIdLivre(resultSet.getInt(2));
	                        pretDTO.setIdMembre(resultSet.getInt(3));
	                        pretDTO.setDatePret(resultSet.getTimestamp(4));
	                        prets.add(pretDTO);
	                    } while(resultSet.next());
	                }
	            }
	        } catch(SQLException sqlException) {
	            throw new DAOException(sqlException);
	        }
	        return prets;
	    }

	    /**
	     * Trouve les réservations à  partir d'un membre.
	     * 
	     * @param membreDTO Le membre à  utiliser
	     * @return La liste des réservations correspondantes ; une liste vide sinon
	     * @throws DAOException S'il y a une erreur avec la base de données
	     */
	    public List<PretDTO> findByMembre(MembreDTO membreDTO) throws DAOException {
	        List<PretDTO> prets = Collections.EMPTY_LIST;
	        try(
	            PreparedStatement findByMembrePreparedStatement = getConnection().prepareStatement(PretDAO.FIND_BY_MEMBRE_REQUEST)) {
	            findByMembrePreparedStatement.setInt(1,
	                membreDTO.getIdMembre());
	            try(
	                ResultSet resultSet = findByMembrePreparedStatement.executeQuery()) {
	                PretDTO pretDTO = null;
	                if(resultSet.next()) {
	                    prets = new ArrayList<>();
	                    do {
	                        pretDTO = new PretDTO();
	                        pretDTO.setIdPret(resultSet.getInt(1));
	                        pretDTO.setIdLivre(resultSet.getInt(2));
	                        pretDTO.setIdMembre(resultSet.getInt(3));
	                        pretDTO.setDatePret(resultSet.getTimestamp(4));
	                        prets.add(pretDTO);
	                    } while(resultSet.next());
	                }
	            }
	        } catch(SQLException sqlException) {
	            throw new DAOException(sqlException);
	        }
	        return prets;
	    }
}
