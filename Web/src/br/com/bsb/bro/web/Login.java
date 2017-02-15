package br.com.bsb.bro.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.bsb.bro.dao.ModuloDao;
import br.com.bsb.bro.dao.Modulos;
import br.com.bsb.bro.dao.Usuario;
import br.com.bsb.bro.dao.UsuarioDao;
import br.com.bsb.bro.error.ErrorCodes;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Sessao sessao = (Sessao) session.getAttribute("login");
		
		String email = request.getParameter("inputEmail");
		String senha = request.getParameter("inputPassword");
		
		response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader ("Expires", 0);

		if (sessao == null && email == null && senha == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		if (sessao == null && email != null && senha != null) {
			
			UsuarioDao ud = new UsuarioDao();
			
			Usuario u = ud.fazerLogin(email, senha);
			
			if (u.getErro() == ErrorCodes.OK) {
				
				Modulos modulos = ModuloDao.consultarModulos(u);
				
				sessao = new Sessao(u);
				session.setAttribute("login", sessao);
				session.setAttribute("modulos", modulos);
				response.sendRedirect("index.jsp");
				request.setAttribute("errorMsg", "Resposta: " + u.getNome());
				return;
			}else{
				//response.sendRedirect("login.jsp");
				request.setAttribute("errorMsg", u.getErro().toString());
				RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
				rd.forward(request, response);
				return;
				
			}
			
		}
		
		if(sessao == null){
			response.sendRedirect("login.jsp");
			return;
		}
		
		

		if (sessao.getUsuarioConectado() != null) {
			if (sessao.isLogado()) {
				session.setAttribute("login", sessao);
				response.sendRedirect("index.jsp");
				return;
			} else {
				response.sendRedirect("login.jsp");
				request.setAttribute("errorMsg", "Usuário não conectado");
				return;
			}
		}


		if (email == null && senha == null) {
			response.sendRedirect("login.jsp");
			return;
		}



	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

    
    

}
