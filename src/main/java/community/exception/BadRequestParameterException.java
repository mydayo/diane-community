package community.exception;

public class BadRequestParameterException extends RuntimeException {
	
	private static final long serialVersionUID = 8103413549802792641L;
	
	public BadRequestParameterException() {
		super(ExceptionMsg.badRequestParameterExceptionMessage);
	}
	
	public BadRequestParameterException(String message) {
		super(message);
	}
}