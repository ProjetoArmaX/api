<%@page import="br.com.bsb.bro.error.ErrorCodes"%>
<%@page import="br.com.bsb.bro.dao.SubModuloDao"%>
<%@page import="br.com.bsb.bro.dao.SubModulo"%>
<%@page import="br.com.bsb.bro.dao.Modulos"%>
<%@page import="br.com.bsb.bro.dao.Usuario"%>
<%@page import="br.com.bsb.bro.dao.Permissao"%>
<%@page import="java.util.ArrayList"%>
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
	SubModulo subModulo = Permissao.checarPermissao(s.getUsuarioConectado(), "ADMIN", "CAD_PERM");
	String idUsuario = request.getParameter("idUsuario");
%>
<div class="jumbotron">

<%
	if (subModulo.getErro() != ErrorCodes.OK){
		%>
			<h1>Usuário sem permissão para acessar o múdulo</h1>
			<p>Favor entrar em contato com o administrador para validar seu acesso.</p>
		<%
	}else{
		ArrayList<Usuario> usuarios = (ArrayList<Usuario>)request.getAttribute("usuarios");
		
		%>
		



	<h1><%=subModulo.getNomeSubModulo() %></h1>
	<h3> <%= (request.getAttribute("msg")!= null ? request.getAttribute("msg") : "") %></h3>
	
	<div class="panel panel-default">
	<!-- Default panel contents -->
		<div class="panel-heading">Panel heading</div>
		<!-- Table -->
		<table class="table">
		<tr>
			<th>Nome</th>
			<th>E-Mail</th>
		</tr>
		<%
			if(usuarios != null)
				for( int i = 0 ; i < usuarios.size() ; i ++){
					out.println("<tr>");
					out.println("<td>" + usuarios.get(i).getNome() + "</td>");
					out.println("<td>" + usuarios.get(i).getEmail() + "</td>");
					out.println("<td><a href=\"#\" class=\"confirm-delete btn mini red-stripe\" role=\"button\" data-title=\"kitty\" data-id=\"2\">Permissoes</a></td>");
					out.println("</tr>");
				}
		%>

		</table>
	</div>
	<form method="post" action="ListarUsuarios">
		<button type="submit" class="btn btn-primary">Consultar</button>
	</form>



		<%
		
	}
		%>
	

</div>

