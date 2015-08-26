package miniTwitter;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class AdminPanel {

	private JFrame frmAdminPanel;
	private JTextField txtboxUserId;

	private JLabel testLabel;
	private JScrollPane scrollPane;
	private JLabel lblUserId;
	private JLabel lblGroupId;
	private JTextField txtboxGroupId;
	private JButton btnAddNewGroup;
	private JLabel lblMessage;
	private JTree userTree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode rootNode;
	private JButton btnUserView;
	private JLabel lblDfrgyertyer;
	private JButton btnNewButton;
	private JButton btnShowTotalGroups;
	private JButton btnTotalMessages;
	private JButton btnNumber;
	private JLabel lblStat;
	private List<User> userList;
	private static AdminPanel instance = null;

	private AdminPanel() {
		userList = new ArrayList<User>();
		initialize();
		frmAdminPanel.setVisible(true);
	}

	public static AdminPanel getInstance()
	{
		if (instance == null)
		{
			instance = new AdminPanel();
		}
		return instance;
	}

	private boolean ValidateTextBox(JTextField tbox, String type) {
		lblMessage.setText("");

		if (tbox.getText().equalsIgnoreCase("")) {
			lblMessage.setText("Please enter " + type + " ID");
			return false;
		}

		return true;
	}


	private void startUserView() {

		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)userTree.getLastSelectedPathComponent();

		if (currentNode == null) {
			lblMessage.setText("Please select a user from the list");
			return;
		}

		Object nodeInfo = currentNode.getUserObject();

		if (nodeInfo instanceof GroupUser) {
			lblMessage.setText("User view is not available for group");
			return;
		}
		else if (nodeInfo instanceof SingleUser) {
			UserPanel.getInstance((SingleUser) nodeInfo);
		}
	}


	private void addNewUser() {

		if (!ValidateTextBox(txtboxUserId, "an user")) {
			return;
		}	

		User user = new SingleUser(txtboxUserId.getText());

		//user already exists
		if (userList.contains(user)) {
			lblMessage.setText("This ID is already taken");
			return;
		}

		this.addObject(user);
		userList.add(user);

		txtboxUserId.setText("");
		txtboxUserId.requestFocus();
	}


	private void addNewGroup() {

		if (!ValidateTextBox(txtboxGroupId, "a group")) {
			return;
		}	

		User user = new GroupUser(txtboxGroupId.getText());

		if (userList.contains(user)) {
			lblMessage.setText("This ID is already taken");
			return;
		}

		this.addObject(user);
		userList.add(user);

		txtboxGroupId.setText("");
		txtboxGroupId.requestFocus();
	}

	public List<User> getAllUsers() {
		return userList;
	}


	private void displayTotalUsers() {

		Stats stats = new Stats();

		for (User user : userList)  {
			user.accept(stats);
		}

		lblStat.setText("Total number of users: " + String.valueOf(stats.getTotalSingleUser()));
	}

	private void displayTotalGroups() {

		Stats stats = new Stats();

		for (User user : userList)  {
			user.accept(stats);

		}

		lblStat.setText("Total number of groups: " + String.valueOf(stats.getTotalGroupUser()));
	}

	private void displayTotalMessages() {

		Stats stats = new Stats();

		for (Tweet t : getAllTweets()) {
			t.accept(stats);
		}

		lblStat.setText("Total messages: " + String.valueOf(stats.getTotalMessages()));
	}

	private void displayTotalPossitivePosts() {

		Stats stats = new Stats();

		for (Tweet t : getAllTweets()) {
			t.accept(stats);
		}

		lblStat.setText("Total possitive posts: " + String.valueOf(stats.getTotalPossitiveTweet()));
	}

	private List<Tweet> getAllTweets() {

		List<Tweet> allTweet = new ArrayList<Tweet>();

		for (User user : userList)  {
			for (Tweet t : user.getNewsfeed()) {
				if (t.getUser().equals(user)) {
					allTweet.add(t);
				}
			}
		}
		return allTweet;
	}


	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {

		DefaultMutableTreeNode parentNode = null;
		DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)userTree.getLastSelectedPathComponent();
		TreePath parentPath = userTree.getSelectionPath();


		if (parentPath == null) {
			parentNode = rootNode;
		}

		else {
			Object nodeInfo = currentNode.getUserObject();

			//If currently selected is a user, the parent of the node about to be added
			//will be the current node's parent
			if (nodeInfo instanceof SingleUser) {
				parentNode = (DefaultMutableTreeNode) currentNode.getParent();
			}

			//If currently selected is a group, the parent of the node about to be added
			//will be the current node
			else if (nodeInfo instanceof GroupUser) {

				parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
			}
		}
		Object parentNodeInfo = parentNode.getUserObject();

		if (parentNodeInfo instanceof GroupUser) {
			((GroupUser) parentNodeInfo).addUserToGroup((User) child);
		}

		return addIntoTree(parentNode, child);
	}

	public DefaultMutableTreeNode addIntoTree(DefaultMutableTreeNode parent, Object child) {

		DefaultMutableTreeNode childNode =	new DefaultMutableTreeNode(child);
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		//Make sure the user can see the new node.
		userTree.scrollPathToVisible(new TreePath(childNode.getPath()));
		return childNode;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdminPanel = new JFrame();
		frmAdminPanel.setResizable(false);
		frmAdminPanel.setTitle("Admin Panel");
		frmAdminPanel.setBounds(100, 100, 809, 551);
		frmAdminPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdminPanel.getContentPane().setLayout(null);


		User root = new GroupUser("Root"); 
		userList.add(root);
		rootNode = new DefaultMutableTreeNode(root);		

		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());

		JButton btnAddNewUser = new JButton("Add user");
		btnAddNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewUser();
			}
		});
		btnAddNewUser.setBounds(666, 46, 127, 23);
		frmAdminPanel.getContentPane().add(btnAddNewUser);

		txtboxUserId = new JTextField();
		txtboxUserId.setBounds(457, 47, 180, 20);
		frmAdminPanel.getContentPane().add(txtboxUserId);
		txtboxUserId.setColumns(10);
		txtboxUserId.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewUser();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 325, 523);
		frmAdminPanel.getContentPane().add(scrollPane);

		userTree = new JTree(treeModel);
		scrollPane.setViewportView(userTree);

		userTree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
		userTree.setShowsRootHandles(true);

		testLabel = new JLabel("Users Tree Structure");
		testLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		testLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(testLabel);

		lblDfrgyertyer = new JLabel("    ");
		lblDfrgyertyer.setVerticalAlignment(SwingConstants.TOP);
		scrollPane.setRowHeaderView(lblDfrgyertyer);

		lblUserId = new JLabel("User ID:");
		lblUserId.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblUserId.setBounds(372, 48, 75, 14);
		frmAdminPanel.getContentPane().add(lblUserId);

		lblGroupId = new JLabel("Group ID:");
		lblGroupId.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblGroupId.setBounds(372, 93, 89, 14);
		frmAdminPanel.getContentPane().add(lblGroupId);

		txtboxGroupId = new JTextField();
		txtboxGroupId.setBounds(457, 92, 180, 20);
		frmAdminPanel.getContentPane().add(txtboxGroupId);
		txtboxGroupId.setColumns(10);
		txtboxGroupId.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addNewGroup();
			}
		});

		btnAddNewGroup = new JButton("Add group");
		btnAddNewGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addNewGroup();
			}
		});
		btnAddNewGroup.setBounds(666, 91, 127, 23);
		frmAdminPanel.getContentPane().add(btnAddNewGroup);

		lblMessage = new JLabel("");
		lblMessage.setForeground(Color.RED);
		lblMessage.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblMessage.setBounds(372, 139, 368, 20);
		frmAdminPanel.getContentPane().add(lblMessage);

		btnUserView = new JButton("User View");
		btnUserView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startUserView();
			}
		});
		btnUserView.setBounds(374, 225, 210, 40);
		frmAdminPanel.getContentPane().add(btnUserView);

		btnNewButton = new JButton("Total users");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayTotalUsers();
			}
		});
		btnNewButton.setBounds(372, 374, 189, 40);
		frmAdminPanel.getContentPane().add(btnNewButton);

		btnShowTotalGroups = new JButton("Total groups");
		btnShowTotalGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayTotalGroups();
			}
		});
		btnShowTotalGroups.setBounds(583, 374, 189, 40);
		frmAdminPanel.getContentPane().add(btnShowTotalGroups);

		btnTotalMessages = new JButton("Total messages");
		btnTotalMessages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayTotalMessages();
			}
		});
		btnTotalMessages.setBounds(372, 442, 189, 40);
		frmAdminPanel.getContentPane().add(btnTotalMessages);

		btnNumber = new JButton("Total positive posts");
		btnNumber.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				displayTotalPossitivePosts();
			}
		});
		btnNumber.setBounds(583, 442, 189, 40);
		frmAdminPanel.getContentPane().add(btnNumber);

		JLabel lblAddNewUser = new JLabel("Add new user or group");
		lblAddNewUser.setForeground(new Color(0, 0, 128));
		lblAddNewUser.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblAddNewUser.setBounds(349, 10, 368, 20);
		frmAdminPanel.getContentPane().add(lblAddNewUser);

		JLabel lblClickToOpen = new JLabel("Click to open user window");
		lblClickToOpen.setForeground(new Color(0, 0, 128));
		lblClickToOpen.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblClickToOpen.setBounds(349, 183, 368, 20);
		frmAdminPanel.getContentPane().add(lblClickToOpen);

		JLabel lblUserStatistics = new JLabel("User statistics");
		lblUserStatistics.setForeground(new Color(0, 0, 128));
		lblUserStatistics.setFont(new Font("Trebuchet MS", Font.PLAIN, 16));
		lblUserStatistics.setBounds(349, 300, 368, 20);
		frmAdminPanel.getContentPane().add(lblUserStatistics);

		lblStat = new JLabel("");
		lblStat.setForeground(Color.RED);
		lblStat.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblStat.setBounds(372, 331, 368, 20);
		frmAdminPanel.getContentPane().add(lblStat);
	}

	class MyTreeModelListener implements TreeModelListener {
		public void treeNodesChanged(TreeModelEvent e) {
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

			/*
			 * If the event lists children, then the changed
			 * node is the child of the node we've already
			 * gotten.  Otherwise, the changed node and the
			 * specified node are the same.
			 */

			int index = e.getChildIndices()[0];
			node = (DefaultMutableTreeNode)(node.getChildAt(index));

		}
		public void treeNodesInserted(TreeModelEvent e) {
		}
		public void treeNodesRemoved(TreeModelEvent e) {
		}
		public void treeStructureChanged(TreeModelEvent e) {
		}
	}
}
