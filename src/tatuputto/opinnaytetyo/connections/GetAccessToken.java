package tatuputto.opinnaytetyo.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OAuth-prosessin viimeinen osa, vaihdetaan koodi k‰ytt‰j‰kohtaiseen Access tokeniin.
 */
@WebServlet("/GetAccessToken")
public class GetAccessToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Sovellukselle rekisterˆidyt avaimet
	private static final String clientId = "566fea61a0cebae27268";
    private static final String clientSecret = "87454f258250d9170e31a8f13b51e6a612bd6545";
  
    
    /**
     * 
     */
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		//TODO  tarkista, ett‰ statet t‰sm‰‰v‰t, jos ei t‰sm‰‰, lopeta sis‰‰nkirjautumisprosessi
   		//TODO tallena access_code sessiioon, ehk‰ ev‰steeseen
   		/*as well as the state you provided in the previous step in a state parameter. If the states don't match, the request has been created by a third party and the process should be aborted.*/
   		
   		String codeToExchange = request.getParameter("code");
   		
   		URL getAccessToken = new URL("https://github.com/login/oauth/access_token?client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + codeToExchange);
   		URLConnection connection = getAccessToken.openConnection();
   		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
   		
   		String inputLine = "";
   		while((inputLine = br.readLine()) != null) {
   			//bw.write(inputLine);
   			log(inputLine);
   			
   		}
   		
   		redirectToFrontPage(request, response, inputLine);
   	
   	}

   	
   	private void redirectToFrontPage(HttpServletRequest request, HttpServletResponse response, String accessToken) throws ServletException, IOException {
   		//Ohjataan k‰ytt‰j‰ takaisin etusivulle ja l‰hetet‰‰n access token mukana
   		request.setAttribute("accessToken", accessToken);
   		response.sendRedirect("ListGists.jsp");
   	}
   	
}
