package tatuputto.opinnaytetyo.connections;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * OAuth-prosessin viimeinen osa, vaihdetaan koodi käyttäjäkohtaiseen access tokeniin.
 */
@WebServlet("/GetAccessToken")
public class GetAccessToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Sovellukselle rekisteröidyt avaimet
	protected static final String clientId = "566fea61a0cebae27268";
	protected static final String clientSecret = "87454f258250d9170e31a8f13b51e6a612bd6545";
	
    
    /**
     * Vaihdetaan saatu koodi access tokeniin.
     * Luodaan evaste, jonka arvoksi saatu token asetetaan.
     * Lopuksi lähetetään käyttäjä viimeistelemään sisäänkirjautuminen.
     */
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		//TODO  tarkista, ett� statet t�sm��v�t, jos ei t�sm��, lopeta sis��nkirjautumisprosessi
   		/*as well as the state you provided in the previous step in a state parameter. 
   		 *If the states don't match, the request has been created by a third party and the process should be aborted.*/
   		String codeToExchange = request.getParameter("code");
   		
   		String url = "https://github.com/login/oauth/access_token?client_id=" + 
   				clientId + "&client_secret=" + clientSecret + "&code=" + codeToExchange;
 
   		//Erotellaan access token saadusta vastauksesta
   		String[] responseContent = new UnauthorizedConnection().formConnection("GET", url, "");
        String[] token = responseContent[2].split("&");
        token = token[0].split("=");
        
        //Luodaan uusi eväste, jonka arvoksi asetetaan access tokenin arvo
   		Cookie tokenCookie = new Cookie("accesstoken", token[1]);
   		tokenCookie.setMaxAge(60*60*24*7); 
   		response.addCookie(tokenCookie);
   	
   		//Suoritetaan sisäänkirjautuminen
   		response.sendRedirect("http://localhost:8080/Opinnaytetyo/HandleLogin"); 		
   	}
}
