package com.valhala.tarefa.relatorio;

import java.util.List;
import java.util.Map;

import com.valhala.tarefa.model.Tarefa;

public interface RelatorioTarefa {
	
	RelatorioVO emitir(Map<String, List<Tarefa>> mapaParaRelatorio);

}
