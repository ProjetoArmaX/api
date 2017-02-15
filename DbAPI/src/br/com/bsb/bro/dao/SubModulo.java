package br.com.bsb.bro.dao;

import java.util.ArrayList;

import br.com.bsb.bro.error.ErrorCodes;

public class SubModulo {
	
	private ErrorCodes erro;
	public ErrorCodes getErro() {
		return erro;
	}
	public void setErro(ErrorCodes erro) {
		this.erro = erro;
	}
	private int idSubModulo;
	private String nomeSubModulo;
	private String link;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	private Modulo modulo;
	private ArrayList<TipoPermissao> permissoes;
	public int getIdSubModulo() {
		return idSubModulo;
	}
	public void setIdSubModulo(int idSubModulo) {
		this.idSubModulo = idSubModulo;
	}
	public String getNomeSubModulo() {
		return nomeSubModulo;
	}
	public void setNomeSubModulo(String nomeSubModulo) {
		this.nomeSubModulo = nomeSubModulo;
	}
	public Modulo getModulo() {
		return modulo;
	}
	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	public ArrayList<TipoPermissao> getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(ArrayList<TipoPermissao> permissoes) {
		this.permissoes = permissoes;
	}
	public void addPermissoes(TipoPermissao permissao){
		if(this.permissoes == null )
			this.permissoes = new ArrayList<TipoPermissao>();
		this.permissoes.add(permissao);
	}

}
