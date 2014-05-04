package com.valhala.tarefa.util;

import java.io.IOException;
import java.util.Properties;

import com.valhala.tarefa.exceptions.PropertiesException;
import com.valhala.tarefa.qualifiers.Auditavel;

@Auditavel
public class PropertiesUtil {
	
	public static final Properties getProperties(String nomeArquivo) throws PropertiesException {
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResource(nomeArquivo).openStream());
		} catch (IOException e) {
			throw new PropertiesException(e.getMessage(), e);
		}
		return properties;
	}

}
