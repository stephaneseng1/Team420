// Fichier IPretFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces;

import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;

/**
 * Interface de façade pour manipuler les prêts dans la base de données.
 *
 * @author Gilles Benichou
 */
public interface IPretFacade extends IFacade {
    /**
     * Commence un prêt.
     * 
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à commencer
     * @throws FacadeException S'il y a une erreur au niveau de la couche Facade
     */
    void commencer(Session session,
        PretDTO pretDTO) throws FacadeException;

    /**
     * Renouvelle le prêt d'un livre.
     * 
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à renouveler
     * @throws FacadeException S'il y a une erreur au niveau de la couche Facade
     */
    void renouveler(Session session,
        PretDTO pretDTO) throws FacadeException;

    /**
     * Termine un prêt.
     * 
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à terminer
     * @throws FacadeException S'il y a une erreur au niveau de la couche Facade
     */
    void terminer(Session session,
        PretDTO pretDTO) throws FacadeException;
}
