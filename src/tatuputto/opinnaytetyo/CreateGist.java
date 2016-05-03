package tatuputto.opinnaytetyo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/CreateGist")
public class CreateGist extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AuthorizedConnection connection = new AuthorizedConnection();
		EncodeJSON encodejson = new EncodeJSON();
		
		String description = request.getParameter("description");
		Boolean isPublic =  Boolean.parseBoolean(request.getParameter("ispublic"));
		String[] filenames = request.getParameterValues("filenames[]");
		String[] sources = request.getParameterValues("sources[]");
		String accessToken = "8bd36e199d0c9ff60b1a86d94c72a0e05e089fb5";
		
		String url = "https://api.github.com/gists";
		//Lähetetään gistille asetut tiedot muunnettavaksi JSON-muotoon
		String data = encodejson.encodeJSONRequestPOST(description, isPublic, filenames, sources).toString();
		ArrayList<String> responseContent = connection.formConnection("POST", url, data, accessToken);
		
		
		//Lähetetään pyynnön vastauskoodi
		//lisäys onnistui: 201 - CREATED
		//ei onnistunut: 401 - Unauthorized / 422 - Unprocessable Entity 
		response.setContentType("application/text");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(responseContent.get(0) + ", " + responseContent.get(1));
		
	}

}
