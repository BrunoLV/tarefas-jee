package com.valhala.tarefa.model;

public enum StatusSla {
	
	VIOLADO("Sim"), NAO_VIOLADO("Não");
	
	private String descricaoViolacao;
	
	private StatusSla(final String descricaoViolacao) {
		this.descricaoViolacao = descricaoViolacao;
	}
	
	public String getDescricaoViolacao() {
		return descricaoViolacao;
	}
	
}
