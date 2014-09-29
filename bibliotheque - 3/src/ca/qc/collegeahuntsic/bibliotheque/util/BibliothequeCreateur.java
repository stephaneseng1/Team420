package ca.qc.collegeahuntsic.bibliotheque.util;

//import GestionInterrogation;
//import GestionLivre;

import java.sql.SQLException;

import ca.qc.collegeahuntsic.bibliotheque.dao.LivreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.MembreDAO;
import ca.qc.collegeahuntsic.bibliotheque.dao.ReservationDAO;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.ConnexionException;
import ca.qc.collegeahuntsic.bibliotheque.service.LivreService;
import ca.qc.collegeahuntsic.bibliotheque.service.MembreService;
import ca.qc.collegeahuntsic.bibliotheque.service.PretService;
import ca.qc.collegeahuntsic.bibliotheque.service.ReservationService;

//class GOD
public class BibliothequeCreateur {
	Connexion con;

	public LivreDAO livre;

	public MembreDAO membre;

	public ReservationDAO reservation;



	public MembreService gestionMembre;

	public PretService gestionPret;

	public ReservationService gestionReservation;

	public LivreService livreService;
	
	public MembreService membreService;
	
	public ReservationService reservationService;
	
	public PretService pretService;
	
//	public GestionInterrogation gestionInterrogation;
//	public GestionLivre gestionLivre;

	public BibliothequeCreateur (String server,String bd,String user,String pass) throws ConnexionException, SQLException{
		con = new Connexion(server,
				bd,
				user,
				pass);
		
		livre = new LivreDAO(con);
		membre = new MembreDAO(con);
		reservation = new ReservationDAO(con);
		
		livreService = new LivreService(livre,membre,reservation);
		membreService =new MembreService(membre,livre,reservation);
		reservationService = new ReservationService(reservation, livre, membre);
		pretService = new PretService();
		
		
		

	}

	public void fermer() throws SQLException {
		// fermeture de la connexion
		con.fermer();
	}
}
