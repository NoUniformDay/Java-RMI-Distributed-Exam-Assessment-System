package exceptions;

public class InvalidLoginException extends Exception{
	public InvalidLoginException(int user,String pass) {
		super("Invalid Login Details -> userID : "+user+" Password : "+pass);
	}
}
