package com.embl.technicaltest.exception;

/**
 * Exception to be thrown if entity not found.
 *
 */
public class EntityNotFoundException extends Exception {

	static final long serialVersionUID = -3387516993334229948L;

	private EntityNotFoundException(String message) {

		super(message);
	}

	public static EntityNotFoundException newInstance(String message) {

		return new EntityNotFoundException(message);
	}

}
