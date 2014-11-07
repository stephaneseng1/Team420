
package ca.qc.collegeahuntsic.bibliotheque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.StringTokenizer;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.MembreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.ReservationDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.DAOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidCriterionValueException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOClassException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.MissingDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.InvalidLoanLimitException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.MissingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.util.BibliothequeCreateur;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.util.FormatteurDate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Interface du système de gestion d'une bibliothèque
 *
 * Ce programme permet d'appeler les transactions de base d'une bibliothèque. Il
 * gère des livres, des membres et des réservations. Les données sont conservées
 * dans une base de données relationnelles accédée avec JDBC. Pour une liste des
 * transactions traitées, voir la méthode afficherAide().
 *
 * Paramètres 0- site du serveur SQL ("local", "distant" ou "postgres") 1- nom
 * de la BD 2- user id pour établir une Session avec le serveur SQL 3- mot de
 * passe pour le user id 4- fichier de transaction [optionnel] si non spécifié,
 * les transactions sont lues au clavier (System.in)
 *
 * Pré-condition la base de données de la bibliothèque doit exister
 *
 * Post-condition le programme effectue les maj associées à chaque transaction
 * @author Stephane Seng
 */
final class Bibliotheque {
    private static BibliothequeCreateur gestionBiblio;

    private static final Log LOGGER = LogFactory.getLog(Bibliotheque.class);

    /**.
     * Constructeur
     * 
     */
    private Bibliotheque() {
        super();
    }

