// Fichier TestLivreFacade.java
// Auteur : 201176542
// Date de création : 2014-12-05

package test.collegeahuntsic.bibliothequeBackEnd.facade;

import java.sql.Timestamp;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestSuite;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.dto.LivreDTO;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidHibernateSessionException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidPrimaryKeyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dao.InvalidSortByPropertyException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.dto.InvalidDTOException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.facade.FacadeException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingLoanException;
import ca.qc.collegeahuntsic.bibliothequeBackEnd.exception.service.ExistingReservationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import test.collegeahuntsic.bibliothequeBackEnd.exception.TestCaseFailedException;

/**
 * Test cases for Livre.
 *
 * @author Team QuatreCentVingt
 */
public class TestLivreFacade extends TestCase {
    private static final Log LOGGER = LogFactory.getLog(TestLivreFacade.class);

    private static final String TEST_CASE_TITLE = "Livre facade test case"; //$NON-NLS-1$

    private static final String TITRE = "Titre "; //$NON-NLS-1$

    private static final String AUTHOR = "Auteur "; //$NON-NLS-1$

    private static int sequence = 1;

    /**
     * Default Constructor.
     *@param name Nom du test
     * 
     * @throws TestCaseFailedException gérer les tests failures.
     */
    public TestLivreFacade(String name) throws TestCaseFailedException {
        super(name);
    }

    /**
     * Sets the test case up.
     * 
     * @throws Exception If an error occurs
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Tears the test case down.
     * 
     * @throws Exception If an error occurs
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Configures the tests to be executed in this test case. This suite is now visible for a {@link junit.awtui.TestRunner}.<br /><br />
     * The suite contains all test cases from:<br />
     * <ul>
     * <li>{@link test.collegeahuntsic.bibliothequeBackEnd.facade.TestLivreFacade#suite() }
     * </ul>
     * 
     * @return Test The tests to be executed in this test case
     */
    public static Test suite() {
        final TestSuite suite = new TestSuite(TestLivreFacade.TEST_CASE_TITLE);
        suite.addTestSuite(TestLivreFacade.class);
        return suite;
    }

    /**
     * 
     * Test cases pour aquérir un livre.
     *
     * @throws TestCaseFailedException gérer les tests failures.
     */

    public void testAcquerirLivre() throws TestCaseFailedException {
        try {
            beginTransaction();
            final LivreDTO livreDTO = new LivreDTO();
            livreDTO.setTitre(TestLivreFacade.TITRE
                + TestLivreFacade.sequence);
            livreDTO.setAuteur(TestLivreFacade.AUTHOR
                + TestLivreFacade.sequence);
            livreDTO.setDateAcquisition(new Timestamp(System.currentTimeMillis()));
            TestLivreFacade.sequence = TestLivreFacade.sequence + 1;
            getLivreFacade().acquerirLivre(getSession(),
                livreDTO);
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidDTOException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFAiledException) {
                TestLivreFacade.LOGGER.error(testCaseFAiledException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }

    /**
     * Test cases pour get un livre.
     * 
     * @throws TestCaseFailedException gérer les tests failures.
     */

    public void testGetLivre() throws TestCaseFailedException {
        try {
            testAcquerirLivre();
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getDateAcquisition());
            final String idLivre = livreDTO.getIdLivre();
            final String titre = livreDTO.getTitre();
            final String auteur = livreDTO.getAuteur();
            final Timestamp dateAquisition = livreDTO.getDateAcquisition();
            commitTransaction();

            beginTransaction();
            livreDTO = getLivreFacade().getLivre(getSession(),
                idLivre);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getDateAcquisition());
            assertEquals(idLivre,
                livreDTO.getIdLivre());
            assertEquals(titre,
                livreDTO.getTitre());
            assertEquals(auteur,
                livreDTO.getAuteur());
            assertEquals(dateAquisition,
                livreDTO.getDateAcquisition());
            commitTransaction();

            beginTransaction();
            livreDTO = getLivreFacade().getLivre(getSession(),
                "-1");
            assertNull(livreDTO);
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | InvalidPrimaryKeyException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFAiledException) {
                TestLivreFacade.LOGGER.error(testCaseFAiledException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }

    /**
     * 
     * Test cases pour get tout les livres.
     *
     * @throws TestCaseFailedException gérer les tests failures
     */

    public void testGetAllLivres() throws TestCaseFailedException {
        try {
            testAcquerirLivre();
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            assertFalse(livres.isEmpty());
            final LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getDateAcquisition());
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFAiledException) {
                TestLivreFacade.LOGGER.error(testCaseFAiledException);
            }
            TestLivreFacade.LOGGER.error(exception);
        }
    }

    /**
     * 
     * Test cases pour vendre un livre.
     *
     * @throws TestCaseFailedException gérer les tests failures
     */
    public void testVendreLivre() throws TestCaseFailedException {

        testAcquerirLivre();
        try {
            beginTransaction();
            final List<LivreDTO> livres = getLivreFacade().getAllLivres(getSession(),
                LivreDTO.TITRE_COLUMN_NAME);
            commitTransaction();

            assertFalse(livres.isEmpty());
            LivreDTO livreDTO = livres.get(livres.size() - 1);
            assertNotNull(livreDTO);
            assertNotNull(livreDTO.getAuteur());
            assertNotNull(livreDTO.getIdLivre());
            assertNotNull(livreDTO.getTitre());
            assertNotNull(livreDTO.getDateAcquisition());

            beginTransaction();
            getLivreFacade().vendreLivre(getSession(),
                livreDTO);

            livreDTO = getLivreFacade().getLivre(getSession(),
                livreDTO.getIdLivre());

            assertNull(livreDTO);
            assertNull(livreDTO.getAuteur());
            assertNull(livreDTO.getIdLivre());
            assertNull(livreDTO.getTitre());
            assertNull(livreDTO.getDateAcquisition());
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException
            | InvalidDTOException
            | ExistingLoanException
            | ExistingReservationException
            | InvalidPrimaryKeyException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFailedException) {
                TestLivreFacade.LOGGER.error(testCaseFailedException);
            }
        }

    }
}
