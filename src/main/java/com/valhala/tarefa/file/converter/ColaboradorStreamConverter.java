package com.valhala.tarefa.file.converter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.model.Atribuicao;
import com.valhala.tarefa.model.Colaborador;

public final class ColaboradorStreamConverter implements StreamConverter<Colaborador> {

    private ColaboradorStreamConverter() {
        super();
    }

    @Override
    public Set<Colaborador> converterParaColecao(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<Colaborador> colaboradores = new HashSet<>();
        String linha;
        int linhaLida = 1;
        try {
            while ((linha = reader.readLine()) != null) {
                String[] atributos = linha.split(";");
                Set<Atribuicao> atribuicoes = new HashSet<>();
                String[] atribuicoesArquivo = atributos[2].split(":");
                for (String atribuicao : atribuicoesArquivo) {
                    atribuicoes.add(Atribuicao.valueOf(atribuicao));
                } // fim do bloco for
                colaboradores.add(new Colaborador.Builder(atributos[0], atributos[1]).atribuicoes(atribuicoes).build());
                linhaLida += 1;
            } // fim do bloco while
        } catch (Exception e) {
            throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
        } // fim do bloco try/catch
        return colaboradores;
    }

    public static ColaboradorStreamConverter obterConversorStream() {
        return new ColaboradorStreamConverter();
    }

}
