
import java.awt.EventQueue;

//import Demo.Login.Librarian_GUI.Main_frame;
//import java_project1.register.Librarian_register;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.JPanel;
//import java.awt.Point;
//import javax.swing.ImageIcon;

/*Main Class Name*/
public class Login
{
	// declaration of the variables
	private JFrame frame;
	private JTextField username_textField;
	private JButton btnLogin,btnCancel;
	private JPasswordField passwordField;
	private JLabel lblAddNewAccount;
	//private JLabel label; 
	//	private JLabel lblNewLabel_1;
	//private JLabel label_1;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Login window = new Login();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}




	//-------------------------------------------------------------------------------------------

	//db() method is static method which create connection with mysql database. db() can be call using class name

	//-------------------------------------------------------------------------------------------


	public static Connection db()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/EasyLibrarySolutions" , "root" , "root");
			return con;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "Database Connection Error . . !");
			return null;
		}

	}


	//-------------------------------------------------------------------------------------------

	//constructor of login

	//-------------------------------------------------------------------------------------------


	/* Constructor */
	public Login() 
	{
		initialize();
	}





	private void initialize()
	{
		//-------------------------------------------------------------------------------------------
		// create frame
		//-------------------------------------------------------------------------------------------
		frame = new JFrame("Librarian Login Page");
		frame.getContentPane().setBackground(new Color(105, 105, 105));
		frame.setBounds(0, 0, 1365,730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 140, 0));
		panel.setBounds(116, 47, 1132, 35);
		frame.getContentPane().add(panel);
		
		JLabel lblSignIn_1 = new JLabel("Sign IN");
		lblSignIn_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignIn_1.setForeground(Color.WHITE);
		lblSignIn_1.setFont(new Font("Likhan", Font.BOLD, 20));
		lblSignIn_1.setFocusTraversalPolicyProvider(true);
		lblSignIn_1.setFocusCycleRoot(true);
		panel.add(lblSignIn_1);



		//textfield for username
		username_textField = new JTextField();
		username_textField.setBounds(426, 228, 513, 36);
		username_textField.setFont(new Font("Likhan", Font.BOLD, 20));
		username_textField.setName("username");
		username_textField.setForeground(Color.BLACK);
		username_textField.setToolTipText("Enter Username");
		username_textField.setBackground(SystemColor.menu);
		frame.getContentPane().add(username_textField);
		username_textField.setColumns(10);



		//textfield for password
		passwordField = new JPasswordField();
		passwordField.setBounds(426, 388, 513, 36);
		passwordField.setFont(new Font("Likhan", Font.BOLD, 20));
		passwordField.setToolTipText("Enter valid password");
		passwordField.setBackground(SystemColor.menu);
		passwordField.setForeground(Color.BLACK);
		frame.getContentPane().add(passwordField);



		//cancle button for set all textfield to null
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(422, 483, 127, 44);
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				if(ae.getSource()==btnCancel)
				{
					username_textField.setText(null);
					passwordField.setText(null);
				}
			}
		});
		btnCancel.setForeground(new Color(255, 245, 238));
		btnCancel.setFont(new Font("Likhan", Font.BOLD, 22));
		btnCancel.setBackground(new Color(255, 69, 0));
		frame.getContentPane().add(btnCancel);




		//-------------------------------------------------------------------------------------------

		//Login button

		//-------------------------------------------------------------------------------------------
		btnLogin = new JButton("Login");
		btnLogin.setBounds(812, 485, 127, 41);
		btnLogin.setToolTipText("Click for Login");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent ae) {
				int flag1=0;
				if(ae.getSource()==btnLogin)
				{

					try
					{
						//create database connection
						Connection con=db();
						if(con != null)
						{
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery("select * from librarian_login");



							//checking the given information from the user with the database

							while(rs.next())
							{
								String user = rs.getString("librarian_username");
								String pass = rs.getString("librarian_password");
								if(user.equals(username_textField.getText()) && pass.equals(passwordField.getText()))
								{
									flag1=1;
									break;

								}
							}
							// if given information match then go to the main_frame
							if(flag1==1)
							{
								Main_frame.main(null);


							}
							//show incorrect information
							else
							{
								JOptionPane.showMessageDialog(null, "Username or Password is Incorrect");
							}
						}
					}

					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}

			}
		});

		btnLogin.setFont(new Font("Likhan", Font.BOLD, 22));
		btnLogin.setForeground(new Color(255, 245, 238));
		btnLogin.setBackground(new Color(30, 144, 255));
		frame.getContentPane().add(btnLogin);

		lblAddNewAccount = new JLabel("Sign UP ");
		lblAddNewAccount.setBounds(612, 545, 141, 36);
		lblAddNewAccount.setToolTipText("Click TO Register ");
		lblAddNewAccount.setFont(new Font("Likhan", Font.BOLD, 20));
		lblAddNewAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) 
			{
				Librarian_register.main(null);
			}
		});
		lblAddNewAccount.setForeground(Color.WHITE);
		frame.getContentPane().add(lblAddNewAccount);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(422, 180, 158, 36);
		lblUsername.setForeground(new Color(255, 255, 255));
		lblUsername.setFont(new Font("Likhan", Font.BOLD, 27));
		frame.getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(432, 340, 148, 36);
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setFont(new Font("Likhan", Font.BOLD, 27));
		frame.getContentPane().add(lblPassword);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(359, 210, 69, 54);
		lblNewLabel.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/LirarianUsernameIcon.png"));
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(376, 370, 47, 54);
		lblNewLabel_1.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/LirarianPassword.png"));
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblrights = new JLabel("\u00A9 2020 All Rights Reserved");
		lblrights.setBounds(346, 625, 234, 36);
		lblrights.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {


				About_us.main(null);







			}
		});
		lblrights.setForeground(Color.WHITE);
		lblrights.setFont(new Font("Likhan", Font.BOLD, 18));
		frame.getContentPane().add(lblrights);



		JLabel label = new JLabel("");
		label.setBounds(0, 0, 1365,730);
		label.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/Login_Frame_BG.jpg"));
		frame.getContentPane().add(label);


	}
}


