package br.com.bsb.bro.error;

public enum ErrorCodes {
	
	OK								(0, "Ok"),
	DB_ERROR						(1, "Erro ao conectar ao banco de dados"),
	DB_ERROR_DRIVER					(2, "Erro ao conectar ao banco de dados - driver n�o encontrado"),
	DB_ERROR_CONNECTION				(3, "Erro ao conectar ao banco de dados - verifique conex�o e funcionamento do mesmo"),
	DB_ERROR_CLOSE_CONNECTION		(4, "Erro ao fechar conex�o"),
	ERROR_LOGIN						(5, "Erro ao fazer login"),
	LOGIN_FAIL						(6, "Usu�rio ou senha inv�lidos"),
	USER_NO_COMPANY					(7, "Usu�rio sem empresa vinculada"),
	ERROR_COMPANY					(8, "Erro ao consultar a empresa"),
	ERROR_CONSULTANDO_MODULOS		(9, "Erro ao consultar os modulos"),
	ERROR_CONSULTANDO_SUB_MODULOS	(10, "Erro ao consultar os submodulos"),
	ERROR_CONSULTANDO_PERMISSOES	(11, "Erro ao consultar os permissoes"),
	ACESSO_AO_SUBMODULO_NEGADO		(12, "Usu�rio sem acesso para acessao o sub-modulo"),
	NOME_MODULO_INVALIDO			(13, "Nome do m�dulo inv�lido"),
	ERRO_INSERIR_MODULO				(14, "Erro ao inserir o m�dulo"),
	SUB_MODULO_NAO_EXISTE			(15, "Sub modulo consultado n�o existe"),
	ERRO_CADASTRAR_USUARIO			(16, "Erro ao cadastrar o usu�rio"),
	SQL_ERRO_CADASTRAR_USUARIO		(17, "Erro de sql ao cadastrar o usu�rio"),
	NOME_DO_USUARIO_INVALIDO		(18, "Nome do usu�rio inv�lido"),
	SENHA_DO_USUARIO_INVALIDO		(19, "Senha do usu�rio inv�lido"),
	EMAIL_DO_USUARIO_INVALIDO		(20, "Email do usu�rio invalido"),
	ERRO_LISTANDO_USUARIOS			(21, "Erro Listando os usu�rio"),
	
	
	;
	
	
	private final int code;
	private final String description;
	
	private ErrorCodes(int code, String description){
		this.code = code;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return code + ": " + description;
	}

}
