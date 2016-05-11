package org.destro.space.repository;

/**
 * @author destro
 */
public class RepositoryException extends RuntimeException {

	private static final long serialVersionUID = 8436332439186048478L;

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepositoryException(String message) {
		super(message);
	}
}
