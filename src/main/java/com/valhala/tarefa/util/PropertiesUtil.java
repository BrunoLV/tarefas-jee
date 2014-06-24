package com.valhala.tarefa.util;

import com.valhala.tarefa.exceptions.PropertiesException;
import com.valhala.tarefa.qualifiers.Auditavel;

import java.io.IOException;
import java.util.Properties;

/**
 * Classe utilizada ler arquivos de propriedades definidos no ambiente onde a aplicação esta sendo executada.
 *
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/06/2014
 */
@Auditavel
public class PropertiesUtil {

    /**
     * Método utilizado para obter um objeto Properties com as propriedades especificadas no arquivo informado.
     *
     * @param nomeArquivo
     * @return
     * @throws PropertiesException
     */
    public static final Properties getProperties(String nomeArquivo) throws PropertiesException {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResource(nomeArquivo).openStream());
        } catch (IOException e) {
            throw new PropertiesException(e.getMessage(), e);
        } // fim do bloco try/catch
        return properties;
    } // fim do método getProperties

} // fim da classe PropertiesUtil