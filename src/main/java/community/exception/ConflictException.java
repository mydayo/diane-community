package community.exception;

public class ConflictException extends RuntimeException {
	private static final long serialVersionUID = -1753747954153526070L;

	public ConflictException(String message) {
		super(message);
	}
}