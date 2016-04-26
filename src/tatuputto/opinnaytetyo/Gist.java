package tatuputto.opinnaytetyo;

public class Gist {
	private String filename;
	private String description;
	private String language;
	private String rawUrl;

	
	public Gist(String filename, String description, String language, String rawUrl) {
		this.filename = filename;
		this.description = description;
		this.language = language;
		this.rawUrl = rawUrl;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRawUrl() {
		return rawUrl;
	}
	public void setRawUrl(String rawUrl) {
		this.rawUrl = rawUrl;
	}
	
	public String toString() {
		return this.filename + ", " + this.description + ", " + this.language + ", " + this.rawUrl;  
	}
	
}
