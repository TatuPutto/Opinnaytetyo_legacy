package tatuputto.opinnaytetyo.connections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
   		
   		String line = "";
   		if(br != null){
   			line = br.readLine();
   		}
   		
        log(line);	
        String[] token = line.split("&");
        token = token[0].split("=");
        
   		Cookie accessToken = new Cookie("accesstoken", token[1]);
   		accessToken.setMaxAge(60*60*24*7); 
   		response.addCookie(accessToken);
   		
   		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/"));
   		
   	}
}
