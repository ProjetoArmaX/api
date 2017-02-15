package br.com.bsb.bro.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.bsb.bro.dao.SubModulo;
import br.com.bsb.bro.dao.SubModuloDao;
import br.com.bsb.bro.dao.Usuario;
import br.com.bsb.bro.dao.UsuarioDao;

/**
 * Servlet implementation class CadastrarModulo
 */
@WebServlet("/CadastrarUsuario")
public class CadastrarUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastrarUsuario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Sessao sessao = (Sessao) session.getAttribute("login");
		
		response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma","no-cache"); 
		response.setDateHeader ("Expires", 0);

		
		String nome = request.getParameter("nomeUsuario");
		String senha = request.getParameter("senha");
		String email = request.getParameter("email");
		
		if (sessao == null ) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		SubModulo sm = SubModuloDao.getModulo("CAD_USUARI");
		
		
		Usuario usuario = UsuarioDao.cadastrarUsuario(sessao.getUsuarioConectado(), nome, senha, email);
		
		request.setAttribute("msg", usuario.getErro().toString());
		RequestDispatcher rd = request.getRequestDispatcher(sm.getLink());
		rd.forward(request, response);
		
		//response.sendRedirect(sm.getLink());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
