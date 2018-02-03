package exceptions;

public class InvalidSessionException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidSessionException() {
        super("Your session has timed out after 5 minutes of inactivity. Please Log In again");
    }
}
