package com.valhala.tarefa.file.converter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.model.Cliente;

public final class ClienteStreamConverter implements StreamConverter<Cliente> {
	
	private ClienteStreamConverter() {
		super();
	}

	@Override
	public Set<Cliente> converterParaColecao(InputStream stream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<Cliente> clientes = new HashSet<>();
        String linha;
        int linhaLida = 1;
        try {
            while ((linha = reader.readLine()) != null) {
                clientes.add(new Cliente.Builder(linha).build());
                linhaLida += 1;
            } // fim do bloco while
        } catch (Exception e) {
            throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
        } // fim do bloco try/catch
        return clientes;
	}
	
	public static ClienteStreamConverter obterConversorStream() {
		return new ClienteStreamConverter();
	}

}
