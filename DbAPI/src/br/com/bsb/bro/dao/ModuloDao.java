package br.com.bsb.bro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.bsb.bro.error.ErrorCodes;
import br.com.bsb.bro.main.Conectar;

public class ModuloDao {
	
	private static final String sqlConsultaModulos = "select "+
			"  m.idModulo as idModulo, "+
			"  m.nomeModulo as nomeModulo "+
			"from usuario u "+
			"inner join usuarioEmpresa ue "+
			"  on ue.idUsuario = u.idUsuario "+
			"inner join empresa e "+
			"  on e.idempresa = ue.idEmpresa "+
			"inner join empresaModulo em "+
			"  on em.idEmpresa = e.idempresa "+
			"inner join modulo m "+
			"  on m.idModulo = em.idModulo "+
			"where u.idusuario = ?";
	
	private static final String sqlConsultaSubModulo = "select "+ 
			"  sm.idSubModulo as idSubModulo, "+
			"  sm.nomeSubModulo as nomeSubModulo, "+
			"  sm.link as link "+
			"from  "+
			"  subModulo sm "+
			"inner join modulo m "+
			"  on sm.idModulo = m.idModulo "+
			"  and m.idModulo = ? ";
	
/*	private static final String sqlConsultaPermissoes = "select " + 
			"  tp.idTipoPermissao, " + 
			"  tp.nomePermissao " + 
			"from " + 
			"  tipoPermissao tp " + 
			"inner join subModulo sm " + 
			"  on sm.idSubModulo = tp.idSubModulo " + 
			"  and sm.idSubModulo = ?";
	*/
	private static final String sqlConsultaPermissoes = "select "+
			"  tp.idTipoPermissao, "+
            "  tp.sisCod, "+
            "  tp.nomePermissao "+
			"from  "+
            "  subModulo sm "+
			"inner join usuarioPermissao up "+
            "  on sm.idSubModulo = up.idSubModulo "+
			"inner join tipoPermissao tp "+
            "  on up.idTipoPermissao = tp.idTipoPermissao "+
			"where sm.idSubModulo = ?";
	private static final String sqlInsertModulo = "insert into desenv.modulo (nomeModulo) values (?)";
	
	
	public static Modulos consultarModulos(Usuario usuario){
		Modulos modulos = new Modulos();
		
		BroConnection con = Conectar.getConnection();
		if(!con.isConnected()){
			modulos.setErro(con.getErro());
			return modulos;
		}
		
		modulos = modulo(con, usuario);
		if(modulos == null)
			return modulos;
		if(modulos.getErro()!= ErrorCodes.OK)
			return modulos;
		
		consultarSubModulos(con, modulos);
		
		consultarPermissoes(con, modulos);
		
		return modulos;
	}
	
	public static Modulo cadastrarModulo(Usuario usuario, String nomeModulo){
		Modulo modulo = new Modulo();
		
		//SubModulo sm = SubModuloDao.checarPemissao(usuario, "CAD", "CAD_MODULO");
		SubModulo sm = new SubModulo();
		sm.setErro(ErrorCodes.DB_ERROR);
		if(sm.getErro() != ErrorCodes.OK){
			modulo.setErro(sm.getErro());
			return modulo;
		}
		
		BroConnection con = Conectar.getConnection();
		if(!con.isConnected()){
			modulo.setErro(con.getErro());
			return modulo;
		}
		
		modulo.setNomeModulo(nomeModulo);
		
		if(modulo.getNomeModulo() == null){
			modulo.setErro(ErrorCodes.NOME_MODULO_INVALIDO);
			return modulo;
		}
			
		if(modulo.getNomeModulo().trim().length() < 3 ){
			modulo.setErro(ErrorCodes.NOME_MODULO_INVALIDO);
			return modulo;
		}
		
		return cadastrarModulo(con, usuario, modulo);
		
	}
	
	
	
