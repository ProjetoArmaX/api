package br.com.bsb.bro.dao;

import java.util.ArrayList;

import br.com.bsb.bro.error.ErrorCodes;

public class Modulo {
	private int idModulo;
	private String nomeModulo;
	private ArrayList<SubModulo> subModulos;
	private ErrorCodes erro;
	public ErrorCodes getErro() {
		return erro;
	}
	public void setErro(ErrorCodes erro) {
		this.erro = erro;
	}
	public ArrayList<SubModulo> getSubModulos() {
		return subModulos;
	}
	public void setSubModulos(ArrayList<SubModulo> subModulos) {
		this.subModulos = subModulos;
	}
	public void addSubModulo(SubModulo subModulo){
		if(this.subModulos == null)
			this.subModulos = new ArrayList<SubModulo>();
		this.subModulos.add(subModulo);
	}
	public int getIdModulo() {
		return idModulo;
	}
	public void setIdModulo(int idModulo) {
		this.idModulo = idModulo;
	}
	public String getNomeModulo() {
		return nomeModulo;
	}
	public void setNomeModulo(String nomeModulo) {
		this.nomeModulo = nomeModulo;
	}
	

}
