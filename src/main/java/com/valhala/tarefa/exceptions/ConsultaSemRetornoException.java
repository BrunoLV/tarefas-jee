package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para consultas que não retornam resultados do banco.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public class ConsultaSemRetornoException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConsultaSemRetornoException() {
    }

    public ConsultaSemRetornoException(String message) {
        super(message);
    }

    public ConsultaSemRetornoException(Throwable cause) {
        super(cause);
    }

    public ConsultaSemRetornoException(String message, Throwable cause) {
        super(message, cause);
    }

} // fim da classe ConsultaSemRetornoException 
