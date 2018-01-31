package exceptions;

public class InvalidQuestionNumber extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidQuestionNumber(int qNum) {
		super("Not a valid question number");
	}
}

