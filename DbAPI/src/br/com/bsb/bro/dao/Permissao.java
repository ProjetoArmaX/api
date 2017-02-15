package br.com.bsb.bro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.bsb.bro.error.ErrorCodes;
import br.com.bsb.bro.main.Conectar;

public class Permissao {
	
	
	private static final String sqlConsultaPermissaoSubModulo = "			select "+
            "  tp.idTipoPermissao, "+
            "  tp.sisCod, "+
            "  tp.nomePermissao "+
			"from  "+
            "  desenv.subModulo sm "+
			"inner join desenv.usuarioPermissao up "+
            "  on sm.idSubModulo = up.idSubModulo "+
			"inner join desenv.tipoPermissao tp "+
            "  on up.idTipoPermissao = tp.idTipoPermissao "+
			"where sm.sisCod = ? "+
            "and tp.sisCod = ? "+
            "and up.idUsuario = ?";
	
	
	public static SubModulo checarPermissao(Usuario usuarioConectado, String permisao, String modulo){
		SubModulo s = new SubModulo();
		
		BroConnection con = Conectar.getConnection();
		if(!con.isConnected()){
			s.setErro(con.getErro());
			return s;
		}
		
		s = checarPermissao(con, usuarioConectado, permisao, modulo);
		return s;
	}
	
	
	
	private static SubModulo checarPermissao(BroConnection con, Usuario usuario, String permCod, String modCod){
		SubModulo s = new SubModulo();
		
		
		PreparedStatement statement = null;
		try {
			statement = con.getCon().prepareStatement(sqlConsultaPermissaoSubModulo);
			statement.setString(1, modCod);
			statement.setInt(3, usuario.getId());
			statement.setString(2, permCod);
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

}
