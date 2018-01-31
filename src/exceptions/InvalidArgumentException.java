package exceptions;

public class InvalidArgumentException extends Exception {
	public InvalidArgumentException(int argsLength) {
		super("Invalid Arguments length : " +argsLength);
	}
}
