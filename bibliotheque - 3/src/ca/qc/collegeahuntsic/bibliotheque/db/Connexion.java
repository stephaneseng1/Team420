// Fichier Connexion.java
// Auteur : Gilles Benichou
// Date de creation : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ConnexionException;

/**
 * Cette classe encapsule une connexion JDBC en fonction d'un type et d'une instance de base de donnÃ©es.<br /><br />
 *
 * La methode {@link Connexion#getServeursSupportes()} indique les type de serveur supportes.<br /><br />
 *
 * Pre-condition : le driver JDBC approprie doit etre accessible.<br />
 *
 * Post-condition : la connexion est cree en mode autocommit false.
 *
 * @author Gilles Benichou
 */
public class Connexion implements AutoCloseable {
    private Connection connection;

    private static final String TYPE_SERVEUR_LOCAL = "local";

    private static final String TYPE_SERVEUR_DISTANT = "distant";

    private static final String TYPE_SERVEUR_POSTGRES = "postgres";

    private static final String TYPE_SERVEUR_ACCESS = "access";

    private static final String SERVEUR_LOCAL_CLASS = "com.mysql.jdbc.Driver";

    private static final String SERVEUR_DISTANT_CLASS = "oracle.jdbc.driver.OracleDriver";

    private static final String SERVEUR_POSTGRES_CLASS = "org.postgresql.Driver";

    private static final String SERVEUR_ACCESS_CLASS = "org.postgresql.Driver";

    private static final String SERVEUR_LOCAL_URL = "jdbc:mysql://localhost:3306/";

    private static final String SERVEUR_DISTANT_URL = "jdbc:oracle:thin:@localhost:1521:";

    //	private static final String SERVEUR_DISTANT_URL = "jdbc:oracle:thin:@collegeahunstic.info:1521:";

    private static final String SERVEUR_POSTGRES_URL = "jdbc:postgresql:";

    private static final String SERVEUR_ACCESS_URL = "jdbc:postgresql:";

    /**
     * CrÃ©e une connexion en mode autocommit false.
     * 
     * @param typeServeur Type de serveur SQL de la BD
     * @param schema Nom du schema de la base de donnees
     * @param nomUtilisateur Nom d'utilisateur sur le serveur SQL
     * @param motPasse Mot de passe sur le serveur SQL
     * @throws ConnexionException Si le driver n'existe pas, S'il y a une erreur avec la base de donnees ou si <code>typeServeur</code> n'est
     *         pas valide
     */
    public Connexion(String typeServeur,
        String schema,
        String nomUtilisateur,
        String motPasse) throws ConnexionException {
        String urlBD = null;

        try {
            if(typeServeur.equals(Connexion.TYPE_SERVEUR_LOCAL)) {
                Class.forName(Connexion.SERVEUR_LOCAL_CLASS);
                urlBD = Connexion.SERVEUR_LOCAL_URL
                    + schema;
            } else if(typeServeur.equals(Connexion.TYPE_SERVEUR_DISTANT)) {
                Class.forName(Connexion.SERVEUR_DISTANT_CLASS);
                urlBD = Connexion.SERVEUR_DISTANT_URL
                    + schema;
            } else if(typeServeur.equals(Connexion.TYPE_SERVEUR_POSTGRES)) {
                Class.forName(Connexion.SERVEUR_POSTGRES_CLASS);
                urlBD = Connexion.SERVEUR_POSTGRES_URL
                    + schema;
            } else if(typeServeur.equals(Connexion.TYPE_SERVEUR_ACCESS)) {
                Class.forName(Connexion.SERVEUR_ACCESS_CLASS);
                urlBD = Connexion.SERVEUR_ACCESS_URL
                    + schema;
            } else {
                throw new IllegalArgumentException("Type de serveur "
                    + typeServeur
                    + "n'est pas valide.");
            }
            setConnection(DriverManager.getConnection(urlBD,
                nomUtilisateur,
                motPasse));

            // Mettre en mode de commit manuel
            getConnection().setAutoCommit(false);

            System.out.println("Ouverture de la connexion en mode read committed (default) :\n"
                + "Heure "
                + System.currentTimeMillis()
                + " "
                + getConnection());
        } catch(ClassNotFoundException classNotFoundException) {
            throw new ConnexionException(classNotFoundException);
        } catch(SQLException sqlException) {
            throw new ConnexionException(sqlException);
        } catch(IllegalArgumentException illegalArgumentException) {
            throw new ConnexionException(illegalArgumentException);
        }
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.connection</code>.
     *
     * @return La variable d'instance <code>this.connection</code>
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Setter de la variable d'instance <code>this.connection</code>.
     *
     * @param connection La valeur Ã  utiliser pour la variable d'instance <code>this.connection</code>
     */
    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws Exception {
        rollback();
        getConnection().close();
        System.out.println("\nConnexion fermÃ©e"
            + " "
            + getConnection());
    }

    /**
     * Effectue un commit sur la {@link java.sql.Connection} JDBC.
     *
     * @throws {@link java.sql.SQLException SQLException} S'il y a une erreur avec la base de donnÃ©es
     */
    public void commit() throws SQLException {
        getConnection().commit();
    }

    /**
     * Effectue un rollback sur la {@link java.sql.Connection} JDBC.
     *
     * @throws {@link java.sql.SQLException SQLException} S'il y a une erreur avec la base de donnÃ©es
     */
    public void rollback() throws SQLException {
        getConnection().rollback();
    }

    /**
      * Retourne la liste des serveurs supportes par ce gestionnaire de connexion :<br /><br />
      * 
      * <code>local</code> :  MySQL installe localement<br />
      * <code>distant</code> : Oracle installe au DÃ©partement d'Informatique du College Ahuntsic<br />
      * <code>postgres</code> : Postgres installe localement<br />
      * <code>access</code> : Microsoft Access installe localement et inscrit dans ODBC
      * 
      * @return La liste des serveurs supportÃ©s par ce gestionnaire de connexion
      */
    public static String getServeursSupportes() {
        return "local : MySQL installe localement\n"
            + "distant : Oracle installe au Departement d'Informatique du College Ahuntsic\n"
            + "postgres : Postgres installe localement\n"
            + "access : Microsoft Access installe localement et inscrit dans ODBC";
    }
}
