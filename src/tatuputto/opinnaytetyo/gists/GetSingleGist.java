package tatuputto.opinnaytetyo.gists;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;
import tatuputto.opinnaytetyo.json.ParseSingleGistJSON;


@WebServlet("/GetSingleGist")
public class GetSingleGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParseSingleGistJSON parse = new ParseSingleGistJSON();
		AuthorizedConnection connection = new AuthorizedConnection();

		String gistId = request.getParameter("id");
		String accessToken = "c72276ff4963b622806d1f141b907b465106b4e8";
		String url = "https://api.github.com/gists/" + gistId;

		ArrayList<String> responseContent = connection.formConnection("GET", url, "", accessToken);
		Gist gist = parse.parseJSON(responseContent.get(2));
	
		request.setAttribute("gist", gist);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/EditGist.jsp");
		rd.forward(request, response);
	}
}
