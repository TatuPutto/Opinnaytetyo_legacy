package tatuputto.opinnaytetyo.gists;

import tatuputto.opinnaytetyo.connections.AuthorizedConnection;
import tatuputto.opinnaytetyo.json.GetGistFilesJSON;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetSingleGistAJAX")
public class GetSingleGistAJAX extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetGistFilesJSON files = new GetGistFilesJSON();
		AuthorizedConnection connection = new AuthorizedConnection();
		
		String gistId = request.getParameter("id");
		String accessToken = "c72276ff4963b622806d1f141b907b465106b4e8";
		String url = "https://api.github.com/gists/" + gistId;
		
		ArrayList<String> responseContent = connection.formConnection("GET", url, "", accessToken);
		
		String data = files.GetGistFiles(responseContent.get(2)).toString();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(data);
	}

}