	private static Modulo cadastrarModulo(BroConnection con, Usuario usuario, Modulo modulo){
		
		
		
		PreparedStatement statement = null;
		try {
			
			statement = con.getCon().prepareStatement(sqlInsertModulo);    
			statement.setString(1, modulo.getNomeModulo());
			statement.executeUpdate();
			
			
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if(generatedKeys.first())
				modulo.setIdModulo(generatedKeys.getInt(1));
			
			
			
		} catch (SQLException e) {
			modulo.setErro(ErrorCodes.ERRO_INSERIR_MODULO);
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					modulo.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					e.printStackTrace();
					return modulo;
				}
			}
		}
		
		return modulo;
	}
	
	
	
	
	private static void consultarPermissoes(BroConnection con , Modulos modulos){
		for(int i = 0 ; i < modulos.getModulos().size() ; i ++){
			Modulo m = modulos.getModulos().get(i);
			
			for (int j = 0 ; j < m.getSubModulos().size() ; j ++){
				
				SubModulo sm = m.getSubModulos().get(j);
				
				
				PreparedStatement statement = null;
				try {
					
					statement = con.getCon().prepareStatement(sqlConsultaPermissoes);    
					statement.setInt(1, sm.getIdSubModulo());
					ResultSet rs = statement.executeQuery();
					
					while(rs.next()){
						TipoPermissao tp = new TipoPermissao();
						tp.setIdTipoPermissao(rs.getInt(1));
						tp.setNomePermissao(rs.getString(2));
						tp.setSisCod(rs.getString(3));
						tp.setErro(ErrorCodes.OK);
						sm.addPermissoes(tp);
					}
					
					
				} catch (SQLException e) {
					sm.setErro(ErrorCodes.ERROR_CONSULTANDO_PERMISSOES);
					e.printStackTrace();
				} finally {
					if (statement != null) {
						try {
							statement.close();
						} catch (SQLException e) {
							sm.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
							e.printStackTrace();
							return;
						}
					}
				}
				
				
				
			}
			
			
		}
	}

	
	private static void consultarSubModulos(BroConnection con , Modulos modulos){

		for( int i = 0 ; i < modulos.getModulos().size() ; i ++){
			Modulo modulo = modulos.getModulos().get(i);
			
			
			PreparedStatement statement = null;
			try {
				
				statement = con.getCon().prepareStatement(sqlConsultaSubModulo);    
				statement.setInt(1, modulo.getIdModulo());
				ResultSet rs = statement.executeQuery();
				
				while(rs.next()){
					SubModulo sm = new SubModulo();
					sm.setErro(ErrorCodes.OK);
					sm.setIdSubModulo(rs.getInt(1));
					sm.setNomeSubModulo(rs.getString(2));
					sm.setLink(rs.getString(3));
					sm.setModulo(modulo);
						
					modulos.getModulos().get(i).addSubModulo(sm);
				}
				
				
			} catch (SQLException e) {
				modulo.setErro(ErrorCodes.ERROR_CONSULTANDO_SUB_MODULOS);
				e.printStackTrace();
			} finally {
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
						modulo.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
						e.printStackTrace();
						return;
					}
				}
			}
			
			
		}
		return ;
	}
	
	private static Modulos modulo(BroConnection con, Usuario usuario){
		
		
		PreparedStatement statement = null;
		Modulos modulos = new Modulos();
		try {
			
			statement = con.getCon().prepareStatement(sqlConsultaModulos);    
			statement.setInt(1, usuario.getId());
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Modulo m = new Modulo();
				m.setErro(ErrorCodes.OK);
				m.setIdModulo(rs.getInt(1));
				m.setNomeModulo(rs.getString(2));
				modulos.addModulo(m);	
			}
			modulos.setErro(ErrorCodes.OK);
			
			
		} catch (SQLException e) {
			modulos.setErro(ErrorCodes.ERROR_CONSULTANDO_MODULOS);
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					modulos.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					e.printStackTrace();
					return modulos;
				}
			}
		}
		return modulos;
		
		
		
	}

}
