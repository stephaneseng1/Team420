// Fichier BibliothequeCreateur.java
// Auteur : Gilles Bénichou
// Date de création : 2014-08-24

package ca.qc.collegeahuntsic.bibliotheque.util;

import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.BibliothequeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.ILivreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IMembreFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IPretFacade;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.facade.interfaces.IReservationFacade;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Utilitaire de création des outils de la bibliothèque.
 * 
 * @author Gilles Benichou
 */
public class BibliothequeCreateur {
    private static final String SPRING_CONFIGURATION_FILE_NAME = "applicationContext.xml";

    private static final String SESSION_FACTORY_NAME = "sessionFactory";

    private static final String LIVRE_FACADE_NAME = "livreFacade";

    private static final String MEMBRE_FACADE_NAME = "membreFacade";

    private static final String PRET_FACADE_NAME = "pretFacade";

    private static final String RESERVATION_FACADE_NAME = "reservationFacade";

    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(BibliothequeCreateur.SPRING_CONFIGURATION_FILE_NAME);

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    private ILivreFacade livreFacade;

    private IMembreFacade membreFacade;

    private IPretFacade pretFacade;

    private IReservationFacade reservationFacade;

    /**
     * Crée le système transactionnel nécessaire à l'application bibliothèque.
     *
     * @throws BibliothequeException S'il y a une erreur avec la base de données
     */
    public BibliothequeCreateur() throws BibliothequeException {
        super();
        try {
            setSessionFactory((SessionFactory) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.SESSION_FACTORY_NAME));
            setMembreFacade((IMembreFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.MEMBRE_FACADE_NAME));
            setLivreFacade((ILivreFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.LIVRE_FACADE_NAME));
            setPretFacade((IPretFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.PRET_FACADE_NAME));
            setReservationFacade((IReservationFacade) BibliothequeCreateur.APPLICATION_CONTEXT.getBean(BibliothequeCreateur.RESERVATION_FACADE_NAME));
        } catch(BeansException beansException) {
            throw new BibliothequeException(beansException);
        }
    }

    // Region Getters and Setters
    /**
     * Getter de la variable d'instance <code>this.sessionFactory</code>.
     *
     * @return La variable d'instance <code>this.sessionFactory</code>
     */
    private SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    /**
     * Setter de la variable d'instance <code>this.sessionFactory</code>.
     *
     * @param sessionFactory1 La valeur à utiliser pour la variable d'instance <code>this.sessionFactory</code>
     */
    private void setSessionFactory(SessionFactory sessionFactory1) {
        this.sessionFactory = sessionFactory1;
    }

    /**
     * Getter de la variable d'instance <code>this.session</code>.
     *
     * @return La variable d'instance <code>this.session</code>
     */
    public Session getSession() {
        return this.session;
    }

    /**
     * Setter de la variable d'instance <code>this.session</code>.
     *
     * @param session1 La valeur à utiliser pour la variable d'instance <code>this.session</code>
     */
    private void setSession(Session session1) {
        this.session = session1;
    }

    /**
     * Getter de la variable d'instance <code>this.transaction</code>.
     *
     * @return La variable d'instance <code>this.transaction</code>
     */
    private Transaction getTransaction() {
        return this.transaction;
    }

    /**
     * Setter de la variable d'instance <code>this.transaction</code>.
     *
     * @param transaction1 La valeur à utiliser pour la variable d'instance <code>this.transaction</code>
     */
    private void setTransaction(Transaction transaction1) {
        this.transaction = transaction1;
    }

    /**
     * Getter de la variable d'instance <code>this.membreFacade</code>.
     *
     * @return La variable d'instance <code>this.membreFacade</code>
     */
    public IMembreFacade getMembreFacade() {
        return this.membreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.membreFacade</code>.
     *
     * @param membreFacade1 La valeur à utiliser pour la variable d'instance <code>this.membreFacade</code>
     */
    private void setMembreFacade(IMembreFacade membreFacade1) {
        this.membreFacade = membreFacade1;
    }

    /**
     * Getter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @return La variable d'instance <code>this.livreFacade</code>
     */
    public ILivreFacade getLivreFacade() {
        return this.livreFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.livreFacade</code>.
     *
     * @param livreFacade1 La valeur à utiliser pour la variable d'instance <code>this.livreFacade</code>
     */
    private void setLivreFacade(ILivreFacade livreFacade1) {
        this.livreFacade = livreFacade1;
    }

    /**
     * Getter de la variable d'instance <code>this.pretFacade</code>.
     *
     * @return La variable d'instance <code>this.pretFacade</code>
     */
    public IPretFacade getPretFacade() {
        return this.pretFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.pretFacade</code>.
     *
     * @param pretFacade1 La valeur à utiliser pour la variable d'instance <code>this.pretFacade</code>
     */
    private void setPretFacade(IPretFacade pretFacade1) {
        this.pretFacade = pretFacade1;
    }

    /**
     * Getter de la variable d'instance <code>this.reservationFacade</code>.
     *
     * @return La variable d'instance <code>this.reservationFacade</code>
     */
    public IReservationFacade getReservationFacade() {
        return this.reservationFacade;
    }

    /**
     * Setter de la variable d'instance <code>this.reservationFacade</code>.
     *
     * @param reservationFacade1 La valeur à utiliser pour la variable d'instance <code>this.reservationFacade</code>
     */
    private void setReservationFacade(IReservationFacade reservationFacade1) {
        this.reservationFacade = reservationFacade1;
    }

    // EndRegion Getters and Setters

    /**
     * Ouvre une session.
     * 
     * @return La session Hibernate
     * @throws BibliothequeException S'il y a une erreur
     */
    private Session openSession() throws BibliothequeException {
        try {
            setSession(getSessionFactory().openSession());
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
        return getSession();
    }

    /**
     * Ouvre une session.
     * 
     * @throws BibliothequeException S'il y a une erreur
     */
    private void closeSession() throws BibliothequeException {
        try {
            getSession().close();
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }

    /**
     * Démarre une transaction.
     * 
     * @throws BibliothequeException S'il y a une erreur
     */
    public void beginTransaction() throws BibliothequeException {
        try {
            setTransaction(openSession().beginTransaction());
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }

    /**
     * Commit une transaction.
     * 
     * @throws BibliothequeException S'il y a une erreur
     */
    public void commitTransaction() throws BibliothequeException {
        try {
            getTransaction().commit();
            closeSession();
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }

    /**
     * Rollback une transaction.
     * 
     * @throws BibliothequeException S'il y a une erreur
     */
    public void rollbackTransaction() throws BibliothequeException {
        try {
            getTransaction().rollback();
            closeSession();
        } catch(HibernateException hibernateException) {
            throw new BibliothequeException(hibernateException);
        }
    }
}
