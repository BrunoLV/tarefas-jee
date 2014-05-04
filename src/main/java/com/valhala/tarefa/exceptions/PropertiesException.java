package com.valhala.tarefa.exceptions;

public class PropertiesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PropertiesException() {
		super();
	}

	public PropertiesException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertiesException(String message) {
		super(message);
	}

	public PropertiesException(Throwable cause) {
		super(cause);
	}
	
}
