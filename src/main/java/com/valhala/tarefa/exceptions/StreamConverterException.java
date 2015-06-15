package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para erro na conversão de streams.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
public class StreamConverterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StreamConverterException() {
        super();
    }

    public StreamConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public StreamConverterException(String message) {
        super(message);
    }

    public StreamConverterException(Throwable cause) {
        super(cause);
    }

} // fim da classe StreamConverterException