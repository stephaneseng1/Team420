package dao;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import ca.qc.collegeahuntsic.bibliotheque.bibliotheque.bibliotheque.BIBLIOTHQUE.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.bibliotheque.bibliotheque.BIBLIOTHQUE.TupleLivre;

/**
 * Permet d'effectuer les acc�s � la table livre.
 */

public class LivreDAO extends DAO{
	private static final long serialVersionUID = 1L;
	
	//INSERT LIVRE
	private static final String ADD_REQUEST = "INSERT INTO livre(idLivre, titre, auteur, dateAcquisition, idMembre, datePret) "
			+ "values (?,?,?,?,null,null)");
	
	//SELECT BY IDLIVRE
			
	//UPDATE
			
	//DELETE
			
	//SELECT ALL REQUEST
			
	//FIND BY TITLE
			
	//FIND BY MEMBER
			
	//REQUEST EMPRUNT (UPDATE)
	
	//REQUEST RETOUR (UPDATE)
			
	
	private PreparedStatement stmtExiste;

	private PreparedStatement stmtInsert;

	private PreparedStatement stmtUpdate;

	private PreparedStatement stmtDelete;

	private Connexion cx;

	/**
	  * Creation d'une instance. Des �nonc�s SQL pour chaque requ�te sont pr�compil�s.
	  */
	public LivreDAO(Connexion cx) throws SQLException {

		this.cx = cx;
		stmtExiste = cx.getConnection().prepareStatement("select idlivre, titre, auteur, dateAcquisition, idMembre, datePret from livre where idlivre = ?");
		stmtInsert = cx.getConnection().prepareStatement("insert into livre (idLivre, titre, auteur, dateAcquisition, idMembre, datePret) "
			+ "values (?,?,?,?,null,null)");
		stmtUpdate = cx.getConnection().prepareStatement("update livre set idMembre = ?, datePret = ? "
			+ "where idLivre = ?");
		stmtDelete = cx.getConnection().prepareStatement("delete from livre where idlivre = ?");
	}

	/**
	  * Retourner la connexion associ�e.
	  */
	public Connexion getConnexion() {

		return cx;
	}
	
	public void add(LivreDTO livreDTO) throws DAOException{
		try{
			PreparedStatement PrepAdd = getConnection().preparedStatement(LivreDAO.ADD_REQUEST).
		} catch(SQLException sqlException){
			throw new DAOException(sqlException);
		}
	}

	/**
	  * Verifie si un livre existe.
	  */
	public boolean existe(int idLivre) throws SQLException {

		stmtExiste.setInt(1,
			idLivre);
		ResultSet rset = stmtExiste.executeQuery();
		boolean livreExiste = rset.next();
		rset.close();
		return livreExiste;
	}

	/**
	  * Lecture d'un livre.
	  */
	public LivreDTO getLivre(int idLivre) throws SQLException {

		stmtExiste.setInt(1,
			idLivre);
		ResultSet rset = stmtExiste.executeQuery();
		if(rset.next()) {
			LivreDTO tupleLivre = new LivreDTO();
			tupleLivre.idLivre = idLivre;
			tupleLivre.titre = rset.getString(2);
			tupleLivre.auteur = rset.getString(3);
			tupleLivre.dateAcquisition = rset.getDate(4);
			tupleLivre.idMembre = rset.getInt(5);
			tupleLivre.datePret = rset.getDate(6);
			return tupleLivre;
		} else
			return null;
	}

	/**
	  * Ajout d'un nouveau livre dans la base de donnees.
	  */
	public void acquerir(int idLivre,
		String titre,
		String auteur,
		String dateAcquisition) throws SQLException {
		/* Ajout du livre. */
		stmtInsert.setInt(1,
			idLivre);
		stmtInsert.setString(2,
			titre);
		stmtInsert.setString(3,
			auteur);
		stmtInsert.setDate(4,
			Date.valueOf(dateAcquisition));
		stmtInsert.executeUpdate();
	}

	/**
	  * Enregistrement de l'emprunteur d'un livre.
	  */
	public int preter(int idLivre,
		int idMembre,
		String datePret) throws SQLException {
		/* Enregistrement du pret. */
		stmtUpdate.setInt(1,
			idMembre);
		stmtUpdate.setDate(2,
			Date.valueOf(datePret));
		stmtUpdate.setInt(3,
			idLivre);
		return stmtUpdate.executeUpdate();
	}

	/**
	  * Rendre le livre disponible (non-pr�t�)
	  */
	public int retourner(int idLivre) throws SQLException {
		/* Enregistrement du pret. */
		stmtUpdate.setNull(1,
			Types.INTEGER);
		stmtUpdate.setNull(2,
			Types.DATE);
		stmtUpdate.setInt(3,
			idLivre);
		return stmtUpdate.executeUpdate();
	}

	/**
	  * Suppression d'un livre.
	  */
	public int vendre(int idLivre) throws SQLException {
		/* Suppression du livre. */
		stmtDelete.setInt(1,
			idLivre);
		return stmtDelete.executeUpdate();
	}
}
