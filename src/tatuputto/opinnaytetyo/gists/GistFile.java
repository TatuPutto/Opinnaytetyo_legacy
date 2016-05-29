package tatuputto.opinnaytetyo.gists;

/**
 * T�m� luokka kuvaa gistin sis�lt�m�� yksitt�ist� tiedostoa
 *
 */
public class GistFile {
	private String filename;
	private String language;
	private String rawUrl;
	private String content;
	
	
	public GistFile(String filename, String rawUrl) {
		this.filename = filename;
		this.rawUrl = rawUrl;
	}
	
	public GistFile(String filename, String language, String rawUrl) {
		this.filename = filename;
		this.language = language;
		this.rawUrl = rawUrl;
	}
	
	public GistFile(String filename, String language, String rawUrl, String content) {
		this.filename = filename;
		this.language = language;
		this.rawUrl = rawUrl;
		this.content = content;
	}
	
	
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
	public String toString() {
		return "\nTiedostonimi: " + this.filename + "\nOhjelmointikieli: " + this.language + 
			   "\nUrl l�hdekoodiin: " + this.rawUrl + "\nL�hdekoodi: " + this.content;
	}
	
	
}
