package com.valhala.tarefa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.valhala.tarefa.exceptions.CopiaDePropriedadesException;

/**
 * Classe utilitaria para copia de propriedades entre objetos.
 * @author Bruno Luiz Viana
 * @version 1.0
 * @since 23/02/2014
 *
 */
public class Copiador {
	
	public Copiador() {
	} // fim do método construtor
	
	/**
	 * Método utilizado para realizar a cópia de propriedades entre objetos por reflexão.
	 * @param classe
	 * @param objetoDestino
	 * @param objetoOrigem
	 * @throws CopiaDePropriedadesException
	 */
	public static void copiar(Class<?> classe, Object objetoDestino, Object objetoOrigem) throws CopiaDePropriedadesException {
		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {
			try {
				if (Modifier.isStatic(field.getModifiers())) {
					continue;
				} // fim do bloco if
				field.setAccessible(true);
				Object object = field.get(objetoOrigem);
				if (object == null) {
					continue;
				} // fim do bloco if
				field.set(objetoDestino, object);
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NullPointerException e) {
				throw new CopiaDePropriedadesException(e.getMessage(), e);
			}  // fim do bloco try/catch
		} // fim do bloco for
	} // fim do método copiar
	
} // fim da classe Copiador