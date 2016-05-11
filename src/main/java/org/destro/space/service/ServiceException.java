package org.destro.space.service;

/**
 * @author destro
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 8436332439186048478L;

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}
}
