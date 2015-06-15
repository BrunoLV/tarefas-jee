package com.valhala.tarefa.file.converter;

import java.io.InputStream;
import java.util.Set;

public interface StreamConverter<T> {
	
	Set<T> converterParaColecao(final InputStream stream);

}
