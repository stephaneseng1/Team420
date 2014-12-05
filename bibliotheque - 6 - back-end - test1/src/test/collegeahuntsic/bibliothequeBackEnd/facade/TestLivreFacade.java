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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import test.collegeahuntsic.bibliothequeBackEnd.exception.TestCaseFailedException;

/**
 * TODO Auto-generated class javadoc
 *
 * @author Tuan-An Lor
 */
public class TestLivreFacade extends TestCase {
    private static final Log logger = LogFactory.getLog(TestLivreFacade.class);

    private static final String TEST_CASE_TITLE = "All test cases"; //$NON-NLS-1$

    private static final String TITRE = "Titre "; //$NON-NLS-1$

    private static final String AUTHOR = "Auteur "; //$NON-NLS-1$

    private static int sequence = 1;

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

    public static Test suite() {
        final TestSuite suite = new TestSuite(TestLivreFacade.TEST_CASE_TITLE);
        suite.addTestSuite(TestLivreFacade.class);
        return suite;
    }

    /**
     * 
     * testAquerirLivre 
     *
     * @throws TestCaseFailedException gérer les tests failures.
     */

    public void testAquerirLivre() throws TestCaseFailedException {
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
                TestLivreFacade.logger.error(testCaseFAiledException);
            }
            TestLivreFacade.logger.error(exception);
        }
    }

    /**
     * 
     * testGetLivres 
     * 
     * @throws TestCaseFailedException gérer les tests failures.
     */

    public void testGetLivres() throws TestCaseFailedException {
        try {
            testAquerirLivre();
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
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | InvalidPrimaryKeyException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFAiledException) {
                TestLivreFacade.logger.error(testCaseFAiledException);
            }
            TestLivreFacade.logger.error(exception);
        }
    }

    /**
     * 
     * testGetAllLivres 
     *
     * @throws TestCaseFailedException gérer les tests failures
     */

    public void testGetAllLivres() throws TestCaseFailedException {
        try {
            testGetLivres();
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
                TestLivreFacade.logger.error(testCaseFAiledException);
            }
            TestLivreFacade.logger.error(exception);
        }
    }
    public void testUpdateLivres() throws TestCaseFailedException {
        try {
            testAquerirLivre();
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
            final String titre = TestLivreFacade.TITRE;
            final String auteur = TestLivreFacade.AUTHOR;
            final Timestamp dateAquisition = livreDTO.getDateAcquisition();
            commitTransaction();
        } catch(
            InvalidHibernateSessionException
            | InvalidSortByPropertyException
            | FacadeException exception) {
            try {
                rollbackTransaction();
            } catch(TestCaseFailedException testCaseFAiledException) {
                TestLivreFacade.logger.error(testCaseFAiledException);
            }
            TestLivreFacade.logger.error(exception);
        }
    }
}
