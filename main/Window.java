import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Window extends Canvas {

	private static final long serialVersionUID = 7046162652717156913L;
	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	public boolean clicked = false;
	final String usernameString = "                 Input Username";
	final String passwordString = "                 Input Password";
	JTextField username;
	JTextField password;
	JLabel user;
	JLabel pass;
	JButton button;

	public Window(String title, Reader reader) {

		// jframe setup
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension((int) size.getWidth() / 2, (int) size.getHeight() / 2));
		frame.setMinimumSize(new Dimension((int) size.getWidth() / 2, (int) size.getHeight() / 2));
		frame.setMaximumSize(new Dimension((int) size.getWidth() / 2, (int) size.getHeight() / 2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);

		// jpanel setup
		JPanel myPanel = new JPanel();
		myPanel.setPreferredSize(new Dimension(200, 200));
		myPanel.setLayout(null);
		myPanel.setBackground(Color.white);

		// textinput setup
		username = new JTextField(usernameString);
		username.setSize(200, 30);
		username.setLocation((int) size.getWidth() / 4 - 100, (int) size.getHeight() / 8);

		password = new JTextField(passwordString);
		password.setSize(200, 30);
		password.setLocation((int) size.getWidth() / 4 - 100, (int) size.getHeight() / 8 + 80);

		user = new JLabel("                     USERNAME");
		user.setSize(200, 30);
		user.setLocation((int) size.getWidth() / 4 - 100, (int) size.getHeight() / 11);

		pass = new JLabel("                     PASSWORD");
		pass.setSize(200, 30);
		pass.setLocation((int) size.getWidth() / 4 - 100, (int) size.getHeight() / 4 - 40);

		// button setup
		button = new JButton("LOG IN");
		button.setSize(200, 30);
		button.setLocation((int) size.getWidth() / 4 - 100, (int) size.getHeight()/2 - (int) size.getHeight() / 8);
		button.setBackground(Color.white);
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        clicked = true;
		    }
		});

		// adding to panel
		myPanel.add(username);
		myPanel.add(password);
		myPanel.add(user);
		myPanel.add(pass);
		myPanel.add(button);

		// setting a limit of 28 characters
		username.setDocument(new JTextFieldLimit(28));
		password.setDocument(new JTextFieldLimit(28));

		// adding panel to frame
		frame.setContentPane(myPanel);

		// packing and setting visible
		frame.setVisible(true);
		frame.pack();

	}

	// return the username string
	public String getUsername() {
		return username.getText();
	}

	// returns the password string
	public String getPassword() {
		return password.getText();
	}

	// sets the character limit
	@SuppressWarnings("serial")
	class JTextFieldLimit extends PlainDocument {
		private int limit;

		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

}
