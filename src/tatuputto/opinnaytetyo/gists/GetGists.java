package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;
import tatuputto.opinnaytetyo.json.ParseMultipleGistsJSON;
import tatuputto.opinnaytetyo.gists.Gist;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetGistsServlet
 */
@WebServlet("/GetGistsServlet")
public class GetGists extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParseMultipleGistsJSON parse = new ParseMultipleGistsJSON();
		AuthorizedConnection connection = new AuthorizedConnection();
		
		String url = "https://api.github.com/gists";
		String accessToken = "";
		
		ArrayList<String> responseContent = connection.formConnection("GET", url, "", accessToken);
		ArrayList<Gist> gists = parse.parseJSON(responseContent.get(2));
		
		request.setAttribute("gists", gists);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListGists.jsp");
		rd.forward(request, response);
	}
}
