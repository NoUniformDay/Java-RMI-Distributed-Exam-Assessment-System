package ct414;

public class UnauthorizedAccess extends Exception {

	public UnauthorizedAccess(String reason) {
		super("Unauthorized Access : "+reason);
	}
}

