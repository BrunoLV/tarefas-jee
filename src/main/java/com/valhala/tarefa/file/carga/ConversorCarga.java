package com.valhala.tarefa.file.carga;

import java.io.InputStream;
import java.util.Set;

import com.valhala.tarefa.file.converter.StreamConverter;

public abstract class ConversorCarga<T> {

    public static <T> Set<T> efetuarConversaoParaCarga(InputStream stream, StreamConverter<T> converter) {
        return converter.converterParaColecao(stream);
    }

}
