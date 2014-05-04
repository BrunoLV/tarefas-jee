package com.valhala.tarefa.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.valhala.tarefa.exceptions.StreamConverterException;
import com.valhala.tarefa.model.Atribuicao;
import com.valhala.tarefa.model.Cliente;
import com.valhala.tarefa.model.Colaborador;
import com.valhala.tarefa.model.Equipe;
import com.valhala.tarefa.model.Prioridade;
import com.valhala.tarefa.model.Sistema;
import com.valhala.tarefa.model.Status;
import com.valhala.tarefa.model.Tarefa;

public class StreamConverter {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	
	public static List<Sistema> converterStreamParaListaDeSistema(InputStream stream) throws StreamConverterException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Sistema> sistemas = new ArrayList<Sistema>();
		String linha;
		int linhaLida = 1;
		try {
			while((linha = reader.readLine()) != null) {
				sistemas.add(new Sistema(linha));
				linhaLida += 1;
			} // fim do bloco try/catch
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
		} // fim do bloco try/catch
		return sistemas;
	} // fim do método converterStreamParaListaDeSistema
	
	public static List<Cliente> converterStreamParaListaDeClientes(InputStream stream) throws StreamConverterException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Cliente> clientes = new ArrayList<Cliente>();
		String linha;
		int linhaLida = 1;
		try {
			while((linha = reader.readLine()) != null) {
				clientes.add(new Cliente(linha));
				linhaLida += 1;
			} // fim do bloco while
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
		} // fim do bloco try/catch
		return clientes;
	} // fim do método converterStreamParaListaDeClientes
	
	public static List<Equipe> converterStreamParaListaDeEquipes(InputStream stream) throws StreamConverterException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Equipe> equipes = new ArrayList<Equipe>();
		String linha;
		int linhaLida = 1;
		try {
			while((linha = reader.readLine()) != null) {
				equipes.add(new Equipe(linha));
				linhaLida += 1;
			} // fim do bloco while
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
		} // fim do bloco try/catch
		return equipes;
	} // fim do método converterStreamParaListaDeEquipes
	
	public static List<Colaborador> converterStreamParaListaDeColaboradores(InputStream stream) throws StreamConverterException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Colaborador> colaboradores = new ArrayList<Colaborador>();
		String linha;
		int linhaLida = 1;
		try {
			while((linha = reader.readLine()) != null) {
				String[] atributos = linha.split(";");
				List<Atribuicao> atribuicoes = new ArrayList<Atribuicao>();
				System.out.println(atributos[2]);
				String[] atribuicoesArquivo = atributos[2].split(":");
				System.out.println(atribuicoesArquivo + "Tamanho " + atribuicoesArquivo.length);
				for (String atribuicao : atribuicoesArquivo) {
					System.out.println(atribuicao);
					atribuicoes.add(Atribuicao.valueOf(atribuicao));
				} // fim do bloco for
				colaboradores.add(new Colaborador(null, atributos[0], atributos[1], null, atribuicoes));
				linhaLida += 1;
			} // fim do bloco while
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
		} // fim do bloco try/catch
		return colaboradores;
	} // fim do método converterStreamParaListaDeColaboradores
	
	public static List<Tarefa> converterStreamParaListaDeTarefas(InputStream stream) throws StreamConverterException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		String linha;
		int linhaLida = 1;
		try {
			while((linha = reader.readLine()) != null) {
				String[] atributos = linha.split(";");
				Colaborador colaborador = atributos[5] != null && !atributos[5].trim().equals("") ? new Colaborador(null, atributos[5], null, null) : null;
				Equipe equipe = atributos[7] != null && !atributos[7].trim().equals("") ? new Equipe(atributos[7]) : null; 
				Tarefa tarefa = new Tarefa(
						atributos[0], 
						atributos[6], 
						atributos[2] != null && !atributos[2].trim().equals("") ? Prioridade.valueOf(atributos[2]) : Prioridade.NAO_DEFINIDA, 
								atributos[1] != null && !atributos[1].trim().equals("") ? StreamConverter.DATE_FORMAT.parse(atributos[1]) : new Date(), 
										null, 
										null, 
										null, 
										atributos[4] != null && !atributos[4].trim().equals("") ? Status.valueOf(atributos[4]) : Status.NAO_INICIADO, 
												null, 
												null,
												null, 
												colaborador, 
												null, 
												equipe, 
												null);
				tarefas.add(tarefa);
				linhaLida += 1;
			} // fim do blovo while
		} catch (IOException | ParseException | ArrayIndexOutOfBoundsException e) {
			throw new StreamConverterException(String.format("Ocorreu um erro de processamento na linha %d - Erro: %s", linhaLida, e.getMessage()), e);
		} // fim do bloco try/catch
		return tarefas;
	} // fim do método converterStreamParaListaDeTarefas

} // fim da classe StreamConverter