package com.valhala.tarefa.relatorio;

import java.io.Serializable;

public class RelatorioVO implements Serializable {
	
	private static final long serialVersionUID = 7733347730372859698L;
	
	private String nome;
	private String caminho;
	private boolean arquivoGerado;
	
	public String getNome() {
		return nome;
	}
	
	public String getCaminho() {
		return caminho;
	}
	
	public boolean isArquivoGerado() {
		return arquivoGerado;
	}
	
	private RelatorioVO() {
		super();
	}
	
	public static RelatorioVO instanciarRelatorio(String nome, String caminho, boolean arquivoGerado) {
		RelatorioVO relatorioVO = new RelatorioVO();
		relatorioVO.nome = nome;
		relatorioVO.caminho = caminho;
		relatorioVO.arquivoGerado = arquivoGerado;
		return relatorioVO;
	}

}
