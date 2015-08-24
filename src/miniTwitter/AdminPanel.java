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
	
	private List<User> userList;
	private static AdminPanel instance = null;
	
	/**
	 * Create the application.
	 */
	private AdminPanel() {
		initialize();
		userList = new ArrayList<User>();
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
		
		//check if user id already exist
		
		return true;
	}
	
	
	
	private void AddNewUser() {
				
		if (!ValidateTextBox(txtboxUserId, "an user")) {
			return;
		}	
	
		
		User user = new SingleUser(txtboxUserId.getText());
		this.addObject(user);
		userList.add(user);
        
        txtboxUserId.setText("");
        txtboxUserId.requestFocus();
        
//		User p1Name = new User("Parent 1");
//		User p2Name = new User("Parent 2");
////		User c1Name = new User("Child 1");
////		User c2Name = new User("Child 2");
//// 
//        DefaultMutableTreeNode p1, p2;
//// 
//        p1 = this.addObject(null, p1Name);
//        p2 = this.addObject(null, p2Name);   //add group to root
//        this.addObject(p1, c2Name);
// 
//        this.addObject(p2, c1Name);
//        this.addObject(p2, c2Name);
		

		
	}
	
	private void setmessage(String s) {
		lblMessage.setText(s);
	}
	
	private void AddNewGroup() {
	
		if (!ValidateTextBox(txtboxGroupId, "a group")) {
			return;
		}	
		
		User user = new GroupUser(txtboxGroupId.getText());
		this.addObject(user);
		userList.add(user);
        
		txtboxGroupId.setText("");
		txtboxGroupId.requestFocus();
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
	
	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {
        DefaultMutableTreeNode parentNode = null;
        DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)userTree.getLastSelectedPathComponent();
        TreePath parentPath = userTree.getSelectionPath();
        
        
        if (parentPath == null) {
            parentNode = rootNode;
        } else {
        	        	        	
        	Object nodeInfo = currentNode.getUserObject();
        	
        	if (nodeInfo instanceof SingleUser) {
        		parentNode = (DefaultMutableTreeNode) currentNode.getParent();
        	}
        	else if (nodeInfo instanceof GroupUser) {
        		parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
        	}
        		
        	
        	System.out.println(nodeInfo.getClass());
        	

        }
        
        //lblMessage.setText(parentNode);
 
        return addObject(parentNode, child, true);
    }
	
	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
			return addObject(parent, child, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		
		DefaultMutableTreeNode childNode =	new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		//It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		//Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			userTree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdminPanel = new JFrame();
		frmAdminPanel.setTitle("Admin Panel");
		frmAdminPanel.setBounds(100, 100, 729, 486);
		frmAdminPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdminPanel.getContentPane().setLayout(null);
						
		
		rootNode = new DefaultMutableTreeNode("Root");
		treeModel = new DefaultTreeModel(rootNode);
	    treeModel.addTreeModelListener(new MyTreeModelListener());
		
		JButton btnAddNewUser = new JButton("Add user");
		btnAddNewUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddNewUser();
			}
		});
		btnAddNewUser.setBounds(599, 30, 104, 23);
		frmAdminPanel.getContentPane().add(btnAddNewUser);
		
		txtboxUserId = new JTextField();
		txtboxUserId.setBounds(434, 31, 143, 20);
		frmAdminPanel.getContentPane().add(txtboxUserId);
		txtboxUserId.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 325, 437);
		frmAdminPanel.getContentPane().add(scrollPane);
		
		userTree = new JTree(treeModel);
		scrollPane.setViewportView(userTree);
		
		//userTree.setEditable(true);
		userTree.getSelectionModel().setSelectionMode (TreeSelectionModel.SINGLE_TREE_SELECTION);
		userTree.setShowsRootHandles(true);
		
		testLabel = new JLabel("Users Tree Structure");
		testLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		testLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(testLabel);
		
		lblUserId = new JLabel("User ID:");
		lblUserId.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblUserId.setBounds(335, 34, 75, 14);
		frmAdminPanel.getContentPane().add(lblUserId);
		
		lblGroupId = new JLabel("Group ID:");
		lblGroupId.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblGroupId.setBounds(335, 79, 89, 14);
		frmAdminPanel.getContentPane().add(lblGroupId);
		
		txtboxGroupId = new JTextField();
		txtboxGroupId.setBounds(434, 78, 143, 20);
		frmAdminPanel.getContentPane().add(txtboxGroupId);
		txtboxGroupId.setColumns(10);
		
		btnAddNewGroup = new JButton("Add group");
		btnAddNewGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddNewGroup();
			}
		});
		btnAddNewGroup.setBounds(599, 77, 104, 23);
		frmAdminPanel.getContentPane().add(btnAddNewGroup);
		
		lblMessage = new JLabel("asdf");
		lblMessage.setForeground(Color.RED);
		lblMessage.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		lblMessage.setBounds(335, 126, 368, 20);
		frmAdminPanel.getContentPane().add(lblMessage);
	}
}
