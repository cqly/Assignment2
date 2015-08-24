package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class GroupUser implements User {

	private String groupID;
	private List<SingleUser> followers;
	private List<SingleUser> following;
	private List<String> newsfeed;
	private List<User> userList;
	
	public GroupUser(String groupId) {
		
		this.groupID = groupId;
		followers = new ArrayList<SingleUser>();
		following = new ArrayList<SingleUser>();
		newsfeed = new ArrayList<String>();
		userList = new ArrayList<User>();
	}
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SingleUser> getFollowers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SingleUser> getFollowing() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getNewsfeed() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addUser(User user) {
		userList.add(user);
	}
	
	public List<User> getUsersInGroup() {
		return userList;
	}
	
	public String toString() {
		return "[GROUP] " + groupID;
	}

}