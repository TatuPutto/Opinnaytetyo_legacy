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
import tatuputto.opinnaytetyo.json.GetGistFilesJSON;
import tatuputto.opinnaytetyo.json.ParseSingleGistJSON;

@WebServlet("/GetSingleGistServlet")
public class GetSingleGistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ParseSingleGistJSON parse = new ParseSingleGistJSON();
		GetGistFilesJSON files = new GetGistFilesJSON();
		AuthorizedConnection connection = new AuthorizedConnection();
		
		String gistId = request.getParameter("id");
		String accessToken = "<accessToken>";
		String url = "https://api.github.com/gists/" + gistId;
		
		ArrayList<String> responseContent = connection.formConnection("GET", url, "", accessToken);
		//Gist gist = parse.parseJSON(responseContent.get(2));
		
		String data = files.GetGistFiles(responseContent.get(2)).toString();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(data);
	}

}
