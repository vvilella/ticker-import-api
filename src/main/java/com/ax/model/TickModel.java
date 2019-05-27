package com.ax.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tick")
public class TickModel implements Serializable{

	private static final long serialVersionUID = 2539796355775596280L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "dataSessao")
	private	Date dataSessao;
	
	@Column(name = "simbolo")
	private	String simbolo;
	
	@Column(name = "numeroNegocio")
	private	Long numeroNegocio;
	
	@Column(name = "precoNegocio")
	private	BigDecimal precoNegocio;
	
	@Column(name = "quantidade")
	private	Long quantidade;
	
	@Column(name = "hora")
	private	Timestamp hora;
	
	@Column(name = "indicadorAnulacao")
	private	String indicadorAnulacao;
	
	@Column(name = "dataOfertaCompra")
	private	Date dataOfertaCompra;
	
	@Column(name = "sequenciaOfertaCompra")
	private	Long sequenciaOfertaCompra;
	
	@Column(name = "generationIdCompra")
	private	Long generationIdCompra;
	
	@Column(name = "condicaoOfertaCompra")
	private	String condicaoOfertaCompra;
	
	@Column(name = "dataOfertaVenda")
	private	Date dataOfertaVenda;
	
	@Column(name = "sequenciaOfertaVenda")
	private	Long sequenciaOfertaVenda;
	
	@Column(name = "generationIdVenda")
	private	Long generationIdVenda;
	
	@Column(name = "condicaoOfertaVenda")
	private	String condicaoOfertaVenda;
	
	@Column(name = "indicadorDireto")
	private	String indicadorDireto;
	
	@Column(name = "corretoraCompra")
	private	int corretoraCompra;
	
	@Column(name = "corretoraVenda")
	private	int corretoraVenda;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataSessao() {
		return dataSessao;
	}

	public void setDataSessao(Date dataSessao) {
		this.dataSessao = dataSessao;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Long getNumeroNegocio() {
		return numeroNegocio;
	}

	public void setNumeroNegocio(Long numeroNegocio) {
		this.numeroNegocio = numeroNegocio;
	}

	public BigDecimal getPrecoNegocio() {
		return precoNegocio;
	}

	public void setPrecoNegocio(BigDecimal precoNegocio) {
		this.precoNegocio = precoNegocio;
	}

	public Long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Long quantidade) {
		this.quantidade = quantidade;
	}

	public Timestamp getHora() {
		return hora;
	}

	public void setHora(Timestamp hora) {
		this.hora = hora;
	}

	public String getIndicadorAnulacao() {
		return indicadorAnulacao;
	}

	public void setIndicadorAnulacao(String indicadorAnulacao) {
		this.indicadorAnulacao = indicadorAnulacao;
	}

	public Date getDataOfertaCompra() {
		return dataOfertaCompra;
	}

	public void setDataOfertaCompra(Date dataOfertaCompra) {
		this.dataOfertaCompra = dataOfertaCompra;
	}

	public Long getSequenciaOfertaCompra() {
		return sequenciaOfertaCompra;
	}

	public void setSequenciaOfertaCompra(Long sequenciaOfertaCompra) {
		this.sequenciaOfertaCompra = sequenciaOfertaCompra;
	}

	public Long getGenerationIdCompra() {
		return generationIdCompra;
	}

	public void setGenerationIdCompra(Long generationIdCompra) {
		this.generationIdCompra = generationIdCompra;
	}

	public String getCondicaoOfertaCompra() {
		return condicaoOfertaCompra;
	}

	public void setCondicaoOfertaCompra(String condicaoOfertaCompra) {
		this.condicaoOfertaCompra = condicaoOfertaCompra;
	}

	public Date getDataOfertaVenda() {
		return dataOfertaVenda;
	}

	public void setDataOfertaVenda(Date dataOfertaVenda) {
		this.dataOfertaVenda = dataOfertaVenda;
	}

	public Long getSequenciaOfertaVenda() {
		return sequenciaOfertaVenda;
	}

	public void setSequenciaOfertaVenda(Long sequenciaOfertaVenda) {
		this.sequenciaOfertaVenda = sequenciaOfertaVenda;
	}

	public Long getGenerationIdVenda() {
		return generationIdVenda;
	}

	public void setGenerationIdVenda(Long generationIdVenda) {
		this.generationIdVenda = generationIdVenda;
	}

	public String getCondicaoOfertaVenda() {
		return condicaoOfertaVenda;
	}

	public void setCondicaoOfertaVenda(String condicaoOfertaVenda) {
		this.condicaoOfertaVenda = condicaoOfertaVenda;
	}

	public String getIndicadorDireto() {
		return indicadorDireto;
	}

	public void setIndicadorDireto(String indicadorDireto) {
		this.indicadorDireto = indicadorDireto;
	}

	public int getCorretoraCompra() {
		return corretoraCompra;
	}

	public void setCorretoraCompra(int corretoraCompra) {
		this.corretoraCompra = corretoraCompra;
	}

	public int getCorretoraVenda() {
		return corretoraVenda;
	}

	public void setCorretoraVenda(int corretoraVenda) {
		this.corretoraVenda = corretoraVenda;
	}
	
	

}
