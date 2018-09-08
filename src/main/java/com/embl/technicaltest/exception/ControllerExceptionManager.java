package com.embl.technicaltest.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception manager. Catch common error here.
 * 
 * @author Ashutosh Shimpi
 *
 */
@ControllerAdvice
public class ControllerExceptionManager {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(ControllerExceptionManager.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleServiceException(Exception se) {

		LOG.error(se.getMessage(), se);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing request");
	}
}
