package br.com.bsb.bro.dao;

import br.com.bsb.bro.error.ErrorCodes;

public class Empresa {
	
	
	private int idEmpresa;
	private String nomeEmpresa;
	private ErrorCodes erro;
	
	public ErrorCodes getErro() {
		return erro;
	}
	public void setErro(ErrorCodes erro) {
		this.erro = erro;
	}
	public int getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}
	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}
	
	

	
}
