package org.destro.space;

/**
 * Exception de validação
 * 
 * @author destro
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 8436332439186048478L;

	private int errorCode;

	public ValidationException(ValidationErrorCode errorCode, String message) {
		super(errorCode.getMessage() + ", " + message);

		this.errorCode = errorCode.getCode();

	}

	public int getErrorCode() {
		return errorCode;
	}

	public enum ValidationErrorCode {

		NAME_ALREADY_EXISTS(100, "Name Already exists"), 
		OUT_OF_BORDER(200, "Out of border"),
		NOT_ALLOWED_TO_LAND(300, "Not allowed to land"),
		INVALID_DIRECTION(400, "Invalid Direction"),
		NAME_NOT_EXISTS(500, "Name Already exists"),
		INVALID_COMMAND(600, "Invalid command");

		private int code;

		private String message;

		ValidationErrorCode(int code, String message) {
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}
	}
}
