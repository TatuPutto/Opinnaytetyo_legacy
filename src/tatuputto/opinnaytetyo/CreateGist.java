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
	AuthorizedConnection connection = new AuthorizedConnection();
	EncodeJSON encodejson = new EncodeJSON();
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String description = request.getParameter("description");
		Boolean isPublic =  Boolean.parseBoolean(request.getParameter("ispublic"));
		String[] filenames = request.getParameterValues("filenames[]");
		String[] sources = request.getParameterValues("sources[]");
		String accessToken = "f0658406e005c2569f0d968f40da48cfd433c4e1";
		
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
