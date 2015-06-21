package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para os erros na montagem de relatórios.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
public class MontagemRelatorioException extends Exception {

    private static final long serialVersionUID = 1L;

    public MontagemRelatorioException() {
        super();
    }

    public MontagemRelatorioException(String message, Throwable cause) {
        super(message, cause);
    }

    public MontagemRelatorioException(String message) {
        super(message);
    }

    public MontagemRelatorioException(Throwable cause) {
        super(cause);
    }

} // fim da classe MontagemRelatorioException
