package br.com.bsb.bro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.bsb.bro.error.ErrorCodes;
import br.com.bsb.bro.main.Conectar;

public class SubModuloDao {
	
	private static final String sqlConsultaSubModuloBySiscod = "select idSubModulo, nomeSubModulo, link, sisCod from subModulo where sisCod = ?";
	/*
	private static final String sqlConsultaPermissaoSubModulo = "select " + 
			"  sm.idSubModulo, " +
			"  sm.nomeSubModulo, " +
			"  sm.link, " +
			"  sm.sisCod " +
			"from  " +
			"  subModulo sm " +
			"inner join usuarioPermissao up " + 
			"  on sm.idSubModulo = up.idSubModulo " +
			"  and up.idUsuario = ? " +
			"inner join tipoPermissao tp " +
			"  on up.idTipoPermissao = tp.idTipoPermissao " +
			"  and tp.sisCod = ? " +
			"where " +
			"  sm.sisCod = ?";
			*/
	/*
	public static SubModulo checarPemissao(Usuario usuario, String permCod){
		BroConnection con = Conectar.getConnection();
		SubModulo s = new SubModulo();
		if(!con.isConnected()){
			s.setErro(con.getErro());
			return s;
		}
		
		//VERIFICAR SE USUARIO E SENHA ESTAO CERTOS, E RETORNAR O OBJETO DO USUARIO
		s = checarPermissao(con, usuario, permCod);
		
		
		return s;
	}
	*/
	
	
	public static SubModulo getModulo(String sisCod){
		BroConnection con = Conectar.getConnection();
		SubModulo s = new SubModulo();
		if(!con.isConnected()){
			s.setErro(con.getErro());
			return s;
		}
		s = getSubModulo(con, sisCod);
		return s;
	}
	
	private static SubModulo getSubModulo(BroConnection con, String modCod){
		SubModulo s = new SubModulo();
		
		
		PreparedStatement statement = null;
		try {
			statement = con.getCon().prepareStatement(sqlConsultaSubModuloBySiscod );
			statement.setString(1, modCod);
			ResultSet rs = statement.executeQuery();
			
			if(rs.first()) {
				
				s.setIdSubModulo(rs.getInt(1));
				s.setNomeSubModulo(rs.getString(2));
				s.setLink(rs.getString(3));
				s.setErro(ErrorCodes.OK);
				
			}
			
		} catch (SQLException e) {
			s.setErro(ErrorCodes.SUB_MODULO_NAO_EXISTE);
			e.printStackTrace();
		}   finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					s.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					e.printStackTrace();
					return s;
				}
			}
		} 
		
		return s;
	}
	
	
	/*
	private static SubModulo checarPermissao(BroConnection con, Usuario usuario, String permCod){
		SubModulo s = new SubModulo();
		
		
		PreparedStatement statement = null;
		try {
			statement = con.getCon().prepareStatement(sqlConsultaPermissaoSubModulo);
			statement.setInt(1, usuario.getId());
			statement.setString(2, permCod);
			statement.setString(3, modCod);
			ResultSet rs = statement.executeQuery();
			
			if (rs.first()) {
				
				s.setIdSubModulo(rs.getInt(1));
				s.setNomeSubModulo(rs.getString(2));
				s.setLink(rs.getString(3));
				s.setErro(ErrorCodes.OK);
				
			}else{
				s.setErro(ErrorCodes.ACESSO_AO_SUBMODULO_NEGADO);
			}
			
		} catch (SQLException e) {
			s.setErro(ErrorCodes.ACESSO_AO_SUBMODULO_NEGADO);
			e.printStackTrace();
		}   finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					s.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					e.printStackTrace();
					return s;
				}
			}
		} 
		
		return s;
	}
*/
}
