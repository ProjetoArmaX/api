<%@page import="br.com.bsb.bro.error.ErrorCodes"%>
<%@page import="br.com.bsb.bro.dao.SubModuloDao"%>
<%@page import="br.com.bsb.bro.dao.SubModulo"%>
<%@page import="br.com.bsb.bro.dao.Modulos"%>
<%@page import="br.com.bsb.bro.dao.Permissao"%>
<%@page import="br.com.bsb.bro.web.Sessao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
	HttpServletResponse httpResponse = (HttpServletResponse)response;
	httpResponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
	response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	httpResponse.setHeader("Pragma","no-cache"); 
	httpResponse.setDateHeader ("Expires", 0);

	//Modulos modulos = (Modulos) request.getSession().getAttribute("modulos");
	Sessao s = (Sessao) request.getSession().getAttribute("login");
	SubModulo subModulo = Permissao.checarPermissao(s.getUsuarioConectado(), "ADMIN", "CAD_USUARI");
%>
<div class="jumbotron">

<%
	if (subModulo.getErro() != ErrorCodes.OK){
		%>
			<h1>Usuário sem permissão para acessar o múdulo</h1>
			<p>Favor entrar em contato com o administrador para validar seu acesso.</p>
		<%
	}else{
		%>
		



	<h1><%=subModulo.getNomeSubModulo() %></h1>
	<h3> <%= (request.getAttribute("msg")!= null ? request.getAttribute("msg") : "") %></h3>
	
	<form method="post" action="CadastrarUsuario">
		<div class="form-group">
			<label for="labelNomeUsuario">Nome do Usuário</label> 
			<input class="form-control" id="nomeUsuario" name="nomeUsuario" placeholder="Nome do Usuário">
			  <small id="nomeUsuarioHelp" class="form-text text-muted">Nome do Usuário.</small>
		</div>
		<div class="form-group">
			<label for="labelSenha">Senha</label> 
			<input type="password" class="form-control" id="senha" name="senha" placeholder="Senha">
			  <small id="senhaHelp" class="form-text text-muted">Senha do usuário.	</small>
		</div>
		<div class="form-group">
			<label for="labelSenha">Email</label> 
			<input type="email" class="form-control" id="email" name="email" placeholder="E-Mail">
			  <small id="emailHelp" class="form-text text-muted">E-Mail do usuário</small>
		</div>
		<button type="submit" class="btn btn-primary">Salvar</button>
	</form>



		<%
		
	}
		%>
	

</div>

