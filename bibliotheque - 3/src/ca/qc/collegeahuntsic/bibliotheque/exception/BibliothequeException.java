package ca.qc.collegeahuntsic.bibliotheque.exception;
/**
 * L'exception BiblioException est levée lorsqu'une transaction est inad�quate.
 * Par exemple -- livre inexistant
 */

public final class BibliothequeException extends Exception {
	public BibliothequeException(String message) {
		super(message);
	}
}