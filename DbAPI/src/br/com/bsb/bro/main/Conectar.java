package br.com.bsb.bro.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.bsb.bro.dao.BroConnection;
import br.com.bsb.bro.error.ErrorCodes;

public class Conectar {

	private static final String STR_DRIVER = "com.mysql.jdbc.Driver";
	private static final String USER = "desenv";
	private static final String PASSWORD = "desenv";
	private static final String IP = "192.168.15.7";
	private static final String DATABASE = "desenv";
	private static final String STR_CON = "jdbc:mysql://" + IP + ":3306/" + DATABASE;
	private static Connection conexao;
	private static BroConnection broConnection;
	static{
		//conexao = getConexao();
		abrirConexao();
	}
	
	private static void abrirConexao(){
		try {
			Class.forName(STR_DRIVER);
			conexao = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			broConnection = new BroConnection(conexao, ErrorCodes.OK);
			
			
		} catch (ClassNotFoundException e) {
			broConnection = new BroConnection(null, ErrorCodes.DB_ERROR_DRIVER);
		} catch (SQLException e) {
			broConnection = new BroConnection(null, ErrorCodes.DB_ERROR_CONNECTION);
		}
		
	}
	
	public static BroConnection getConnection(){
		if(broConnection == null || broConnection.getCon() == null)
			abrirConexao();
		return broConnection;
	}

	public static Connection getConexao() throws SQLException {
		Connection conn = null;
		try {
			Class.forName(STR_DRIVER);
			conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
			System.out.println("Conectando...");
			return conn;

		} catch (ClassNotFoundException e) {
			String errorMsg = "Driver nao encontrado";
			throw new SQLException(errorMsg, e);
		} catch (SQLException e) {
			String errorMsg = "Erro ao obter a conexao";
			throw new SQLException(errorMsg, e);
		}
	}

	public static void closeAll(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
