package miniTwitter;

public class TextTweet implements Tweet {
	
	private User user;
	private String message;
	
	
	public TextTweet(User user, String message) {
		this.user = user;
		this.message = message;
	}

	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return "[" + user.getID() + "] " + message;
	}
}
