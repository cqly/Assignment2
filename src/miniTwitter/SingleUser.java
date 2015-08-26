package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class SingleUser implements User {

	private String userId;
	private List<User> followers;
	private List<User> following;
	private List<Tweet> newsfeed;
	private UserPanel userPanel;

	public SingleUser(String userId) {
		this.userId = userId;
		followers = new ArrayList<User>();
		following = new ArrayList<User>();
		newsfeed = new ArrayList<Tweet>();
	}

	public void addFollower(SingleUser user) {
		followers.add(user);
	}

	public String getID() {
		return userId;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public List<User> getFollowing() {
		return following;
	}

	public List<Tweet> getNewsfeed() {
		return newsfeed;
	}

	public String toString() {
		return this.userId;
	}

	@Override
	public boolean equals(Object o){
		User user = (User) o;		        
		return this.userId.equalsIgnoreCase(user.getID());
	}

	@Override
	public void addToNewsfeed(Tweet tweet) {
		this.newsfeed.add(tweet);
	}


	public void followUser(User user) {
		this.following.add(user);
		user.addFollower(this);
	}

	public void addFollower(User user) {
		this.followers.add(user);
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.addSingleUser(this);
	}

	@Override
	public UserPanel getUserPanel() {
		return userPanel;
	}

	@Override
	public void setUserPanel(UserPanel p) {
		this.userPanel = p;
	}
}
