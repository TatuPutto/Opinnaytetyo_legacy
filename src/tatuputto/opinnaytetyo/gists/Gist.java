package tatuputto.opinnaytetyo.gists;

import java.util.ArrayList;

public class Gist {
	private String id;
	private String description;
	private GistOwner owner;
	private ArrayList<GistFile> files;
	//TODO hae tarkemmat tiedot: omistaja, luonti/p�ivitys aika, lyhennetty yms.
	
	
	public Gist(String id, String description, ArrayList<GistFile> files) {
		this.id = id;
		this.description = description;
		this.files = files;
	}
	
	public Gist(String id, String description, GistOwner owner, ArrayList<GistFile> files) {
		this.id = id;
		this.description = description;
		this.owner = owner;
		this.files = files;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public GistOwner getOwner() {
		return owner;
	}
	public void setOwner(GistOwner owner) {
		this.owner = owner;
	}

	public ArrayList<GistFile> getFiles() {
		return files;
	}
	public void setFiles(ArrayList<GistFile> files) {
		this.files = files;
	}


	public String toString() {
		return "ID: " + this.id + "\nKuvaus: " + this.description + "\nTiedostot: " + this.files;  
	}
	
}
