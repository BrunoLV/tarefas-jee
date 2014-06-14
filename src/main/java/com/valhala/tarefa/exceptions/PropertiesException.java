package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para erros na leitura dos properties.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
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

} // fim da classe PropertiesException