package miniTwitter;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.SetChangeListener;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserPanel {

	private JFrame frmUserView;
	private JTextField textField;
	private JTextArea textArea_1;
	private JButton btnPostTweet;
	private JTextArea txtAreaNewsFeed;
	
	private User currentUser;
	private UserPanel currentUserPanel;
	private static List<UserPanel> userPanelList = new ArrayList<UserPanel>();
	private static List<User> userList = new ArrayList<User>();
	
	/**
	 * Create the application.
	 */
	private UserPanel(User user) {
		
		this.currentUser = user;
		this.currentUserPanel = this;
		initialize();
		update();
		frmUserView.setVisible(true);
	}
	
	private void update() {
		
		String msg = "";
		
		for (TextTweet tt : currentUser.getNewsfeed()) {
			msg += tt + "\n";
			
		}
		
		txtAreaNewsFeed.setText(msg);
	}
	
	public static UserPanel getInstance(User user) {
		
		
		
		if (!userList.contains(user)) {
			
			userPanelList.add(new UserPanel(user));
			userList.add(user);
			return userPanelList.get(userList.size()-1);
		}
		
		//userPanelList.indexOf(arg0)
		
		//userPanelList.indexOf(arg0)
		
		
		return null;
	}

	
	public UserPanel getCurrentUserPanel() {
		return currentUserPanel;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public static List<UserPanel> getUserPanelList() {
		return userPanelList;
	}
	

//	
//	public boolean equals(Object o){
//		
//		UserPanel up = (UserPanel) o;
//		        
//        return this.userID.equalsIgnoreCase(up.getPanelID());
//    }
//	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmUserView = new JFrame();
		frmUserView.setTitle("User View @" + currentUser.getID() + "");
		frmUserView.setBounds(100, 100, 450, 300);
		frmUserView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmUserView.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 86, 20);
		frmUserView.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnFollowUser = new JButton("Follow User");
		btnFollowUser.setBounds(155, 10, 89, 23);
		frmUserView.getContentPane().add(btnFollowUser);
		
		JTextArea txtAreaFollowing = new JTextArea();
		txtAreaFollowing.setBounds(10, 42, 293, 53);
		frmUserView.getContentPane().add(txtAreaFollowing);
		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(25, 127, 185, 20);
		frmUserView.getContentPane().add(textArea_1);
		
		btnPostTweet = new JButton("Post Tweet");
		btnPostTweet.setBounds(233, 128, 89, 23);
		frmUserView.getContentPane().add(btnPostTweet);
		
		txtAreaNewsFeed = new JTextArea();
		txtAreaNewsFeed.setBounds(10, 170, 293, 81);
		frmUserView.getContentPane().add(txtAreaNewsFeed);
		
		frmUserView.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	userList.remove(currentUser);
		    	userPanelList.remove(currentUserPanel);
		    	
		    	
		    	//System.out.println(userPanelList.indexOf(currentUser));
		    	//userPanelList.remove(userPanelList.indexOf(arg0))
		    }
		});
	}

}
