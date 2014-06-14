package com.valhala.tarefa.exceptions;

/**
 * Exception customizada para os erros da camada DAO.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 04/06/2014
 */
public class DaoException extends Exception {

    private static final long serialVersionUID = 1L;

    public DaoException() {
        super();
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }

} // fim da classe DaoException