package com.valhala.tarefa.file.converter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.model.Prioridade;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.StatusSla;
import com.valhala.tarefa.model.Tarefa;
import com.valhala.tarefa.model.TipoDemanda;

public final class TarefaStreamConverter implements StreamConverter<Tarefa> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    private TarefaStreamConverter() {
        super();
    }

    @Override
    public Set<Tarefa> converterParaColecao(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Set<Tarefa> tarefas = new HashSet<>();
        String linha;
        int linhaLida = 1;
        try {
            while ((linha = reader.readLine()) != null) {
                String[] atributos = linha.split(";");
                System.out.println(atributos[6]);
                Tarefa tarefa = new Tarefa.Builder(Long.parseLong(atributos[0]), atributos[8], Prioridade.valueOf(atributos[2]), TipoDemanda.valueOf(atributos[3])).
                        equipe(atributos[1] != null && !atributos[1].trim().equals("") ? new Equipe.Builder(atributos[1]).build() : null).
                        abertura(DATE_FORMAT.parse(atributos[4])).
                        status(Status.valueOf(atributos[5])).
                        categoria(atributos[6]).
                        statusSla(StatusSla.valueOf(atributos[7])).
                        colaborador(atributos[9] != null && !atributos[9].trim().equals("") ? new Colaborador.Builder(atributos[9], null).build() : null).build();
                System.out.println("CATEGORIA: " + tarefa.getCategoria());
                tarefas.add(tarefa);
                linhaLida += 1;
            } // fim do bloco while
        } catch (Exception e) {
            throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
        } // fim do bloco try/catch
        return tarefas;
    }

    public static TarefaStreamConverter obterConversorStream() {
        return new TarefaStreamConverter();
    }

}
