package ct414;

public class InvalidQuestionNumber extends Exception {
		
	public InvalidQuestionNumber(int qNum) {
		super("Not a valid question number");
	}
}

