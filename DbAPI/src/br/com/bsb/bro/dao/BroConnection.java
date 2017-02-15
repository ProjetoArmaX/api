package br.com.bsb.bro.dao;

import java.sql.Connection;
import br.com.bsb.bro.error.ErrorCodes;

public class BroConnection {
	private Connection con;
	private ErrorCodes erro;

	public BroConnection(Connection con, ErrorCodes erro) {
		super();
		this.con = con;
		this.erro = erro;
	}

	public Connection getCon() {
		return con;
	}

	public ErrorCodes getErro() {
		return erro;
	}

	public boolean isConnected() {
		if (erro == ErrorCodes.OK)
			return true;
		else
			return false;

	}

}
