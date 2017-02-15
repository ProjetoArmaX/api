package br.com.bsb.bro.web;

import br.com.bsb.bro.dao.Usuario;

public class Sessao {
	
	private Usuario usuarioConectado;
	private boolean logado;

	public boolean isLogado() {
		return logado;
	}

	public Usuario getUsuarioConectado() {
		return usuarioConectado;
	}

	public Sessao(Usuario usuario) {
		this.usuarioConectado = usuario;
		this.logado = true;
	}

}
