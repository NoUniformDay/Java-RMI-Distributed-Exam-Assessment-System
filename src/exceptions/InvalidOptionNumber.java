package exceptions;

public class InvalidOptionNumber extends Exception {
		
	public InvalidOptionNumber(String reason) {
		super("Invalid Number Chosen");
	}
}

