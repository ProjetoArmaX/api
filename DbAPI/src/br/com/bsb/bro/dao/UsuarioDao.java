package br.com.bsb.bro.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.bsb.bro.error.ErrorCodes;
import br.com.bsb.bro.main.Conectar;

public class UsuarioDao {
	

	private static final String sqlConsultaUsuario = "select idusuario, nome from usuario where email = ? and senha = ?";
	private static final String sqlInsertUsuario = "insert into usuario (nome, senha, email) values (?, ?, ?)";
	private static final String sqlInsertUsuarioEmpresa = "insert into usuarioEmpresa (idUsuario, idEmpresa) values (?, ?)";
	private static final String sqlListarUsuario = " select " +  
			"   u.idUsuario, " + 
			"   u.nome, " + 
			"   u.email " + 
			"from  " + 
			"  desenv.usuario u " + 
			"inner join desenv.usuarioEmpresa ue " + 
			"  on u.idusuario = ue.idUsuario " + 
			"  and ue.idEmpresa = ?";
	
	
	public Usuario fazerLogin(String nome, String senha){
		BroConnection con = Conectar.getConnection();
		Usuario u = new Usuario();
		if(!con.isConnected()){
			u.setErro(con.getErro());
			return u;
		}
		
		//VERIFICAR SE USUARIO E SENHA ESTAO CERTOS, E RETORNAR O OBJETO DO USUARIO
		u = autenticar(con, nome, senha);
		
		if(u.getErro() != ErrorCodes.OK)
			return u;
		
		
		
		Empresa empresa = EmpresaDao.empresaUsuario(con, u);
		
		if(empresa.getErro() != ErrorCodes.OK){
			u.setErro(empresa.getErro());
			return u;
		}
		u.setEmpresa(empresa);
		
		
		return u;
	}
	
	public static Usuario cadastrarUsuario(Usuario usuarioConectado, String nome, String senha, String email){
		Usuario usuario = new Usuario();
		//SubModulo sm = SubModuloDao.checarPemissao(usuarioConectado, "CAD", "CAD_USUARI");
		SubModulo sm = Permissao.checarPermissao(usuarioConectado, "ADMIN", "CAD_USUARI");
		
		if(sm.getErro() != ErrorCodes.OK){
			usuario.setErro(sm.getErro());
			return usuario;
		}
		
		BroConnection con = Conectar.getConnection();
		if(!con.isConnected()){
			usuario.setErro(con.getErro());
			return usuario;
		}
		
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setEmail(email);
		
		if(usuario.getNome() == null){
			usuario.setErro(ErrorCodes.NOME_DO_USUARIO_INVALIDO);
			return usuario;
		}
			
		if(usuario.getNome().trim().length() < 3 ){
			usuario.setErro(ErrorCodes.NOME_DO_USUARIO_INVALIDO);
			return usuario;
		}
		
		if(usuario.getSenha() == null){
			usuario.setErro(ErrorCodes.SENHA_DO_USUARIO_INVALIDO);
			return usuario;
		}
			
		if(usuario.getSenha().trim().length() < 3 ){
			usuario.setErro(ErrorCodes.SENHA_DO_USUARIO_INVALIDO);
			return usuario;
		}

		if(usuario.getEmail() == null){
			usuario.setErro(ErrorCodes.EMAIL_DO_USUARIO_INVALIDO);
			return usuario;
		}
			
		if(usuario.getEmail().trim().length() < 3 ){
			usuario.setErro(ErrorCodes.EMAIL_DO_USUARIO_INVALIDO);
			return usuario;
		}
		return cadastrarUsuario(con, usuario.getNome(), usuario.getSenha(), usuario.getEmail(), usuarioConectado.getEmpresa());
		
	}
	
