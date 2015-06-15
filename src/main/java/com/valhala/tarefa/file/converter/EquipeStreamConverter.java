package com.valhala.tarefa.file.converter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.model.Equipe;

public final class EquipeStreamConverter implements StreamConverter<Equipe> {

	private EquipeStreamConverter() {
		super();
	}
	
	@Override
	public Set<Equipe> converterParaColecao(InputStream stream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<Equipe> equipes = new HashSet<>();
        String linha;
        int linhaLida = 1;
        try {
            while ((linha = reader.readLine()) != null) {
                equipes.add(new Equipe.Builder(linha).build());
                linhaLida += 1;
            } // fim do bloco while
        } catch (Exception e) {
            throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
        } // fim do bloco try/catch
        return equipes;
	}
	
	public static EquipeStreamConverter obterConversorStream() {
		return new EquipeStreamConverter();
	}

}
