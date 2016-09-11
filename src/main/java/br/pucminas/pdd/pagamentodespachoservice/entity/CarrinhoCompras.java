package br.pucminas.pdd.pagamentodespachoservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarrinhoCompras {
	private Integer id;
	private List<Livro> livros;

	@JsonProperty("Id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("Livros")
	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

}
