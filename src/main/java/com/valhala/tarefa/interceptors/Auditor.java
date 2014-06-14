package com.valhala.tarefa.interceptors;

import com.valhala.tarefa.qualifiers.Auditavel;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

/**
 * Interceptador utilizado para interceptar a execução de métodos das classes que precisam ser auditadas.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 */
@Interceptor
@Auditavel
public class Auditor {

    @Inject
    private Logger logger;

    /**
     * Método utilizado para auditar as via log as operações executadas no sistema.
     *
     * @param context
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object auditar(InvocationContext context) throws Exception {
        Method method = context.getMethod();
        Object target = context.getTarget();
        Object[] params = context.getParameters();

        logger.info("############################# INICIO AUDITORIA #############################");
        logger.info(String.format("Classe:  '%s'", target.getClass().getName()));
        logger.info(String.format("Objeto: '%s'", target));
        logger.info(String.format("Parametros: '%s'", params == null || params.length <= 0 ? null : params));
        logger.info(String.format("Método: '%s'", method));
        logger.info("====> CHAMANDO O MÉTODO");
        Object retorno;
        try {
            retorno = context.proceed();
        } catch (Exception e) {
            logger.error("====> OCORREU UM ERRO DURANTE A EXECUÇÃO");
            logger.error(String.format("Erro: '%s'", e.getMessage()), e);
            throw e;
        } // fim do bloco try/catch
        logger.info(String.format("Retorno: '%s'", retorno));
        logger.info("############################# FINAL AUDITORIA  #############################");
        return retorno;
    } // fim do método auditar

} // fim da classe Auditor