package exceptions;

public class InvalidLoginException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidLoginException(int user,String pass) {
		super("Invalid Login Details -> userID : "+user+" Password : "+pass);
	}
}
