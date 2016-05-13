package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetSingleGistServlets
 */
@WebServlet("/GetGistSource")
public class GetGistSource extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthorizedConnectionOauth connection = new AuthorizedConnectionOauth();
		
		String rawUrl = request.getParameter("rawurl");
		
		String accessToken = "<accessToken>";
		
			
		ArrayList<String> responseContent = connection.formConnection("GET", rawUrl, "", accessToken);
		
		response.setContentType("application/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseContent.get(2));
	}

	
}
