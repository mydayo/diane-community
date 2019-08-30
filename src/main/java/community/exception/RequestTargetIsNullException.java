package community.exception;

public class RequestTargetIsNullException extends RuntimeException {

	private static final long serialVersionUID = -6926675840605835922L;

	public RequestTargetIsNullException(String target) {
		super(target + ExceptionMsg.TargetNullMessage);
	}
	
}
