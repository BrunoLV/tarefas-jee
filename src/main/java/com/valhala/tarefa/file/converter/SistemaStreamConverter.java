package com.valhala.tarefa.file.converter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.model.Sistema;

public final class SistemaStreamConverter implements StreamConverter<Sistema> {

    private SistemaStreamConverter() {
        super();
    }

    @Override
    public Set<Sistema> converterParaColecao(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<Sistema> sistemas = new HashSet<>();
        String linha;
        int linhaLida = 1;
        try {
            while ((linha = reader.readLine()) != null) {
                sistemas.add(new Sistema.Builder(linha).build());
                linhaLida += 1;
            } // fim do bloco try/catch
        } catch (Exception e) {
            throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
        } // fim do bloco try/catch
        return sistemas;
    }

    public static SistemaStreamConverter obterConversorStream() {
        return new SistemaStreamConverter();
    }

}
