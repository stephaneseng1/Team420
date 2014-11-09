// Fichier PretFacade.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.implementations;

import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.InvalidServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces.IPretService;

/**
 * Facade pour interagir avec le service de prêts.
 *
 * @author Gilles Benichou
 */
public class PretFacade extends Facade implements IPretFacade {
    private IPretService pretService;

    /**
     * Crée la façade de la table <code>pret</code>.
     * 
     * @param pretService Le service de la table <code>pret</code>
     * @throws FacadeException S'il y a une erreur au niveau de la couche Facade
     */
    PretFacade(IPretService pretService) throws FacadeException {
        super();
        if(pretService == null) {
            throw new FacadeException(new InvalidServiceException("Le service de prêts ne peut être null"));
        }
        setPretService(pretService);
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.pretService</code>.
     *
     * @return La variable d'instance <code>this.pretService</code>
     */
    private IPretService getPretService() {
        return this.pretService;
    }

    /**
     * Setter de la variable d'instance <code>this.pretService</code>.
     *
     * @param pretService La valeur à utiliser pour la variable d'instance <code>this.pretService</code>
     */
    private void setPretService(IPretService pretService) {
        this.pretService = pretService;
    }

    // EndRegion Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void commencer(Session session,
        PretDTO pretDTO) throws FacadeException {
        try {
            getPretService().commencer(session,
                pretDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void renouveler(Session session,
        PretDTO pretDTO) throws FacadeException {
        try {
            getPretService().renouveler(session,
                pretDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void terminer(Session session,
        PretDTO pretDTO) throws FacadeException {
        try {
            getPretService().terminer(session,
                pretDTO);
        } catch(ServiceException serviceException) {
            throw new FacadeException(serviceException);
        }
    }
}
