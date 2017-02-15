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
	//SubModulo subModulo = SubModuloDao.checarPemissao(s.getUsuarioConectado(), "CON", "CAD_MODULO");
	SubModulo subModulo = Permissao.checarPermissao(s.getUsuarioConectado(), "ADMIN", "CAD_MODULO");
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
	
	<form method="post" action="CadastrarModulo">
		<div class="form-group">
			<label for="exampleInputEmail1">Nome do Modulo</label> 
			<input class="form-control" id="nomeModulo" name="nomeModulo" placeholder="Nome do Módulo">
			  <small id="nomeModuloHelp" class="form-text text-muted">Este é o nome do Modulo (Opção maior nos menus).</small>
		</div>
		<button type="submit" class="btn btn-primary">Salvar</button>
	</form>



		<%
		
	}
		%>
	

</div>

