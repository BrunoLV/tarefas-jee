package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para erros ocorridos na camada de negócio.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
public class ServiceException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ServiceException() {
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

} // fim da classe ServiceException