
package ca.qc.collegeahuntsic.bibliotheque.util;

import java.sql.SQLException;
import java.sql.Statement;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.exception.BDCreateurException;
import ca.qc.collegeahuntsic.bibliotheque.exception.ConnexionException;

/**
 *<pre>
 *
 *Permet de créer la BD utilisee par Biblio.java.
 *
 *Parametres:0- serveur SQL 
 *           1- bd nom de la BD 
 *           2- user id pour etablir une connexion avec le serveur SQL
 *           3- mot de passe pour le user id
 *</pre>
 */
class BDCreateur {
    public static void main(String args[]) throws BDCreateurException {

        final String dropSeqIdRes = "DROP SEQUENCE SEQ_ID_RESERVATION";
        final String dropSeqIdPret = "DROP SEQUENCE SEQ_ID_PRET";
        final String dropSeqIdLivre = "DROP SEQUENCE SEQ_ID_LIVRE";
        final String dropSeqIdMembre = "DROP SEQUENCE SEQ_ID_MEMBRE";

        final String dropTableRes = "DROP TABLE reservation CASCADE CONSTRAINTS PURGE";
        final String dropTablePret = "DROP TABLE pret        CASCADE CONSTRAINTS PURGE";
        final String dropTableLivre = "DROP TABLE livre       CASCADE CONSTRAINTS PURGE";
        final String dropTableMembre = "DROP TABLE membre      CASCADE CONSTRAINTS PURGE";

        final String createSeqIdMembre = "CREATE SEQUENCE SEQ_ID_MEMBRE      START WITH 1 INCREMENT BY 1";
        final String createSeqIdLivre = "CREATE SEQUENCE SEQ_ID_LIVRE       START WITH 1 INCREMENT BY 1";
        final String createSeqIdPret = "CREATE SEQUENCE SEQ_ID_PRET        START WITH 1 INCREMENT BY 1";
        final String createSeqIdRes = "CREATE SEQUENCE SEQ_ID_RESERVATION START WITH 1 INCREMENT BY 1";

        final String createTableMembre = "CREATE TABLE membre (idMembre   NUMBER(3)    CHECK (idMembre > 0), "
            + "nom        VARCHAR(100) NOT NULL, "
            + "telephone  NUMBER(10), "
            + "limitePret NUMBER(2)    CHECK (limitePret > 0 AND limitePret <= 10), "
            + "nbPret     NUMBER(2)    DEFAULT 0 CHECK (nbpret >= 0), "
            + "CONSTRAINT cleMembre    PRIMARY KEY (idMembre), "
            + "CONSTRAINT limiteNbPret CHECK (nbPret <= limitePret))";

        final String createTableLivre = "CREATE TABLE livre (idLivre         NUMBER(3)    CHECK (idLivre > 0), "
            + "titre           VARCHAR(100) NOT NULL, "
            + "auteur          VARCHAR(100) NOT NULL, "
            + "dateAcquisition TIMESTAMP    NOT NULL, "
            + "CONSTRAINT      cleLivre     PRIMARY KEY (idLivre))";

        final String createTablePret = "CREATE TABLE pret (idPret     NUMBER(3)  CHECK (idPret > 0), "
            + "idMembre   NUMBER(3)  CHECK (idMembre > 0), "
            + "idLivre    NUMBER(3)  CHECK (idLivre > 0), "
            + "datePret   TIMESTAMP, "
            + "dateRetour TIMESTAMP, "
            + "CONSTRAINT clePrimairePret PRIMARY KEY (idPret), "
            + "CONSTRAINT refPretMembre   FOREIGN KEY (idMembre) REFERENCES membre (idMembre) ON DELETE CASCADE, "
            + "CONSTRAINT refPretLivre    FOREIGN KEY (idLivre)  REFERENCES livre (idLivre)   ON DELETE CASCADE)";

        final String createTableRes = "CREATE TABLE reservation (idReservation   NUMBER(3)  CHECK (idReservation > 0), "
            + "idMembre        NUMBER(3)  CHECK (idMembre > 0), "
            + "idLivre         NUMBER(3)  CHECK (idLivre > 0), "
            + "dateReservation TIMESTAMP, "
            + "CONSTRAINT      clePrimaireReservation  PRIMARY KEY (idReservation), "
            + "CONSTRAINT      cleEtrangereReservation UNIQUE (idMembre, idLivre), "
            + "CONSTRAINT      refReservationMembre    FOREIGN KEY (idMembre) REFERENCES membre (idMembre) ON DELETE CASCADE, "
            + "CONSTRAINT      refReservationLivre     FOREIGN KEY (idLivre)  REFERENCES livre (idLivre)";

        if(args.length < 4) {
            System.out.println("Usage : java BDCreateur <type_serveur> <nom_schema> <nom_utilisateur> <mot_passe>");
        } else {
            try(
                Connexion connexion = new Connexion(args[0],
                    args[1],
                    args[2],
                    args[3])) {

                try(
                    Statement statement = connexion.getConnection().createStatement()) {

                    statement.executeUpdate(dropSeqIdRes);
                    statement.executeUpdate(dropSeqIdPret);
                    statement.executeUpdate(dropSeqIdMembre);
                    statement.executeUpdate(dropSeqIdLivre);

                    statement.executeUpdate(dropTableRes);
                    statement.executeUpdate(dropTablePret);
                    statement.executeUpdate(dropTableMembre);
                    statement.executeUpdate(dropTableLivre);

                    statement.executeUpdate(createSeqIdRes);
                    statement.executeUpdate(createSeqIdPret);
                    statement.executeUpdate(createSeqIdMembre);
                    statement.executeUpdate(createSeqIdLivre);

                    statement.executeUpdate(createTableRes);
                    statement.executeUpdate(createTablePret);
                    statement.executeUpdate(createTableMembre);
                    statement.executeUpdate(createTableLivre);

                    connexion.commit();

                }
            } catch(ConnexionException connexionException) {
                throw new BDCreateurException(connexionException);
            } catch(SQLException sqlException) {
                throw new BDCreateurException(sqlException);
            } catch(Exception exception) {
                throw new BDCreateurException(exception);
            }
        }
    }
}
