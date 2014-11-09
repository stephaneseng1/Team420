// Fichier IPretService.java
// Auteur : Gilles Bénichou
// Date de création : 2014-09-01

package ca.qc.collegeahuntsic.bibliothequeBackEnd.service.interfaces;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Session;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.PretDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ServiceException;

public interface IPretService extends IService {
    /**
     * Ajoute un nouveau prêt dans la base de données.
     *
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à ajouter
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    void add(Session session,
        PretDTO pretDTO) throws ServiceException;

    /**
     * Lit un prêt à partir de la base de données.
     *
     * @param session La session Hibernate à utiliser
     * @param idPret L'ID du prêt à lire
     * @return Le prêt
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    PretDTO get(Session session,
        String idPret) throws ServiceException;

    /**
     * Met à jour un prêt dans la base de données.
     *
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à mettre à jour
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    void update(Session session,
        PretDTO pretDTO) throws ServiceException;

    /**
     * Supprime un prêt de la base de données.
     *
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à supprimer
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    void delete(Session session,
        PretDTO pretDTO) throws ServiceException;

    /**
     * Trouve tous les prêts de la base de données. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt
     * n'est trouvé, une {@link List} vide est retournée.
     *
     * @param session La session Hibernate à utiliser
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste de tous les prêts ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    List<PretDTO> getAll(Session session,
        String sortByPropertyName) throws ServiceException;

    /**
     * Trouve les prêts à partir d'une date de prêt. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun prêt
     * n'est trouvé, une {@link List} vide est retournée.
     *
     * @param session La session Hibernate à utiliser
     * @param datePret La date de prêt à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    List<PretDTO> findByDatePret(Session session,
        Timestamp datePret,
        String sortByPropertyName) throws ServiceException;

    /**
     * Trouve les prêts à partir d'une date de retour. La liste est classée par ordre croissant sur <code>sortByPropertyName</code>. Si aucun
     * prêt n'est trouvé, une {@link List} vide est retournée.
     *
     * @param session La session Hibernate à utiliser
     * @param dateRetour La date de retour à trouver
     * @param sortByPropertyName The nom de la propriété à utiliser pour classer
     * @return La liste des prêts correspondants ; une liste vide sinon
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    List<PretDTO> findByDateRetour(Session session,
        Timestamp dateRetour,
        String sortByPropertyName) throws ServiceException;

    /**
     * Commence un prêt.
     *
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à commencer
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    void commencer(Session session,
        PretDTO pretDTO) throws ServiceException;

    /**
     * Renouvelle le prêt d'un livre.
     *
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à renouveler
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    void renouveler(Session session,
        PretDTO pretDTO) throws ServiceException;

    /**
     * Termine un prêt.
     *
     * @param session La session Hibernate à utiliser
     * @param pretDTO Le prêt à terminer
     * @throws ServiceException S'il y a une erreur au niveau de la couche Service
     */
    void terminer(Session session,
        PretDTO pretDTO) throws ServiceException;
}
