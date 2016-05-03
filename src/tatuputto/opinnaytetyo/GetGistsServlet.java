package tatuputto.opinnaytetyo;

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
public class GetGistsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ParseMultipleGistsJSON parse = new ParseMultipleGistsJSON();
	AuthorizedConnection connection = new AuthorizedConnection();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Haetaan gistit
		String url = "https://api.github.com/gists";
		String accessToken = "8bd36e199d0c9ff60b1a86d94c72a0e05e089fb5";
		
	
		ArrayList<String> responseContent = connection.formConnection("GET", url, "", accessToken);
		System.out.println(responseContent.get(2));
		ArrayList<Gist> gists = parse.parseJSON(responseContent.get(2));
		
		/*ArrayList<Gist> gists = new ArrayList<Gist>();
		ArrayList<GistFile> files = new ArrayList<GistFile>();
		files.add(new GistFile("testfile1.java", "Java", "http://www.adf.com"));
		gists.add(new Gist("123", "testi gist", files));*/
		
		request.setAttribute("gists", gists);
		//request.setAttribute("gists", gists);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/ListGists.jsp");
		rd.forward(request, response);
	}


}
