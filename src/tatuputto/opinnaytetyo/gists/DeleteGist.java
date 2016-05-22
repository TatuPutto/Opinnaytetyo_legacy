package tatuputto.opinnaytetyo.gists;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;


@WebServlet("/DeleteGist")
public class DeleteGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		
		String accessToken = (String)request.getAttribute("accessToken");
		if(accessToken != null && !accessToken.isEmpty()) {
		
			String gistId = request.getParameter("id");
			String url = "https://api.github.com/gists/" + gistId;
			
			String[] responseContent = connection.formConnection("DELETE", url, "", accessToken);
		
			//TODO lähetä vastaukoodi
		}
		
		
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
	}

}