    /**
     * Ouverture de la BD, traitement des transactions et fermeture de la BD.
     * @param argv Les arguments
     * @throws Exception Les exceptions
     */
    public static void main(String[] argv) throws Exception {
        // validation du nombre de parametres
        try {
            // ouverture du fichier de transactions
            final InputStream sourceTransaction = Bibliotheque.class.getResourceAsStream("../../../../bibliotheque.dat");

            try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(sourceTransaction))) {
                gestionBiblio = new BibliothequeCreateur();
                traiterTransactions(reader);
            }
        } catch(Exception e) {
            gestionBiblio.rollbackTransaction();
            Bibliotheque.LOGGER.info(e);
        }
        Bibliotheque.LOGGER.info("Usage: java Biblio <serveur> <bd> <user> <password> [<fichier-transactions>]");
    }

    /**.
     * Traitement des transactions de la bibliothèque
     * @param reader Le bufferedreader
     * @throws Exception Les exceptions
     */
    static void traiterTransactions(BufferedReader reader) throws Exception {
        afficherAide();
        String transaction = lireTransaction(reader);
        while(!finTransaction(transaction)) {
            final StringTokenizer tokenizer = new StringTokenizer(transaction,
                " ");
            if(tokenizer.hasMoreTokens()) {
                executerTransaction(tokenizer);
            }
            transaction = lireTransaction(reader);
        }
    }

    /**.
     * Lecture d'une transaction
     * @param reader Le bufferedreader
     * @throws IOException Les exceptions
     * @return transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException {
        final String transaction = reader.readLine();
        if(transaction != null) {
            Bibliotheque.LOGGER.info(transaction);
        }
        return transaction;
    }

    /**.
     * Décodage et traitement d'une transaction
     *
     * @param tokenizer Le StringTokenizer
     * @throws BibliothequeException Exception de bibliotheque
     * @throws InvalidCriterionValueException Exception criterion value
     * @throws DAOException Exception dao
     */
    static void executerTransaction(StringTokenizer tokenizer) throws BibliothequeException,
        InvalidCriterionValueException,
        DAOException {
        try {
            final String command = tokenizer.nextToken();

            if("aide".startsWith(command)) {
                afficherAide();
            } else if("acquerir".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final LivreDTO livreDTO = new LivreDTO();
                livreDTO.setTitre(readString(tokenizer));
                livreDTO.setAuteur(readString(tokenizer));
                livreDTO.setDateAcquisition(readDate(tokenizer));
                gestionBiblio.getLivreFacade().acquerir(gestionBiblio.getSession(),
                    livreDTO);
                gestionBiblio.commitTransaction();
            } else if("vendre".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final LivreDTO livreDTO = new LivreDTO();
                livreDTO.setIdLivre(readString(tokenizer));
                gestionBiblio.getLivreFacade().vendre(gestionBiblio.getSession(),
                    livreDTO);
                gestionBiblio.commitTransaction();
            } else if("preter".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final PretDTO pretDTO = new PretDTO();
                final String membreId = readString(tokenizer);
                final String livreId = readString(tokenizer);
                final MembreDTO membreDTO = gestionBiblio.getMembreFacade().getMembre(gestionBiblio.getSession(),
                    membreId);
                if(membreDTO == null) {
                    throw new MissingDTOException("Le membreId "
                        + membreId
                        + " est null.");
                }
                final LivreDTO livreDTO = gestionBiblio.getLivreFacade().getLivre(gestionBiblio.getSession(),
                    livreId);
                pretDTO.setMembreDTO(membreDTO);
                if(livreDTO == null) {
                    throw new MissingDTOException("Le livreId "
                        + livreId
                        + " est null.");
                }
                pretDTO.setLivreDTO(livreDTO);
                gestionBiblio.getPretFacade().commencer(gestionBiblio.getSession(),
                    pretDTO);
                gestionBiblio.commitTransaction();
            } else if("renouveler".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final PretDTO pretDTO = new PretDTO();
                pretDTO.setIdPret(readString(tokenizer));
                gestionBiblio.getPretFacade().renouveler(gestionBiblio.getSession(),
                    pretDTO);
                gestionBiblio.commitTransaction();
            } else if("retourner".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final PretDTO pretDTO = new PretDTO();
                pretDTO.setIdPret(readString(tokenizer));
                gestionBiblio.getPretFacade().terminer(gestionBiblio.getSession(),
                    pretDTO);
                gestionBiblio.commitTransaction();
            } else if("inscrire".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final MembreDTO membreDTO = new MembreDTO();
                membreDTO.setNom(readString(tokenizer));
                membreDTO.setTelephone(readString(tokenizer));
                membreDTO.setLimitePret(readString(tokenizer));
                membreDTO.setNbPret("0");
                gestionBiblio.getMembreFacade().inscrire(gestionBiblio.getSession(),
                    membreDTO);
                gestionBiblio.commitTransaction();
            } else if("desinscrire".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final MembreDTO membreDTO = new MembreDTO();
                membreDTO.setIdMembre(readString(tokenizer));
                gestionBiblio.getMembreFacade().desinscrire(gestionBiblio.getSession(),
                    membreDTO);
                gestionBiblio.commitTransaction();
            } else if("reserver".startsWith(command)) {
                // Juste pour éviter deux timestamps de réservation strictement
                // identiques
                Thread.sleep(1);
                gestionBiblio.beginTransaction();
                final ReservationDTO reservationDTO = new ReservationDTO();
                final String membreId = readString(tokenizer);
                final String livreId = readString(tokenizer);
                final MembreDTO membreDTO = gestionBiblio.getMembreFacade().getMembre(gestionBiblio.getSession(),
                    membreId);
                if(membreDTO == null) {
                    throw new MissingDTOException("Le membreId "
                        + membreId
                        + " est null.");
                }
                reservationDTO.setMembreDTO(membreDTO);
                final LivreDTO livreDTO = gestionBiblio.getLivreFacade().getLivre(gestionBiblio.getSession(),
                    livreId);
                if(livreDTO == null) {
                    throw new MissingDTOException("Le livreId "
                        + livreId
                        + " est null.");
                }
                reservationDTO.setLivreDTO(livreDTO);
                gestionBiblio.getReservationFacade().placer(gestionBiblio.getSession(),
                    reservationDTO);
                gestionBiblio.commitTransaction();
            } else if("utiliser".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readString(tokenizer));
                gestionBiblio.getReservationFacade().utiliser(gestionBiblio.getSession(),
                    reservationDTO);
                gestionBiblio.commitTransaction();
            } else if("annuler".startsWith(command)) {
                gestionBiblio.beginTransaction();
                final ReservationDTO reservationDTO = new ReservationDTO();
                reservationDTO.setIdReservation(readString(tokenizer));
                gestionBiblio.getReservationFacade().annuler(gestionBiblio.getSession(),
                    reservationDTO);
                gestionBiblio.commitTransaction();
            } else if("--".startsWith(command)) {
                Bibliotheque.LOGGER.info("");
            } else {
                Bibliotheque.LOGGER.info("  Transactions non reconnue.  Essayer \"aide\"");
            }
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | InvalidDTOClassException
            | FacadeException
            | InvalidPrimaryKeyException
            | MissingDTOException
            | InvalidCriterionException
            | InvalidSortByPropertyException
            | ExistingLoanException
            | ExistingReservationException
            | InvalidLoanLimitException
            | MissingLoanException exception) {
            Bibliotheque.LOGGER.error("**** "
                + exception.getMessage());
            gestionBiblio.rollbackTransaction();
        } catch(InterruptedException interruptedException) {
            Bibliotheque.LOGGER.info("**** "
                + interruptedException.toString());
            gestionBiblio.rollbackTransaction();
        }
    }

    /**.
     * Affiche le menu des transactions acceptées par le système
     */
    static void afficherAide() {
        Bibliotheque.LOGGER.info("Chaque transaction comporte un nom et une liste d'arguments");
        Bibliotheque.LOGGER.info("separes par des espaces. La liste peut etre vide.");
        Bibliotheque.LOGGER.info(" Les dates sont en format yyyy-mm-dd.");
        Bibliotheque.LOGGER.info("");
        Bibliotheque.LOGGER.info("Les transactions sont :");
        Bibliotheque.LOGGER.info("  aide");
        Bibliotheque.LOGGER.info("  exit");
        Bibliotheque.LOGGER.info("  acquerir <titre> <auteur> <dateAcquisition>");
        Bibliotheque.LOGGER.info("  preter <idMembre> <idLivre>");
        Bibliotheque.LOGGER.info("  renouveler <idLivre>");
        Bibliotheque.LOGGER.info("  retourner <idLivre>");
        Bibliotheque.LOGGER.info("  vendre <idLivre>");
        Bibliotheque.LOGGER.info("  inscrire <nom> <telephone> <limitePret>");
        Bibliotheque.LOGGER.info("  desinscrire <idMembre>");
        Bibliotheque.LOGGER.info("  reserver <idMembre> <idLivre>");
        Bibliotheque.LOGGER.info("  utiliser <idReservation>");
        Bibliotheque.LOGGER.info("  annuler <idReservation>");
        // Bibliotheque.LOGGER.info("  listerLivresRetard <dateCourante>");
        // Bibliotheque.LOGGER.info("  listerLivresTitre <mot>");
        // Bibliotheque.LOGGER.info("  listerLivres");
    }

    /**
     * Vérifie si la fin du traitement des transactions est atteinte.
     * @param transaction Le string de fin
     * @return true fin
     */
    static boolean finTransaction(String transaction) {
        if(transaction == null) {
            return true;
        }

        final StringTokenizer tokenizer = new StringTokenizer(transaction,
            " ");

        if(!tokenizer.hasMoreTokens()) {
            return false;
        }
        final String commande = tokenizer.nextToken();
        return commande.equals("exit");
    }

    /**.
     * lecture d'une chaîne de caractères de la transaction entrée à l'écran
     * @param tokenizer Le StringTokenizer
     * @throws BibliothequeException exceptions de bibliotheque
     * @return Le prochain token
     */
    static String readString(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            return tokenizer.nextToken();
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**.
     * lecture d'un int java de la transaction entrée à l'écran
     * @param tokenizer Le StringTokenizer
     * @throws BibliothequeException Exceptions
     * @return retourne le int du tokenizer
     */
    static int readInt(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return Integer.valueOf(token).intValue();
            } catch(NumberFormatException e) {
                throw new BibliothequeException("Nombre attendu à la place de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**.
     * lecture d'un long java de la transaction entrée à l'écran
     * @param tokenizer Le StringTokenizer
     * @throws BibliothequeException Exceptions
     * @return retourne le long du tokenizer
     */
    static long readLong(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return Long.valueOf(token).longValue();
            } catch(NumberFormatException e) {
                throw new BibliothequeException("Nombre attendu à la place de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }

    /**.
     * lecture d'une date en format YYYY-MM-DD
     * @param tokenizer Le StringTokenizer
     * @throws BibliothequeException Exceptions
     * @return retourne la date en string du tokenizer
     */
    static Timestamp readDate(StringTokenizer tokenizer) throws BibliothequeException {
        if(tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            try {
                return FormatteurDate.timestampValue(token);
            } catch(ParseException e) {
                throw new BibliothequeException("Date en format YYYY-MM-DD attendue à la place  de \""
                    + token
                    + "\"");
            }
        }
        throw new BibliothequeException("autre paramètre attendu");
    }
}
