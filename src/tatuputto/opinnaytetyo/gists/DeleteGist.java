package tatuputto.opinnaytetyo.gists;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;


@WebServlet("/DeleteGist")
public class DeleteGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String accessToken = (String)request.getSession(false).getAttribute("accessToken");
		String gistId = request.getParameter("id");
	
		String url = "https://api.github.com/gists/" + gistId;
			
		String[] responseContent = new AuthorizedConnectionOauth().formConnection("DELETE", url, "", accessToken);
		
		//TODO lähetä vastaukoodi
		
		response.sendRedirect("http://localhost:8080/Opinnaytetyo/gists");
	}

}
