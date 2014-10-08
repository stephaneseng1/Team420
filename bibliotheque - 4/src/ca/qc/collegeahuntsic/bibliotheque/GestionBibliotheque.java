package ca.qc.collegeahuntsic.bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;
import java.util.StringTokenizer;

import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliotheque.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dao.InvalidPrimaryKeyRequestException;
import ca.qc.collegeahuntsic.bibliotheque.exception.db.ConnexionException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliotheque.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliotheque.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliotheque.util.BibliothequeCreateur;

public class GestionBibliotheque {
	static String url = null;

	static String user = null;

	static String password = null;

	static InputStream inStream = null;

	static Connexion connexion = null;

	static GestionBibliotheque gestionBibliotheque;

	static BibliothequeCreateur bc = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gestionBibliotheque = new GestionBibliotheque();
	}

	public GestionBibliotheque() {
		aide();
	}

	public void init() {
		Properties config = new Properties();
		String propFileName = "config.properties";
		try {
			inStream = getClass().getClassLoader().getResourceAsStream(
					propFileName);
			config.load(inStream);
			url = config.getProperty("url");
			user = config.getProperty("user");
			password = config.getProperty("password");
			inStream.close();
		} catch (IOException e) {
			System.err.println("ERROR: lecture du fichier.");
			e.printStackTrace();
		}
		try {
			bc = new BibliothequeCreateur("distant", url, user, password);
		} catch (BibliothequeException e) {
			System.err.println(e.getMessage());
		}
	}

	public void aide() {
		System.out.println();
		System.out.println("");
		System.out.println("separes par des espaces. La liste peut etre vide.");
		System.out.println(" Les dates sont en format yyyy-mm-dd.");
		System.out.println("");
		System.out.println("Les transactions sont :");
		System.out.println("  aide");
		System.out.println("  exit");
		System.out
				.println("  acquerir <idLivre> <titre> <auteur> <dateAcquisition>");
		System.out.println("  preter <idMembre> <idLivre>");
		System.out.println("  renouveler <idLivre>");
		System.out.println("  retourner <idLivre>");
		System.out.println("  vendre <idLivre>");
		System.out
				.println("  inscrire <idMembre> <nom> <telephone> <limitePret>");
		System.out.println("  desinscrire <idMembre>");
		System.out.println("  reserver <idReservation> <idMembre> <idLivre>");
		System.out.println("  utiliser <idReservation>");
		System.out.println("  annuler <idReservation>");
	}

	public void traiterFicher() {
		// TODO
		try {
			inStream = getClass().getClassLoader().getResourceAsStream(
					"bibliotheque.dat");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inStream));
			String ligne;
			while ((ligne = reader.readLine()) != null) {
				executerTransaction(ligne);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void executerTransaction(String transaction) {
		try {
			StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
			String type = tokenizer.nextToken();
			switch (type) {
			case "acquerir":
				LivreDTO livreDTO = new LivreDTO();
				livreDTO.setIdLivre(tokenizer.nextToken());
				livreDTO.setTitre(tokenizer.nextToken());
				livreDTO.setAuteur(tokenizer.nextToken());
				Date date= new java.util.Date();
				Timestamp currentStamp = new Timestamp(date.getTime());
				livreDTO.setDateAcquisition(currentStamp);
				bc.getLivreService().acquerir(bc.getConnexion(), livreDTO);
				bc.commit();
				break;
			case "vendre":
				break;
			case "preter":
				break;
			case "renouveler":
				break;
			case "retourner":
				break;
			case "inscrire":
				break;
			case "desinscrire":
				break;
			case "reserver":
				break;
			case "utiliser":
				break;
			case "annuler":
				break;
			default:
				break;
			}
		} catch (InvalidHibernateSessionException | InvalidDTOException
				| InvalidDTOClassException | InvalidPrimaryKeyRequestException
				| ServiceException | BibliothequeException e) {
			try {
				bc.rollback();
			} catch (BibliothequeException e1) {
				System.out.println(e1.getMessage());
			}
			System.out.println(e.getMessage());
		}
	}
}
