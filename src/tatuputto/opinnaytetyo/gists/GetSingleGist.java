package tatuputto.opinnaytetyo.gists;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.connections.AuthorizedConnectionOauth;
import tatuputto.opinnaytetyo.json.ParseSingleGistJSON;

/**
 * Servlet implementation class GetSingleGist
 */
@WebServlet("/GetSingleGist")
public class GetSingleGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		String gistId = request.getParameter("id");
		String url = "https://api.github.com/gists/" + gistId;
		String accessToken = (String)session.getAttribute("accessToken");
		int userId = (int)session.getAttribute("userId");
		
		String[] responseContent;
		Gist gist = null;
		
		responseContent = new AuthorizedConnectionOauth().formConnection("GET", url, "", accessToken);
		gist = new ParseSingleGistJSON().parseJSON(responseContent[2]);
		
		if(gist.getOwner().getId() == userId) {
			request.setAttribute("gistOwner", true);
		}
		else {
			request.setAttribute("gistOwner", false);
		}
		
		request.setAttribute("gist", gist);
		request.setAttribute("id", gistId);
			
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsps/SingleGist.jsp");
		rd.forward(request, response);
	}
}
