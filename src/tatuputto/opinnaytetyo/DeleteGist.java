package tatuputto.opinnaytetyo;

public class DeleteGist {
	AuthorizedConnection connection = new AuthorizedConnection();
	
	public void deleteGist(String accessToken, String gistId) {
		String url = "https://api.github.com/gists/" + gistId;
		connection.formConnection("DELETE", url, "", accessToken);
	}
	
	
}
