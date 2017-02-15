package br.com.bsb.bro.dao;

import br.com.bsb.bro.error.ErrorCodes;

public class Usuario {


	private int id;
	private String nome;
	private String senha;
	private String email;
	private ErrorCodes erro;
	private Empresa empresa;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public ErrorCodes getErro() {
		return erro;
	}

	public void setErro(ErrorCodes erro) {
		this.erro = erro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}



}
