package community.exception;

public class ExceptionMsg {
	
	public static final String credentialExceptionMessage = "INVALID CREDENTIAL";
	
	public static final String nullTokenMessage = "PLEASE LOGIN AND SEND ME A TOKEN";
	
	public static final String badRequestParameterExceptionMessage = "CHECK PARAMETER. PARAMETER SHOULD NOT BE NULL AND FOLLOWED BY DEFINED INPUT RULES";
	
	public static final String TargetNullMessage = "YOUR REQUEST TRARGET IS NULL";

	public static final String emailRegex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
	
	public static final String exceptionMessageWithNullValue = "REQUIRED PARAMETER CAN NOT BE NULL, CHECK ID AND PASSWORD";
	
	public static final String exceptionMessageWithNotValidEmail = "CHECK EMAIL PARAMETER";
	
	public static final String exceptionMessage = "CONFLICT OCCURED WHILE PROCESSING, RETRY AGAIN";
	
	public static final String userIsNullExceptionMessage = "NO USER FOUNDED";
	
	public static final String loginFailExceptionMessage = "PLEASE CHECK PASSWORD AND RETRY AGAIN";
}
