package exceptions;

public class InvalidLoginException extends Exception{
	public InvalidLoginException(String user,String pass) {
		super("Invalid Login Details -> Username : "+user+" Password : "+pass);
	}
}
