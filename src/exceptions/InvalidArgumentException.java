package exceptions;

public class InvalidArgumentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidArgumentException(int argsLength) {
		super("Invalid Arguments length : " +argsLength);
	}
}
