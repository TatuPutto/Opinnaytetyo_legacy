package tatuputto.opinnaytetyo.gists;

public class GistOwner {
	private String login;
	private String avatarUrl;
	
	
	public GistOwner(String login, String avatarUrl) {
		this.login = login;
		this.avatarUrl = avatarUrl;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	
}
