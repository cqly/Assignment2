package miniTwitter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;

public class UserPanel implements miniTwitter.Observer, Subject {

	private JFrame frmUserView;
	private JTextField txtUser;
	private JTextArea txtTweet;
	private JButton btnPostTweet;
	private JTextArea txtAreaNewsFeed;
	private JTextArea txtAreaFollowing;
	private JLabel lblMessage;
	
	private User currentUser;
	private static List<User> userList = new ArrayList<User>();
	

	private UserPanel(User user) {
		
		this.currentUser = user;
		initialize();
		frmUserView.setVisible(true);
		update();
		updateFollowingPanel();
	}
	
	@Override
	public void notifyObservers(Tweet tweet) {
		
		for (User follower : currentUser.getFollowers()) {
			follower.addToNewsfeed(tweet);
			follower.getUserPanel().update();
		}
	}
	
	@Override
	public void update() {

		String msg = "";
		
		for (Tweet tt : currentUser.getNewsfeed()) {
			msg += "  " + tt + "\n";
		}

		txtAreaNewsFeed.setText(msg);
	}
	
	public void updateFollowingPanel() {
		String following = "";
		for (User usr : currentUser.getFollowing()) {
			following += "  - " + usr + "\n";
		}
		txtAreaFollowing.setText(following);
		
	}
	
	public static UserPanel getInstance(User user) {
		
		if (!userList.contains(user)) {
			
			user.setUserPanel(new UserPanel(user));
			userList.add(user);
		}
		
		//in the case of GroupUser, it will return null
		return user.getUserPanel();
	}

	private void postTweet() {
		if (!txtTweet.getText().equalsIgnoreCase("")) {
			
			TextTweet tweet = new TextTweet(currentUser, txtTweet.getText());
			currentUser.addToNewsfeed(tweet);

			//update current panel first
			this.update();
			txtTweet.setText("");
			
			//notify the followers
			notifyObservers(tweet);
		}
	}
	
	private void followUser() {
		
		if (txtUser.getText().equalsIgnoreCase("")) {
			lblMessage.setText("Please enter the user ID");
			return;
			
		}
		
		User user = searchUser(txtUser.getText());
		if (user != null) {
			currentUser.followUser(user);
			txtUser.setText("");
			
			//graphically update the following panel
			this.updateFollowingPanel();
		}
	}
	
	private SingleUser searchUser(String userId) {
		
		lblMessage.setText("");
		
		User user = new SingleUser(userId);
		
		//check if following yourself
		if (user.equals(currentUser)) {
			lblMessage.setText("You cannot follow yourself");
			return null;
		}
		
		List<User> list = AdminPanel.getInstance().getAllUsers();
		
		//check if already follow
		if (currentUser.getFollowing().contains(user)) {
			lblMessage.setText("You are currently following that user");
			return null;
		}
		
		if (list.contains(user)) {
			User temp = list.get(list.indexOf(user));
			
			//check if it is a GroupUser
			if (temp instanceof GroupUser) {
				lblMessage.setText("Cannot follow a group");
				return null;
			}
			else {
				return (SingleUser) list.get(list.indexOf(user));
			}
		}
		else {
			lblMessage.setText("User doesn't exist");
			return null;
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUserView = new JFrame();
		frmUserView.setTitle("@" + currentUser.getID() + " User View");
		frmUserView.setBounds(100, 100, 749, 502);
		frmUserView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmUserView.getContentPane().setLayout(null);
		
		txtUser = new JTextField();
		txtUser.setBounds(10, 26, 223, 20);
		frmUserView.getContentPane().add(txtUser);
		txtUser.setColumns(10);
		txtUser.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		       followUser();
		    }
		});
		
		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				followUser();
			}
		});
		btnFollowUser.setBounds(250, 25, 133, 23);
		frmUserView.getContentPane().add(btnFollowUser);
		
		btnPostTweet = new JButton("Post Tweet");
		btnPostTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postTweet();
			}
		});
		btnPostTweet.setBounds(250, 292, 133, 23);
		frmUserView.getContentPane().add(btnPostTweet);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(418, 28, 303, 425);
		frmUserView.getContentPane().add(scrollPane);
		
		txtAreaNewsFeed = new JTextArea();
		txtAreaNewsFeed.setFont(new Font("Miriam Fixed", Font.PLAIN, 14));
		txtAreaNewsFeed.setEditable(false);
		txtAreaNewsFeed.setLineWrap(true);
		scrollPane.setViewportView(txtAreaNewsFeed);
		
		JLabel lblTweets = new JLabel("News Feed");
		lblTweets.setFont(new Font("Tunga", Font.PLAIN, 20));
		lblTweets.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblTweets);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 88, 352, 150);
		frmUserView.getContentPane().add(scrollPane_1);
		
		JLabel lblFollower = new JLabel("Currently Follow");
		lblFollower.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblFollower.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane_1.setColumnHeaderView(lblFollower);
		
		txtAreaFollowing = new JTextArea();
		txtAreaFollowing.setEditable(false);
		

		scrollPane_1.setViewportView(txtAreaFollowing);
		
		lblMessage = new JLabel("");
		lblMessage.setForeground(Color.RED);
		lblMessage.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblMessage.setBounds(10, 57, 388, 20);
		frmUserView.getContentPane().add(lblMessage);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 290, 223, 119);
		frmUserView.getContentPane().add(scrollPane_2);
		
		txtTweet = new JTextArea();
		txtTweet.setLineWrap(true);
		KeyStroke k = KeyStroke.getKeyStroke("ENTER");
		txtTweet.getInputMap().put(k, new AbstractAction () {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				postTweet();
			}
		});
		
		scrollPane_2.setViewportView(txtTweet);
		
		frmUserView.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	userList.remove(currentUser);
		    }
		});
	}
}
