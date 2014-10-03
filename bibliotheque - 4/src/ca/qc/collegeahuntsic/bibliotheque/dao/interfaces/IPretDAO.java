
package ca.qc.collegeahuntsic.bibliotheque.dao.interfaces;

import java.sql.Timestamp;
import java.util.List;
import ca.qc.collegeahuntsic.bibliotheque.db.Connexion;
import ca.qc.collegeahuntsic.bibliotheque.dto.PretDTO;

public interface IPretDAO extends IDAO {

    List<PretDTO> findByDatePret(Connexion connexion,
        Timestamp datePret,
        String sortByPropertyName);

    List<PretDTO> findByDateRetour(Connexion connexion,
        Timestamp dateRetour,
        String sortByPropertyName);

    List<PretDTO> findByLivre(Connexion connexion,
        String idLivre,
        String sortByPropertyName);

    List<PretDTO> findByMembre(Connexion connexion,
        String idMembre,
        String sortByPropertyName);

}
