package tatuputto.opinnaytetyo;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		String accessToken = "615b87d48ce87d6563b93ef117c4aac23281f429";
		ArrayList<Gist> gists = new ArrayList<Gist>();
		
		GetGists getGists = new GetGists();
		GetGistSource getGistSource = new GetGistSource();
		
		//getGists.getGists(accessToken);
		
		gists = getGists.getGists(accessToken);
		System.out.println(getGistSource.getSource(gists.get(1).getRawUrl(), accessToken));
		
		
		//CreateGist createGist = new CreateGist();
		//createGist.createNewGist();
	}
}
