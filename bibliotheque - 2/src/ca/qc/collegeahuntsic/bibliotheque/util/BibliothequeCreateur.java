package ca.qc.collegeahuntsic.bibliotheque.util;

import java.sql.SQLException;

import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.ConnexionException;

//class GOD
public class BibliothequeCreateur {
	Connexion con;
	public BibliothequeCreateur (String server,String bd,String user,String pass) throws ConnexionException, SQLException{
		con = new Connexion(server,
				bd,
				user,
				pass);
	}
}
