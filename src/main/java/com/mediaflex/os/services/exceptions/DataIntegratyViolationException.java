package com.mediaflex.os.services.exceptions;

import com.mediaflex.os.services.TecnicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataIntegratyViolationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	Logger log = LoggerFactory.getLogger(TecnicoService.class);

	public DataIntegratyViolationException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegratyViolationException(String message) {
		super(message);
		log.info("SERVICE - BUSCANDO DataIntegratyViolationException");
	}

}
