package tatuputto.opinnaytetyo;

public class Main {
	public static void main(String[] args) {
		String accessToken = "4289d31cc0771473d357fec77583d632a82b2168";
		
		GetGists getGists = new GetGists();
		getGists.getGistsThroughAPI(accessToken);
	}
}
