package ca.qc.collegeahuntsic.bibliotheque.facade;
/**
 * L'exception BiblioException est levée lorsqu'une transaction est inad�quate.
 * Par exemple -- livre inexistant
 */

public final class BiblioException extends Exception {
	public BiblioException(String message) {
		super(message);
	}
}