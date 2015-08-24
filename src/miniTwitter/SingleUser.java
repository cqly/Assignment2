package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class SingleUser implements User {
	
	private String userId;
	private List<SingleUser> followers;
	private List<SingleUser> following;
	private List<TextTweet> newsfeed;
	
	public SingleUser(String userId) {
		this.userId = userId;
		followers = new ArrayList<SingleUser>();
		following = new ArrayList<SingleUser>();
		newsfeed = new ArrayList<TextTweet>();
		
		newsfeed.add(new TextTweet(this, "asdfsdf"));
		newsfeed.add(new TextTweet(this, "asdfsdfgdfdf"));
		newsfeed.add(new TextTweet(this, "asdfshrttydf"));
	}
	
	public void addFollower(SingleUser user) {
		followers.add(user);
	}

	public String getID() {
		return userId;
	}

	public List<SingleUser> getFollowers() {
		return followers;
	}

	public List<SingleUser> getFollowing() {
		return following;
	}

	public List<TextTweet> getNewsfeed() {
		return newsfeed;
	}
	

	public void setNewsfeed(List<TextTweet> newsfeed) {
		this.newsfeed = newsfeed;
	}
	
	public String toString() {
	    return this.userId;
	}
	
	@Override
	public boolean equals(Object o){
		
		User user = (User) o;		        
        return this.userId.equalsIgnoreCase(user.getID());
    }
		
	
}
