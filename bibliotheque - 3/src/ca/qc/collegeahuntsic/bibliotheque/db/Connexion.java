
package ca.qc.collegeahuntsic.bibliotheque.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
-
/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.
 *
 * Ce programme ouvrir une connexion avec une BD via JDBC.
 * La méthode serveursSupportes() indique les serveurs supportés.
 *
 * Pré-condition
 *   le driver JDBC approprié doit être accessible.
 *
 * Post-condition
 *   la connexion est ouverte en mode autocommit false et sérialisable, 
 *   (s'il est supporté par le serveur).
 * </pre>
 */
public class Connexion {

    private Connection conn;

    /**
     * Ouverture d'une connexion en mode autocommit false et sérialisable (si supporté)
     * @param serveur serveur SQL de la BD
     * @bd nom de la base de données
     * @user userid sur le serveur SQL
     * @pass mot de passe sur le serveur SQL
     */
    public Connexion(String serveur,
        String bd,
        String user,
        String pass) throws SQLException {
        Driver d = null;
        String urlBD = null;
        try {
            if(serveur.equals("local")) {
                Class.forName("com.mysql.jdbc.Driver");
                urlBD = "jdbc:mysql://localhost:3306/"
                    + bd;
                this.conn = DriverManager.getConnection(urlBD,
                    user,
                    pass);
            } else if(serveur.equals("distant")) {
                d = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:"
                    + bd,
                    user,
                    pass);
                //				this.conn = DriverManager.getConnection("jdbc:oracle:thin:@collegeahuntsic.info:1521:"
                //					+ bd,
                //					user,
                //					pass);
            } else if(serveur.equals("postgres")) {
                d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.registerDriver(d);
                this.conn = DriverManager.getConnection("jdbc:postgresql:"
                    + bd,
                    user,
                    pass);
            } else // access
            {
                d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
                this.conn = DriverManager.getConnection("jdbc:odbc:"
                    + bd,
                    "",
                    "");
            }

            // mettre en mode de commit manuel
            this.conn.setAutoCommit(false);

            System.out.println("Ouverture de la connexion en mode read committed (default) :\n"
                + "Heure "
                + System.currentTimeMillis()
                + " "
                + this.conn);
        }// try

        catch(SQLException e) {
            throw e;
        } catch(Exception e) {
            e.printStackTrace(System.out);
            throw new SQLException("JDBC Driver non instancie");
        }
    }

    /**
     *fermeture d'une connexion
     */
    public void fermer() throws SQLException {
        this.conn.rollback();
        this.conn.close();
        System.out.println("Connexion fermee"
            + " "
            + this.conn);
    }

    /**
     *commit
     */
    public void commit() throws SQLException {
        this.conn.commit();
    }

    /**
     *rollback
     */
    public void rollback() throws SQLException {
        this.conn.rollback();
    }

    /**
     *retourne la Connection jdbc
     */
    public Connection getConnection() {
        return this.conn;
    }

    /**
      * Retourne la liste des serveurs supportés par ce gestionnaire de connexions
      */
    public static String serveursSupportes() {
        return "local : MySQL installé localement\n"
            + "distant : Oracle installé au Département d'Informatique du Collège Ahuntsic\n"
            + "postgres : Postgres installé localement\n"
            + "access : Microsoft Access installé localement et inscrit dans ODBC";
    }
}// Classe Connexion
