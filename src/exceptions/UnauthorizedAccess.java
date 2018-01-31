package exceptions;

public class UnauthorizedAccess extends Exception {

	private static final long serialVersionUID = 1L;

	public UnauthorizedAccess(String reason) {
		super("Unauthorized Access : "+reason);
	}
}

