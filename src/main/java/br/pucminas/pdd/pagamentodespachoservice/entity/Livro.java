package br.pucminas.pdd.pagamentodespachoservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Livro {
	private Integer id;
	private String titulo;
	private String autor;
	private Double preco;

	@JsonProperty("Id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("Titulo")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@JsonProperty("Autor")
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	@JsonProperty("Preco")
	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

}
