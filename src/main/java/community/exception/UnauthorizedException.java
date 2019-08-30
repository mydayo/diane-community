package community.exception;

public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = -1498765097993500013L;

	public UnauthorizedException() {
		super();
	}
	public UnauthorizedException(String message) {
		super(message);
	}
}


