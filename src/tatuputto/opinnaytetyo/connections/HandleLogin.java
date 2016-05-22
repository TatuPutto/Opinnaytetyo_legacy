package tatuputto.opinnaytetyo.connections;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tatuputto.opinnaytetyo.gists.User;
import tatuputto.opinnaytetyo.json.ParseAuthorizationInfo;


@WebServlet("/HandleLogin")
public class HandleLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ParseAuthorizationInfo parse = new ParseAuthorizationInfo();
		
		Cookie cookie = null;
		Cookie[] cookies = null;
	    cookies = request.getCookies();
	    String accessToken = "";
	    boolean tokenCookieFound = false;
		
	   
	    //Tarkistetaan löytyykö accesstokenin sisältävää evästettä
	    if(cookies != null) {
	    	for (int i = 0; i < cookies.length; i++) {
	    		cookie = cookies[i];
	    		
	    		if(cookie.getName().equals("accesstoken")) {
	    			log("Cookie: " + cookie.getName() + " Value: " + cookie.getValue());
	    			tokenCookieFound = true;
	    			accessToken = cookie.getValue();
	    			
	    			break;
	    		}	
	    	}
	    	
	    	//Jos eväste löytyi, tarkistetaan onko access token vielä voimassa
	    	if(tokenCookieFound) {
	    		ArrayList<String> responseContent = checkAuthorization(accessToken);	
	    		
	    		//Jos eväste on voimassa, aloitetaan sessio
	    		if(!responseContent.get(0).equals("404")) {
	    			log("Access token on voimassa.");
	    			
	    			User user = parse.parseJSON(responseContent.get(2));
	    			
	    			//Asetetaan tarvittavat sessiomuuttujat
	    			HttpSession session = request.getSession(true);
	    			session.setAttribute("loggedIn", true);
	    			session.setAttribute("userId", user.getId());
	    			session.setAttribute("username", user.getLogin());
	    			session.setAttribute("avatar", user.getAvatarUrl());
	    			session.setAttribute("accessToken", accessToken);

	    			response.sendRedirect("http://localhost:8080/Opinnaytetyo/gists");
	    		}
	    		//Jos ei ole voimassa, poistetaan vanha eväste ja lähetetään käyttäjä tekemään uusi auktorisointi
	    		else {
	    			log("Access token ei ole enää voimassa.");
	    			
	    			Cookie accessTokenCookie = new Cookie("accesstoken", "");
	    			accessTokenCookie.setMaxAge(0); 
	    	   		response.addCookie(accessTokenCookie);
	    			
	    			response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");
	    		}
	    	}
	    	else {
	    		response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");
	    	}    	
	    }
	    //Jos sessiota eikä evästettä löydy, lähetetään käyttäjä tunnistautumaan githubin välityksellä
	    //https://developer.github.com/v3/oauth/#web-application-flow
	    else {
	    	response.sendRedirect("https://github.com/login/oauth/authorize?client_id=566fea61a0cebae27268&scope=gist");
	    }
	}
	
	
	//Selvit��n onko access token voimassa
	private ArrayList<String> checkAuthorization(String accessToken) {
		AuthorizedConnectionBasic connection = new AuthorizedConnectionBasic();
		
		String id = GetAccessToken.clientId;
		String secret = GetAccessToken.clientSecret;
		String url = "https://api.github.com/applications/" + id + "/tokens/" + accessToken + "";
		
		return connection.formConnection("GET", url, "", id, secret);

	}
	
}
