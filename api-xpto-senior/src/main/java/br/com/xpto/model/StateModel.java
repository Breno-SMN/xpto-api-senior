package br.com.xpto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateModel {
	
	private String estado;
	private Long qtdeCidades;
	private String descricao;
	
	public StateModel(String estado, Long qtdeCidades) {
		super();
		this.estado = estado;
		this.qtdeCidades = qtdeCidades;
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getQtdeCidades() {
		return qtdeCidades;
	}
	public void setQtdeCidades(Long qtdeCidades) {
		this.qtdeCidades = qtdeCidades;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
