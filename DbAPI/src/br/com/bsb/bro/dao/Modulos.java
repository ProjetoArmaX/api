package br.com.bsb.bro.dao;

import java.util.ArrayList;

import br.com.bsb.bro.error.ErrorCodes;

public class Modulos {
	private ErrorCodes erro;
	private ArrayList<Modulo> modulos;
	public ErrorCodes getErro() {
		return erro;
	}
	public void setErro(ErrorCodes erro) {
		this.erro = erro;
	}
	public ArrayList<Modulo> getModulos() {
		return modulos;
	}
	public void setModulos(ArrayList<Modulo> modulos) {
		this.modulos = modulos;
	}
	public void addModulo(Modulo modulo){
		if(this.modulos == null)
			this.modulos = new ArrayList<Modulo>();
		this.modulos.add(modulo);
	}

}
