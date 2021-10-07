

import java.awt.EventQueue;

//import java_project1.login.Login;

import javax.swing.JFrame;
//import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Point;

import java.sql.Connection;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.SystemColor;

public class Librarian_register 
{

	private JFrame frame;
	private JTextField txt_firstname;
	private JTextField txt_lastname,txt_username,txt_password,txt_repassword,txt_phoneno,txt_mail;
	private JButton btnCancel,btnRegister; 
	private JLabel lblLastName;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JPanel panel;
	private JLabel lblRegistationForm;

	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Librarian_register window = new Librarian_register();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Librarian_register() 
	{
		initialize();
	}

	private void initialize() {
		frame = new JFrame("Librarian Register Page");
		frame.getContentPane().setFont(new Font("Likhan", Font.BOLD, 18));
		frame.getContentPane().setLocation(new Point(1364, 725));
		//	frame.setLocation(new Point(1364, 725));
		frame.getContentPane().setFocusTraversalPolicyProvider(true);
		frame.setFocusTraversalPolicyProvider(true);
		frame.setVisible(true);
		frame.getContentPane().setFocusCycleRoot(true);
		frame.getContentPane().setBackground(new Color(105, 105, 105));
		frame.setBounds(0, 0, 1365,730);



		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(116, 31, 1132, 35);
		panel.setBackground(new Color(255, 140, 0));
		frame.getContentPane().add(panel);
		
		lblRegistationForm = new JLabel("Registation Form");
		lblRegistationForm.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistationForm.setForeground(Color.WHITE);
		lblRegistationForm.setFont(new Font("Likhan", Font.BOLD, 20));
		lblRegistationForm.setFocusTraversalPolicyProvider(true);
		lblRegistationForm.setFocusCycleRoot(true);
		panel.add(lblRegistationForm);

		JLabel lblFirstName = new JLabel("First Name ");
		lblFirstName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFirstName.setBounds(205, 106, 333, 40);
		lblFirstName.setForeground(Color.WHITE);
		lblFirstName.setFont(new Font("Likhan", Font.BOLD, 24));
		frame.getContentPane().add(lblFirstName);
				
						JLabel lblContact = new JLabel("Phone Number ");
						lblContact.setHorizontalAlignment(SwingConstants.RIGHT);
						lblContact.setBounds(215, 441, 323, 40);
						lblContact.setForeground(Color.WHITE);
						lblContact.setFont(new Font("Likhan", Font.BOLD, 24));
						frame.getContentPane().add(lblContact);
				
				lblLastName = new JLabel("Last Name");
				lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
				lblLastName.setBounds(215, 172, 323, 40);
				lblLastName.setForeground(Color.WHITE);
				lblLastName.setFont(new Font("Likhan", Font.BOLD, 24));
				frame.getContentPane().add(lblLastName);
				
				lblUsername = new JLabel("Username");
				lblUsername.setHorizontalAlignment(SwingConstants.RIGHT);
				lblUsername.setBounds(215, 241, 323, 40);
				lblUsername.setForeground(Color.WHITE);
				lblUsername.setFont(new Font("Likhan", Font.BOLD, 24));
				frame.getContentPane().add(lblUsername);
				
				lblPassword = new JLabel("Password");
				lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
				lblPassword.setBounds(215, 306, 323, 40);
				lblPassword.setForeground(Color.WHITE);
				lblPassword.setFont(new Font("Likhan", Font.BOLD, 24));
				frame.getContentPane().add(lblPassword);
		
				JLabel lblConfirmPassword = new JLabel("Confirm Password");
				lblConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
				lblConfirmPassword.setBounds(215, 375, 323, 40);
				lblConfirmPassword.setForeground(Color.WHITE);
				lblConfirmPassword.setFont(new Font("Likhan", Font.BOLD, 24));
				frame.getContentPane().add(lblConfirmPassword);
		
				JLabel lblEmail = new JLabel("Email ");
				lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
				lblEmail.setBounds(215, 510, 323, 40);
				lblEmail.setForeground(Color.WHITE);
				lblEmail.setFont(new Font("Likhan", Font.BOLD, 24));
				frame.getContentPane().add(lblEmail);

		txt_lastname = new JTextField();
		txt_lastname.setBackground(SystemColor.menu);
		txt_lastname.setBounds(590, 179, 341, 31);
		txt_lastname.setToolTipText("Enter Your Last Name");
		txt_lastname.setFont(new Font("Likhan", Font.PLAIN, 18));
		txt_lastname.setForeground(Color.BLACK);
		//	txt_lastname.setBackground(Color.GRAY);
		txt_lastname.setColumns(10);
		frame.getContentPane().add(txt_lastname);
		
				txt_firstname = new JTextField();
				txt_firstname.setBackground(SystemColor.menu);
				txt_firstname.setBounds(590, 113, 341, 31);
				txt_firstname.setToolTipText("Enter Your First Name");
				txt_firstname.setFont(new Font("Likhan", Font.PLAIN, 18));
				txt_firstname.setForeground(Color.BLACK);
				frame.getContentPane().add(txt_firstname);
				txt_firstname.setColumns(10);

		txt_username = new JTextField();
		txt_username.setBackground(SystemColor.menu);
		txt_username.setBounds(590, 248, 341, 31);
		txt_username.setToolTipText("Enter Your User Name For Login And Must Remember it..!");
		txt_username.setFont(new Font("Likhan", Font.PLAIN, 18));
		txt_username.setForeground(Color.BLACK);
		//txt_username.setBackground(Color.GRAY);
		txt_username.setColumns(10);
		frame.getContentPane().add(txt_username);

		txt_password = new JPasswordField();
		txt_password.setBackground(SystemColor.menu);
		txt_password.setBounds(590, 313, 341, 31);
		txt_password.setToolTipText("Please Set a Password  For Login And Must Remember it..!");
		txt_password.setFont(new Font("Likhan", Font.PLAIN, 18));
		txt_password.setForeground(Color.BLACK);
		//	txt_password.setBackground(Color.GRAY);
		txt_password.setColumns(10);
		frame.getContentPane().add(txt_password);

		txt_repassword = new JPasswordField();
		txt_repassword.setBackground(SystemColor.menu);
		txt_repassword.setBounds(590, 378, 341, 31);
		txt_repassword.setToolTipText("Re-Enter Password You Entered Above..!");
		txt_repassword.setFont(new Font("Likhan", Font.PLAIN, 18));
		txt_repassword.setForeground(Color.BLACK);
		//txt_repassword.setBackground(Color.GRAY);
		txt_repassword.setColumns(10);
		frame.getContentPane().add(txt_repassword);

		txt_phoneno = new JTextField();
		txt_phoneno.setBackground(SystemColor.menu);
		txt_phoneno.setBounds(590, 448, 341, 31);
		txt_phoneno.setToolTipText("Enter Your Contact Number  ");
		txt_phoneno.setForeground(Color.BLACK);
		txt_phoneno.setFont(new Font("Likhan", Font.PLAIN, 18));
		txt_phoneno.setColumns(10);
		frame.getContentPane().add(txt_phoneno);

		txt_mail = new JTextField();
		txt_mail.setBackground(SystemColor.menu);
		txt_mail.setBounds(590, 517, 341, 31);
		txt_mail.setToolTipText("Enter Your Email ID  ");
		txt_mail.setForeground(Color.BLACK);
		txt_mail.setFont(new Font("Likhan", Font.PLAIN, 18));
		txt_mail.setColumns(10);
		frame.getContentPane().add(txt_mail);

		
		
		
		//-------------------------------------------------------------------------------------------
		
		//setting null to the all textfields
		
		//-------------------------------------------------------------------------------------------
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(436, 595, 122, 40);
		btnCancel.setFocusTraversalPolicyProvider(true);
		btnCancel.setFocusCycleRoot(true);
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				txt_firstname.setText(null);
				txt_lastname.setText(null);
				txt_username.setText(null);
				txt_password.setText(null);
				txt_repassword.setText(null);
				txt_phoneno.setText(null);
				txt_mail.setText(null);
			}
		});
		btnCancel.setForeground(Color.WHITE);
		btnCancel.setFont(new Font("Likhan", Font.BOLD, 22));
		btnCancel.setBackground(new Color(255, 69, 0));
		frame.getContentPane().add(btnCancel);

		btnRegister = new JButton("Register");
		btnRegister.setBounds(805, 595, 122, 40);
		btnRegister.setToolTipText(" Click For Register");
		btnRegister.setFocusTraversalPolicyProvider(true);
		btnRegister.setFocusCycleRoot(true);
		btnRegister.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				String username = txt_username.getText();
				String password = txt_password.getText();
				String repassword = txt_repassword.getText();
				username = username.trim();
				password = password.trim();
				repassword = repassword.trim();
				if(ae.getSource()==btnRegister)
				{
					try
					{
						Connection con=null;
						con=Login.db();
						Statement stmt= con.createStatement();
						
						//-------------------------------------------------------------------------------------------
						//check if username and password is empty
						if(username.equals(""))
						{
							JOptionPane.showMessageDialog(null,"Enter UserName");
						}
						else if(password.equals(""))
						{
							JOptionPane.showMessageDialog(null,"Enter Password.");
						}
						else if(password.equals(repassword))
						{
							//-------------------------------------------------------------------------------------------
							
							//inserting users details in database
							
							//-------------------------------------------------------------------------------------------
							
							
							int result=stmt.executeUpdate("insert into librarian_login(librarian_username,librarian_password)" +"values('"+username+"','"+password+"')");
							if(result==1)
							{
								JOptionPane.showMessageDialog(null, "Successfully Registered");
								txt_firstname.setText(null);
								txt_lastname.setText(null);
								txt_username.setText(null);
								txt_password.setText(null);
								txt_repassword.setText(null);
								txt_phoneno.setText(null);
								txt_mail.setText(null);
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Password Not Matching","Alert",JOptionPane.WARNING_MESSAGE);
						}
						stmt.close();
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Dublicate Data Found");
					}
				}
			}
		});
		btnRegister.setForeground(new Color(255, 245, 238));
		btnRegister.setFont(new Font("Likhan", Font.BOLD, 22));
		btnRegister.setBackground(new Color(30, 144, 255));
		frame.getContentPane().add(btnRegister);

		JLabel lblAlreadyAccount = new JLabel("Sign IN");
		lblAlreadyAccount.setBounds(590, 645, 183, 26);
		lblAlreadyAccount.setToolTipText("Click For Login");
		lblAlreadyAccount.setFont(new Font("Likhan", Font.BOLD, 22));
		lblAlreadyAccount.setFocusTraversalPolicyProvider(true);
		lblAlreadyAccount.setFocusCycleRoot(true);
		lblAlreadyAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlreadyAccount.setForeground(Color.WHITE);
		frame.getContentPane().add(lblAlreadyAccount);
		
				JLabel lblNewLabel = new JLabel("");
				lblNewLabel.setBounds(0, 0, 1365,730);
				lblNewLabel.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/Login_Frame_BG.jpg"));
				frame.getContentPane().add(lblNewLabel);

		//-------------------------------------------------------------------------------------------
		
		// already account button so user can go to the login page
		
		//-------------------------------------------------------------------------------------------
		
		lblAlreadyAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
				Login.main(null);
			}
		});	
	}
}
