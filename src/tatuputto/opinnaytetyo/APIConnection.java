package tatuputto.opinnaytetyo;


/**
 * 
 * @author Tatu Putto
 * Rajapintaluokka, jonka toteuttavat luokat muodostavat yhteyden GitHubin API:in eri tavoin.
 */
interface APIConnection {
	//public URLConnection formConnection(String url, String accessToken);
	public String formConnection(String method, String url, String data, String accessToken);
	
}
