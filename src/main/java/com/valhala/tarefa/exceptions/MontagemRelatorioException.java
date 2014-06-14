package com.valhala.tarefa.exceptions;

/**
 * Created by Bruno Luiz Viana on 07/06/2014.
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

}
