package org.destro.space;

/**
 * Exception de validação
 * 
 * @author destro
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 8436332439186048478L;

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}
}
