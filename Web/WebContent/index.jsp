<%@page import="br.com.bsb.bro.dao.Modulos"%>
<%@page import="br.com.bsb.bro.web.Sessao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	HttpServletResponse httpResponse = (HttpServletResponse)response;
	httpResponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
	response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	httpResponse.setHeader("Pragma","no-cache"); 
	httpResponse.setDateHeader ("Expires", 0);
	
	Sessao s = (Sessao)request.getSession().getAttribute("login");
	Modulos modulos = (Modulos)request.getSession().getAttribute("modulos");
	String paginaCentral = (request.getParameter("page") != null ? request.getParameter("page") : "BoasVindas.html");
	if(s == null){
		response.sendRedirect("login.jsp");
		return;	
	}else{
		if(!s.isLogado()){
			response.sendRedirect("login.jsp");
			return;
		}
	}
%>

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Inicio</title>
    <link href="bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="bootstrap-3.3.7-dist/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <link href="bootstrap-3.3.7-dist/css/navbar-static-top.css" rel="stylesheet">
    <script src="bootstrap-3.3.7-dist/js/ie-emulation-modes-warning.js"></script>
  </head>

  <body>
    <nav class="navbar navbar-default navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.jsp?page=BoasVindas.html"><%=s.getUsuarioConectado().getEmpresa().getNomeEmpresa() %> </a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
          <%
          for(int i = 0 ; i < modulos.getModulos().size() ; i ++){
        	  %>
        	  <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><%=modulos.getModulos().get(i).getNomeModulo() %> <span class="caret"></span></a>
                <ul class="dropdown-menu">
                <%
                  for(int j = 0 ; j < modulos.getModulos().get(i).getSubModulos().size() ; j ++){
                	  %>
                	    <li><a href="<%=modulos.getModulos().get(i).getSubModulos().get(j).getLink() %>"><%=modulos.getModulos().get(i).getSubModulos().get(j).getNomeSubModulo() %></a></li>
                	  <%
                  }
                %>
                </ul>
              </li>
        	  <%
          }
          %>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="Logoff">LOG OFF</a></li>
          </ul>
        </div>
      </div>
    </nav>


    <div class="container">

    <jsp:include page="<%= paginaCentral %>" flush="true" />

    </div>
    
    


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script src="bootstrap-3.3.7-dist/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
