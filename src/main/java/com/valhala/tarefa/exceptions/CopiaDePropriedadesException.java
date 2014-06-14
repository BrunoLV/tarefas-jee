package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para erro de cópia de propriedades.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public class CopiaDePropriedadesException extends Exception {

    private static final long serialVersionUID = 1L;

    public CopiaDePropriedadesException() {
    }

    public CopiaDePropriedadesException(String message) {
        super(message);
    }

    public CopiaDePropriedadesException(Throwable cause) {
        super(cause);
    }

    public CopiaDePropriedadesException(String message, Throwable cause) {
        super(message, cause);
    }

} // fim da classe CopiaDePropriedadesException