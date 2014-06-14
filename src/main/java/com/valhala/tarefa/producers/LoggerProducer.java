package com.valhala.tarefa.producers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Classe produtora de Loggers na aplicação.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
public class LoggerProducer {

    /**
     * Método produtor de loggers.
     *
     * @param point
     * @return
     */
    @Produces
    public Logger gerarLogger(InjectionPoint point) {
        return LoggerFactory.getLogger(point.getMember().getDeclaringClass().getCanonicalName());
    } // fim do método gerarLogger

} // fim da classe LoggerProducer