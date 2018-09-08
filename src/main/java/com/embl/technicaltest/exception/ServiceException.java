package com.embl.technicaltest.exception;

/**
 * Exception to be thrown in case of program failure
 */
public class ServiceException extends RuntimeException {

	static final long serialVersionUID = -3387516993334229948L;

	private ServiceException(String message) {

		super(message);
	}

	public static ServiceException newInstance(String message) {

		return new ServiceException(message);
	}
}
