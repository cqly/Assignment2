package miniTwitter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TextTweet implements Tweet {
	
	private User user;
	private String message;
	private String time;
	
	public TextTweet(User user, String message) {
		
		
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        
        this.user = user;
		this.message =  message;
		this.time = sdf.format(cal.getTime());
        
	}

	public User getUser() {
		return user;
	}

	public String getMessage() {
		return message;
	}

	public String toString() {
		return time + " @" + user.getID() + ":  " + message;
	}
}
