package br.pucminas.pdd.pagamentodespachoservice.entity;

import java.util.List;

public class SolicitacaoDespacho {
	private Integer id;
	private Livro livro;
	private String endereco;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

}
