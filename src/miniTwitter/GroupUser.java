package miniTwitter;

import java.util.ArrayList;
import java.util.List;

public class GroupUser implements User {

	private String groupID;
	private List<User> userList;
	private List<Tweet> tweetList = new ArrayList<Tweet>();
	
	public GroupUser(String groupId) {
		
		this.groupID = groupId;
		userList = new ArrayList<User>();
	}
	
	@Override
	public String getID() {
		return groupID;
	}

	@Override
	public List<User> getFollowers() {
		// not available for GroupUser
		return null;
	}

	@Override
	public List<User> getFollowing() {
		// not available for GroupUser
		return null;
	}

	@Override
	public List<Tweet> getNewsfeed() {
		// return an empty list
		return tweetList;
	}
	
	public void addUserToGroup(User user) {
		userList.add(user);
	}
	
	public List<User> getUsersInGroup() {
		return userList;
	}
	
	public String toString() {
		return "[GROUP] " + groupID;
	}

	@Override
	public void addToNewsfeed(Tweet t) {
		// not available for GroupUser
	}

	@Override
	public void addFollower(User u) {
		// not available for GroupUser
	}

	@Override
	public void followUser(User u) {
		// not available for GroupUser
	}
	
	@Override
	public boolean equals(Object o){
		
		User group = (User) o;		        
        return this.groupID.equalsIgnoreCase(group.getID());
    }

	@Override
	public void accept(Visitor visitor) {
		visitor.addGroupUser(this);
		
	}

	@Override
	public UserPanel getUserPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserPanel(UserPanel p) {
		// not available for GroupUser
	}
}