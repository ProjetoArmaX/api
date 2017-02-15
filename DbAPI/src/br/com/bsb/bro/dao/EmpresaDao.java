package br.com.bsb.bro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.bsb.bro.error.ErrorCodes;
import br.com.bsb.bro.main.Conectar;

public class EmpresaDao {
	
	private static final String sqlEmpresaDoUsuario = "select e.idempresa, e.nomeEmpresa from empresa e inner join usuarioEmpresa ue on e.idEmpresa = ue.idEmpresa inner join desenv.usuario u on u.idUsuario = ue.idUsuario and ue.idUsuario = ? ";


	
	public Empresa empresaUsuario(Usuario u){
		BroConnection con = Conectar.getConnection();
		Empresa empresa = new Empresa();
		if(!con.isConnected()){
			empresa.setErro(con.getErro());
			return empresa;
		}
		
		empresa = empresaUsuario(con, u);
		return empresa;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	protected static Empresa empresaUsuario(BroConnection con, Usuario usuario){
		Empresa empresa = new Empresa();
		PreparedStatement stmt = null;
		try {
			stmt = con.getCon().prepareStatement(sqlEmpresaDoUsuario);
			
			stmt.setInt(1, usuario.getId());
			ResultSet rs = stmt.executeQuery();
			
			if(rs.first()){
				empresa.setIdEmpresa(rs.getInt(1));
				empresa.setNomeEmpresa(rs.getString(2));
				empresa.setErro(ErrorCodes.OK);
			}else{
				empresa.setErro(ErrorCodes.USER_NO_COMPANY);
			}
			
			
			
			
		} catch (SQLException e) {
			empresa.setErro(ErrorCodes.ERROR_COMPANY);
			e.printStackTrace();
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					empresa.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					e.printStackTrace();
					return empresa;
				}
			}
		}
		return empresa;
		
	}

}
