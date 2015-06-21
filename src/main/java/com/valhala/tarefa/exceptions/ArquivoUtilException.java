package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para erro na conversão de streams.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
public class ArquivoUtilException extends Exception {

    private static final long serialVersionUID = 1L;

    public ArquivoUtilException() {
        super();
    }

    public ArquivoUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArquivoUtilException(String message) {
        super(message);
    }

    public ArquivoUtilException(Throwable cause) {
        super(cause);
    }

} // fim da classe StreamConverterException
