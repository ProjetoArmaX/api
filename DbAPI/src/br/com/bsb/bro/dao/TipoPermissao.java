package br.com.bsb.bro.dao;

import br.com.bsb.bro.error.ErrorCodes;

public class TipoPermissao {
	
	private int idTipoPermissao;
	private String nomePermissao;
	private String sisCod;
	
	public String getSisCod() {
		return sisCod;
	}
	public void setSisCod(String sisCod) {
		this.sisCod = sisCod;
	}
	private ErrorCodes erro;
	
	public ErrorCodes getErro() {
		return erro;
	}
	public void setErro(ErrorCodes erro) {
		this.erro = erro;
	}
	public int getIdTipoPermissao() {
		return idTipoPermissao;
	}
	public void setIdTipoPermissao(int idTipoPermissao) {
		this.idTipoPermissao = idTipoPermissao;
	}
	public String getNomePermissao() {
		return nomePermissao;
	}
	public void setNomePermissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}
	

}