	public static ArrayList<Usuario> listarUsuario (Usuario usuarioConectado){
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		Empresa empresa = usuarioConectado.getEmpresa();
		//SubModulo sm = SubModuloDao.checarPemissao(usuarioConectado, "LST", "LST_USUARI");
		SubModulo sm  = Permissao.checarPermissao(usuarioConectado, "USER", "LST_USUARI");
		
		if(sm.getErro() != ErrorCodes.OK){
			usuario.setErro(sm.getErro());
			usuarios.add(usuario);
			return usuarios;
		}
		
		BroConnection con = Conectar.getConnection();
		if(!con.isConnected()){
			usuario.setErro(con.getErro());
			usuarios.add(usuario);
			return usuarios;
		}
		
		
		usuarios = listarUsuario(con, empresa);
		
		return usuarios;
	}
	
	//TODO PAREI AQUI LISTANDO OS USUÁRIOS, VERIFICAR SE FUNDIONOU, e fazer a tela ainda
	private static ArrayList<Usuario> listarUsuario (BroConnection con, Empresa empresa){
		PreparedStatement statement = null;
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			
			statement = con.getCon().prepareStatement(sqlListarUsuario);    
			statement.setInt(1, empresa.getIdEmpresa());
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt(1));
				usuario.setNome(rs.getString(2));
				usuario.setEmail(rs.getString(3));
				usuario.setErro(ErrorCodes.OK);
				usuarios.add(usuario);
			}
			
			
			
		} catch (SQLException e) {
			Usuario u = new Usuario();
			u.setErro(ErrorCodes.ERRO_LISTANDO_USUARIOS);
			usuarios.add(u);
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					Usuario u = new Usuario();
					u.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					usuarios.add(u);
					e.printStackTrace();
					return usuarios;
				}
			}
		}
		return usuarios;
	}
	
	
	private static Usuario cadastrarUsuario(BroConnection con, String nome, String senha, String email, Empresa empresa){
		PreparedStatement statement = null;
		Usuario u = new Usuario();
		try {
			con.getCon().setAutoCommit(false);
			
			statement = con.getCon().prepareStatement(sqlInsertUsuario, PreparedStatement.RETURN_GENERATED_KEYS);    
			statement.setString(1, nome);
			statement.setString(2, senha);
			statement.setString(3, email);
			statement.executeUpdate();
			
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if(generatedKeys.first()){
				u.setId(generatedKeys.getInt(1));
				
				statement.close();
				statement = con.getCon().prepareStatement(sqlInsertUsuarioEmpresa );
				statement.setInt(1, u.getId());
				statement.setInt(2, empresa.getIdEmpresa());
				
				statement.executeUpdate();
				
				u.setNome(nome);
				u.setSenha(senha);
				u.setEmail(email);
				u.setErro(ErrorCodes.OK);
				
				con.getCon().commit();
			}else{
				u.setErro(ErrorCodes.ERRO_CADASTRAR_USUARIO);
				
			}
			
			
			
			
			
		} catch (SQLException e) {
			u.setErro(ErrorCodes.SQL_ERRO_CADASTRAR_USUARIO);
			//con.getCon().rollback();
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					u.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					e.printStackTrace();
					return u;
				}
			}
		}
		return u;
	}
	
	
	
	
	
	
	
	private static Usuario autenticar(BroConnection con, String login, String senha) {
		PreparedStatement statement = null;
		Usuario u = new Usuario();
		try {
			
			statement = con.getCon().prepareStatement(sqlConsultaUsuario);    
			statement.setString(1, login);
			statement.setString(2, senha);    
			ResultSet rs = statement.executeQuery();
			
			if(rs.first()){
				u.setId(rs.getInt("idusuario"));
				u.setNome(rs.getString("nome"));
				u.setErro(ErrorCodes.OK);
			}else{
				u.setErro(ErrorCodes.LOGIN_FAIL);
			}
			
			
			
		} catch (SQLException e) {
			u.setErro(ErrorCodes.ERROR_LOGIN);
			e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					u.setErro(ErrorCodes.DB_ERROR_CLOSE_CONNECTION);
					e.printStackTrace();
					return u;
				}
			}
		}
		return u;

	}

}
