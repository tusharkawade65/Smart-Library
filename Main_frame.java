import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public  class Main_frame {

	private JFrame frame;
	private JTextField return_book_barcode;
	private JTextField issue_book_barcode_;
	private JTextField return_student_barcode;
	private JTextField issue_student_barcode;
	private static JTextField book_title_txtfield;
	private static JTextField book_author_txtfield;
	private static JTextField book_rack_no_txtfield;
	private static JTextField book_row_no_txtfield;
	private static JTextField book_publication_txtfield;
	private static JTextField book_edition_txtfield;
	private static JTextField book_edition_year_txtfield;
	private JTextField student_search_txtfield;
	public static JTextField book_barcode_txtfield;
	private static JTextField book_price_txtfield;
	private static JTextField book_no_of_copies_txtfield;
	private JTextField student_barcode;
	private JTextField student_name;
	private JTextField student_contact;
	private JTextField student_email;
	private JTextField student_address;
	private JTextField issue_date;
	private JTextField return_date;
	private JTabbedPane tabbedPane;
	public int bar_no;
	private JPanel panel_7;
	private JComboBox student_class ;
	private JTextField student_register_no;
	public static int bcopies=0;
	private JTextField delete_book_textField;
	private JTextField delete_student_textField;
	private JTextField fine_textField;
	private JTextField avl_textField;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_frame window = new Main_frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Main_frame() {
		initialize();
	}
	
	static void clear()
	{
		book_barcode_txtfield.setText("");

		book_author_txtfield.setText("");

		book_edition_txtfield.setText("");

		book_edition_year_txtfield.setText("");

		book_no_of_copies_txtfield.setText("");

		book_price_txtfield.setText("");

		book_publication_txtfield.setText("");

		book_rack_no_txtfield.setText("");

		book_row_no_txtfield.setText("");

		book_title_txtfield.setText("");
	}
	
	private void initialize() {
		frame = new JFrame("Easy Library Solutions");
		frame.getContentPane().setBackground(new Color(105, 105, 105));
		frame.getContentPane().setLayout(null);
		frame.setBounds(0, 0, 1365,730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Likhan", Font.BOLD, 18));
		tabbedPane.setBounds(52, 147, 1260, 535);
		frame.getContentPane().add(tabbedPane);

		issue_book();

		return_book();

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(105	, 105, 105));
		tabbedPane.addTab("Availability", null, panel_2, null);
		panel_2.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(255, 140, 0));
		panel_3.setBounds(95, 51, 1064, 41);
		panel_2.add(panel_3);

		JLabel lblBookAvailability = new JLabel("Book Availability");
		lblBookAvailability.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookAvailability.setForeground(Color.WHITE);
		lblBookAvailability.setFont(new Font("Likhan", Font.BOLD, 20));
		lblBookAvailability.setFocusTraversalPolicyProvider(true);
		lblBookAvailability.setFocusCycleRoot(true);
		panel_3.add(lblBookAvailability);

		avl_textField = new JTextField();
		avl_textField.setBounds(650, 236, 209, 35);
		panel_2.add(avl_textField);
		avl_textField.setColumns(10);


		// here we checking book Abailibility


		JButton btnCheck = new JButton("Check");
		btnCheck.setFont(new Font("Likhan", Font.BOLD, 18));
		btnCheck.setBackground(new Color(218, 165, 32));
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {



				String book_name=avl_textField.getText();
				//---------------------------------------------------------------------------------
				// here we check book is present in database or not
				//---------------------------------------------------------------------------------

				try
				{
					int found_flag=0;
					String title = null;
					int avl_flag=0;
					Connection con=Login.db();
					Statement stmt=con.createStatement();
					ResultSet rs=stmt.executeQuery("select * from book_title");
					while(rs.next())
					{
						//---------------------------------------------------------------------------------------
						//here we match the given searching information with the database  for checking it is available in database or not
						//----------------------------------------------------------------------------------------
						title=rs.getString("book_title");
						if(book_name.equals(title))
						{
							found_flag=1;
							break;
						}
					}
					if(found_flag==0)
					{
						JOptionPane.showMessageDialog(null, "Book is Not Present in Library"); 
					}
					if(found_flag==1)
					{
						ResultSet rs4=stmt.executeQuery("select * from avl");
						while(rs4.next())
						{
							String avl_book_name=rs4.getString("book_name") ;
							if(title.equals(avl_book_name))
							{
								avl_flag=1;
								break;
							}
						}

						// if book not found in available table then insert in available table

						if(avl_flag==0) 
						{
							ResultSet rs5=stmt.executeQuery("select bt.book_title,count(no_of_copies) from book b,book_title  bt where bt.book_title_id=b.book_title_id and bt.book_title='"+book_name+"' group by book_title");
							while(rs5.next())
							{
								String name=rs5.getString("book_title");
								int copy=rs5.getInt(2);
								int issue=0;
								int result=stmt.executeUpdate("insert into avl(book_name,total_copies,issue_copies)"+" values('"+name+"','"+copy+"','"+issue+"')");
								if(result==1)
								{
									if(result!=0)
									{
										int book_avl_flag=0;
										String title_book=null;
										int total_copies=0;
										int available_Copies=0;
										ResultSet rs3=stmt.executeQuery("select * from avl");
										while(rs3.next())
										{
											title_book=rs3.getString("book_name");
											total_copies=rs3.getInt("total_copies");
											available_Copies=rs3.getInt("issue_copies");
											if(title.equals(title_book))
											{
												book_avl_flag=1;
												break;
											}
										}
										if(book_avl_flag==1)
										{
											int ans=total_copies-available_Copies;
											JOptionPane.showMessageDialog(null, title_book+" has "+total_copies+ " Total Copies and Available Copies are "+ans);
											avl_textField.setText(null);
										}
									}
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Something Went Wrong");
								}
							}
						}
						else  // if book found in available table then update in available table
						{
							String mangu=null;
							int result=0;
							ResultSet rs1=stmt.executeQuery("select * from avl");
							while(rs1.next())
							{
								mangu=rs1.getString("book_name");
							}


							ResultSet rs2=stmt.executeQuery("select bt.book_title,count(no_of_copies) from book b,book_title  bt where bt.book_title_id=b.book_title_id and bt.book_title='"+book_name+"' group by book_title");
							while(rs2.next())
							{
								String name=rs2.getString("book_title");
								int copy=rs2.getInt(2);
								result=stmt.executeUpdate("update avl set book_name ='"+name+"',total_copies='"+copy+"' where book_name='"+name+"';");
								if(result!=0)
								{
									int book_avl_flag=0;
									String title_book=null;
									int total_copies=0;
									int available_Copies=0;
									ResultSet rs3=stmt.executeQuery("select * from avl");
									while(rs3.next())
									{
										title_book=rs3.getString("book_name");
										total_copies=rs3.getInt("total_copies");
										available_Copies=rs3.getInt("issue_copies");
										if(title.equals(title_book))
										{
											book_avl_flag=1;
											break;
										}

									}
									if(book_avl_flag==1)
									{
										int ans=total_copies-available_Copies;
										JOptionPane.showMessageDialog(null, title_book+" has "+total_copies+ " Total Copies and Available Copies are "+ans);
										avl_textField.setText(null);	
									}
								}
							}
						}
					}

					if(found_flag==1)
					{
						JOptionPane.showMessageDialog(null, "Please Check Spelling or Book Not Found");
					}
				}
				catch(Exception e)
				{
					//JOptionPane.showMessageDialog(null,e);

				}
			}
		});
		btnCheck.setBounds(715, 369, 129, 37);
		panel_2.add(btnCheck);

		JLabel lblEnterBookName = new JLabel(" Enter Book Name :");
		lblEnterBookName.setForeground(new Color(255, 255, 255));
		lblEnterBookName.setFont(new Font("Likhan", Font.PLAIN, 20));
		lblEnterBookName.setBounds(400, 231, 232, 41);
		panel_2.add(lblEnterBookName);

		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				avl_textField.setText(null);
			}
		});
		button.setFont(new Font("Likhan", Font.BOLD, 18));
		button.setBackground(Color.PINK);
		button.setBounds(473, 369, 137, 37);
		panel_2.add(button);
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.controlShadow);
		tabbedPane.addTab("Reports", null, panel, null);
		panel.setLayout(null);

		//===========================================================
		//here is a show student panel
		//===========================================================

		JPanel show_book_panel = new JPanel();
		show_book_panel.setBackground(SystemColor.controlShadow);
		show_book_panel.setBounds(10, 11, 1329, 485);
		panel.add(show_book_panel);
		show_book_panel.setLayout(null);

		//-----------------------------------------------------------
		//here show student frame  call
		//-----------------------------------------------------------

		JButton student_button = new JButton("SHOW STUDENTS");
		student_button.setFont(new Font("Likhan", Font.BOLD, 18));
		student_button.setBackground(new Color(144, 238, 144));
		student_button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				show_student_frame.main(null);
			}
		});
		student_button.setBounds(101, 307, 187, 56);
		show_book_panel.add(student_button);

		//-----------------------------------------------------------




		//-----------------------------------------------------------
		//here show book frame call
		//-----------------------------------------------------------


		JButton book_button = new JButton("SHOW BOOKS");
		book_button.setFont(new Font("Likhan", Font.BOLD, 18));
		book_button.setBackground(new Color(144, 238, 144));
		book_button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				show_book_frame.main(null);
			}
		});
		book_button.setBounds(401, 308, 187, 56);
		show_book_panel.add(book_button);


		//-------------------------------------------------------------




		//-----------------------------------------------------------
		//here show issued book call
		//-----------------------------------------------------------


		JButton btnIssuedBooks = new JButton("ISSUED BOOKS");
		btnIssuedBooks.setBackground(new Color(144, 238, 144));
		btnIssuedBooks.setFont(new Font("Likhan", Font.BOLD, 18));
		btnIssuedBooks.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{

				show_issued_book_frame.main(null);
			}
		});
		btnIssuedBooks.setBounds(712, 308, 187, 56);
		show_book_panel.add(btnIssuedBooks);


		//-------------------------------------------------------------




		//-----------------------------------------------------------
		//here show Return books of last 10 days call
		//-----------------------------------------------------------


		JButton btnReturnBook = new JButton("RETURN BOOK");
		btnReturnBook.setFont(new Font("Likhan", Font.BOLD, 18));
		btnReturnBook.setBackground(new Color(144, 238, 144));
		btnReturnBook.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				show_return_book_frame.main(null);
			}
		});
		btnReturnBook.setBounds(1009, 305, 187, 56);
		show_book_panel.add(btnReturnBook);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/ShowStudentIcon.png"));
		lblNewLabel.setBounds(88, 93, 200, 200);
		show_book_panel.add(lblNewLabel);

		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/ShowBookIcon.png"));
		label_2.setBounds(391, 93, 200, 200);
		show_book_panel.add(label_2);

		JLabel label_3 = new JLabel("New label");
		label_3.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/ReturnBookIcon.png"));
		label_3.setBounds(999, 93, 200, 200);
		show_book_panel.add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/IssueBookIcon.png"));
		label_4.setBounds(702, 93, 200, 200);
		show_book_panel.add(label_4);

		//-----------------------------------------------------------




		//=============================================================







		//===========================================================
		//here is a Add Book panel
		//===========================================================

		JPanel add_book_panel = new JPanel();
		add_book_panel.setBorder(new CompoundBorder());
		add_book_panel.setFont(new Font("Likhan", Font.PLAIN, 20));
		add_book_panel.setBackground(new Color(105, 105, 105));
		tabbedPane.addTab("Add Book", null, add_book_panel, null);
		add_book_panel.setLayout(null);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.LIGHT_GRAY);
		panel_5.setBounds(868, 84, 353, 315);
		add_book_panel.add(panel_5);
		panel_5.setLayout(null);

		JLabel lblPosition = new JLabel("Rack Number : ");
		lblPosition.setBounds(59, 65, 124, 27);
		lblPosition.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPosition.setForeground(new Color(0, 0, 0));
		lblPosition.setFont(new Font("Likhan", Font.PLAIN, 18));
		panel_5.add(lblPosition);

		book_rack_no_txtfield = new JTextField();
		book_rack_no_txtfield.setBounds(201, 69, 109, 21);
		book_rack_no_txtfield.setColumns(10);
		panel_5.add(book_rack_no_txtfield);

		JLabel lblRowNumber = new JLabel("Row Number : ");
		lblRowNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRowNumber.setForeground(new Color(0, 0, 0));
		lblRowNumber.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblRowNumber.setBounds(59, 182, 124, 27);
		panel_5.add(lblRowNumber);

		book_row_no_txtfield = new JTextField();
		book_row_no_txtfield.setColumns(10);
		book_row_no_txtfield.setBounds(201, 186, 109, 21);
		panel_5.add(book_row_no_txtfield);

		JPanel panel_6 = new JPanel();
		panel_6.setBackground(new Color(255, 140, 0));
		panel_6.setBounds(868, 12, 359, 35);
		add_book_panel.add(panel_6);

		JLabel lblBookPosition = new JLabel("Set Book Position ");
		panel_6.add(lblBookPosition);
		lblBookPosition.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookPosition.setForeground(Color.WHITE);
		lblBookPosition.setFont(new Font("Likhan", Font.BOLD, 20));
		lblBookPosition.setFocusTraversalPolicyProvider(true);
		lblBookPosition.setFocusCycleRoot(true);

		panel_7 = new JPanel();
		panel_7.setBackground(Color.LIGHT_GRAY);
		panel_7.setBounds(12, 84, 780, 315);
		add_book_panel.add(panel_7);
		panel_7.setLayout(null);

		book_title_txtfield = new JTextField();
		book_title_txtfield.setBounds(564, 34, 178, 24);
		panel_7.add(book_title_txtfield);
		book_title_txtfield.setColumns(10);

		JLabel lblBookTitle = new JLabel("Book Title : ");
		lblBookTitle.setBounds(416, 31, 112, 27);
		panel_7.add(lblBookTitle);
		lblBookTitle.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBookTitle.setForeground(new Color(0, 0, 0));
		lblBookTitle.setFont(new Font("Likhan", Font.PLAIN, 18));

		JLabel lblBookAuthor = new JLabel("Author : ");
		lblBookAuthor.setBounds(38, 101, 112, 27);
		panel_7.add(lblBookAuthor);
		lblBookAuthor.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBookAuthor.setForeground(new Color(0, 0, 0));
		lblBookAuthor.setFont(new Font("Likhan", Font.PLAIN, 18));

		book_author_txtfield = new JTextField();
		book_author_txtfield.setBounds(186, 104, 178, 24);
		panel_7.add(book_author_txtfield);
		book_author_txtfield.setColumns(10);

		JLabel lblPublication = new JLabel("Publication : ");
		lblPublication.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPublication.setForeground(new Color(0, 0, 0));
		lblPublication.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblPublication.setBounds(416, 101, 112, 27);
		panel_7.add(lblPublication);

		book_publication_txtfield = new JTextField();
		book_publication_txtfield.setColumns(10);
		book_publication_txtfield.setBounds(564, 104, 178, 24);
		panel_7.add(book_publication_txtfield);

		book_edition_txtfield = new JTextField();
		book_edition_txtfield.setColumns(10);
		book_edition_txtfield.setBounds(186, 172, 178, 24);
		panel_7.add(book_edition_txtfield);

		JLabel lblEdition = new JLabel("Edition : ");
		lblEdition.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEdition.setForeground(new Color(0, 0, 0));
		lblEdition.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblEdition.setBounds(38, 169, 112, 27);
		panel_7.add(lblEdition);

		book_edition_year_txtfield = new JTextField();
		book_edition_year_txtfield.setColumns(10);
		book_edition_year_txtfield.setBounds(564, 172, 178, 24);
		panel_7.add(book_edition_year_txtfield);

		JLabel lblEditionYear = new JLabel("Edition Year : ");
		lblEditionYear.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEditionYear.setForeground(new Color(0, 0, 0));
		lblEditionYear.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblEditionYear.setBounds(405, 169, 123, 27);
		panel_7.add(lblEditionYear);

		JLabel lblBarcode = new JLabel("Barcode : ");
		lblBarcode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBarcode.setForeground(new Color(0, 0, 0));
		lblBarcode.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblBarcode.setBounds(38, 31, 112, 27);
		panel_7.add(lblBarcode);

		book_barcode_txtfield = new JTextField();
		book_barcode_txtfield.setColumns(10);
		book_barcode_txtfield.setBounds(186, 34, 178, 24);
		panel_7.add(book_barcode_txtfield);


		book_price_txtfield = new JTextField();
		book_price_txtfield.setColumns(10);
		book_price_txtfield.setBounds(186, 233, 178, 24);
		panel_7.add(book_price_txtfield);

		JLabel lblBookCost = new JLabel("Price : ");
		lblBookCost.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBookCost.setForeground(new Color(0, 0, 0));
		lblBookCost.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblBookCost.setBounds(38, 230, 112, 27);
		panel_7.add(lblBookCost);

		book_no_of_copies_txtfield = new JTextField();
		book_no_of_copies_txtfield.setColumns(10);
		book_no_of_copies_txtfield.setBounds(564, 236, 178, 24);
		panel_7.add(book_no_of_copies_txtfield);

		JLabel lblNoOfCopies = new JLabel("No of Copies : ");
		lblNoOfCopies.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNoOfCopies.setForeground(new Color(0, 0, 0));
		lblNoOfCopies.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblNoOfCopies.setBounds(405, 233, 123, 27);
		panel_7.add(lblNoOfCopies);

		JLabel lblAllFieldsAre = new JLabel("All Fields are Required");
		lblAllFieldsAre.setFont(new Font("Likhan", Font.BOLD, 16));
		lblAllFieldsAre.setForeground(new Color(255, 69, 0));
		lblAllFieldsAre.setBounds(592, 288, 153, 15);
		panel_7.add(lblAllFieldsAre);

		JPanel panel_8 = new JPanel();
		panel_8.setBackground(new Color(255, 140, 0));
		panel_8.setBounds(12, 12, 780, 35);
		add_book_panel.add(panel_8);

		JLabel lblBookInformation = new JLabel("Insert Book Information");
		lblBookInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookInformation.setForeground(Color.WHITE);
		lblBookInformation.setFont(new Font("Likhan", Font.BOLD, 20));
		lblBookInformation.setFocusTraversalPolicyProvider(true);
		lblBookInformation.setFocusCycleRoot(true);
		panel_8.add(lblBookInformation);

		JButton btnAddBook = new JButton("Add Book");
		btnAddBook.setBackground(new Color(50, 205, 50));
		btnAddBook.setFont(new Font("Likhan", Font.BOLD, 20));
		btnAddBook.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int bbflag=0,bcopiesflag=0,rackflag=0;          //use for if condition on add book click event
				int bb = 0;

				int flag=0;                                    //use for message dialogue

				try
				{
					bb=Integer.parseInt(book_barcode_txtfield.getText());
					if(bb<=0)
					{
						JOptionPane.showMessageDialog(null, "Scan Valid Barcode");
						flag=1;
					}
					else
					{
						if(dublicate_entry.check_dublicate_book_barcode(bb))
						{
							JOptionPane.showMessageDialog(null, "Duplicate Barcode Found");
							flag = 1;
						}
						else
						{
							bbflag=1;
						}
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "not valid barcode");
					flag=1;

				}				String bt=book_title_txtfield.getText();
				String ba=book_author_txtfield.getText();
				String bp=book_publication_txtfield.getText();
				String be=book_edition_txtfield.getText();
				int bey=0;
				int stringflag=0;
				if(flag==0)
				{
					if(bt.trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Enter Title");
						flag=1;

					}
					else if(ba.trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Enter Author Name");
						flag=1;
					}
					else if(bp.trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Enter Publication");
						flag=1;
					}
					else if(be.trim().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Enter Edition");
						flag=1;
					}
					else if(!(book_edition_year_txtfield.getText().equals("")))
					{
						try 
						{

							String temp=book_edition_year_txtfield.getText();
							bey=Integer.parseInt(temp);
							if(bey<1600 || bey> 2020)
							{
								JOptionPane.showMessageDialog(null, "Enter Valid Year , It Must Between 1600 to 2020");
								flag=1;
							}
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "Enter Valid Year , It Must Between 1600 to 2020");
							flag=1;
						}
					}
					int bcflag=0;
					float bc=0;

					if(flag==0)
					{
						try
						{

							bc=Float.parseFloat(book_price_txtfield.getText());
							if(bc<=0 || bc >=6300000 )
							{
								flag=1;
								JOptionPane.showMessageDialog(null, "Enter Valid Price");
							}
							else
							{
								bcflag=1;
							}
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "Enter Valid Price");
							flag=1;
						}
					}
					if(flag==0)
					{
						try
						{
							bcopies=Integer.parseInt(book_no_of_copies_txtfield.getText());
							if(bcopies<=0)
							{
								flag=1;
								JOptionPane.showMessageDialog(null, "Minimum One Copy is Required");
							}
							else if(bcopies>15)
							{
								JOptionPane.showMessageDialog(null, "At a Time Only 15 Copies per Book Allowed");
								flag = 1;
							}
							else
							{
								bcopiesflag=1;

							}
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "Please Enter Valid Number of Copies");
							flag=1;
						}
					}
					int rack=0;
					if(flag==0)
					{
						try
						{
							rack=Integer.parseInt(book_rack_no_txtfield.getText());
							if(rack<=0 || rack > 50)
							{
								flag=1;
								JOptionPane.showMessageDialog(null, "Enter Valid Rack Number,it Between 1-50");
							}
							else
							{
								rackflag=1;
							}
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "Enter Valid Rack Number");
							flag=1;
						}
					}
					int rowflag=0,row=0;
					if(flag==0)
					{
						try
						{
							row=Integer.parseInt(book_row_no_txtfield.getText());

							if(row<=0)
							{
								flag=1;
								JOptionPane.showMessageDialog(null, "Enter Valid Row Number,it Between 1-10");
							}
							else
							{
								rowflag=1;
								//JOptionPane.showMessageDialog(null, " valid row");

							}
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "please enter valid row position");

							flag=1;

						}
					}

					//--------------------------------------------------------------------------------
					if(flag!=1)
					{
						if(bbflag==1 && bcflag==1 && bcopiesflag==1 && rackflag==1 && rowflag==1 )
						{
							//JOptionPane.showMessageDialog(null, "hello world");
							if(bcopies > 1)
							{
								dbconnections.add_book_panel(bb,bt,ba,bp,be,bey,bc,bcopies,rack,row);



								//multiple_books tp=new multiple_books();
								multiple_books.accept(bt,ba,bp,be,bey,bc,rack,row);
								multiple_books.main(null);
									
							}
							else 
							{
								int success=dbconnections.add_book_panel(bb,bt,ba,bp,be,bey,bc,bcopies,rack,row);
								if(success==1)
								{
									JOptionPane.showMessageDialog(null, "Book Added Successfully");

									clear();
									
									//----------------------------------------------------------------------------------------
									//code for availibility
									//----------------------------------------------------------------------------------------




									String book_name=bt;



									

									//---------------------------------------------------------------------------------
									// here we check book is present in database or not
									//---------------------------------------------------------------------------------

									try
									{
										int found_flag=0;
										String title = null;
										int avl_flag=0;
										Connection con=Login.db();
										Statement stmt=con.createStatement();
										ResultSet rs=stmt.executeQuery("select * from book_title");
										while(rs.next())
										{
											//---------------------------------------------------------------------------------------
											//here we match the given searching information with the database  for checking it is available in database or not
											//----------------------------------------------------------------------------------------
											title=rs.getString("book_title");
											if(book_name.equals(title))
											{
												found_flag=1;
												break;
											}
										}



										if(found_flag==0)
										{
											JOptionPane.showMessageDialog(null, "book not present in database"); 
										}
										if(found_flag==1)
										{
											ResultSet rs4=stmt.executeQuery("select * from avl");
											while(rs4.next())
											{
												String avl_book_name=rs4.getString("book_name") ;
												if(title.equals(avl_book_name))
												{
													avl_flag=1;
													break;
												}
											}




											if(avl_flag==0) // if book not found in avl table
											{  
												// JOptionPane.showMessageDialog(null, "ala");

												ResultSet rs5=stmt.executeQuery("select bt.book_title,count(no_of_copies) from book b,book_title  bt where bt.book_title_id=b.book_title_id and bt.book_title='"+book_name+"' group by book_title");
												while(rs5.next())
												{
													String name=rs5.getString("book_title");
													int copy=rs5.getInt(2);
													int issue=0;
													int result=stmt.executeUpdate("insert into avl(book_name,total_copies,issue_copies)"+" values('"+name+"','"+copy+"','"+issue+"')");
													if(result==1)
													{
														//JOptionPane.showMessageDialog(null, "inserted successfully");
														if(result!=0)
														{
															int book_avl_flag=0;
															String title_book=null;
															int total_copies=0;
															int available_Copies=0;
															ResultSet rs3=stmt.executeQuery("select * from avl");
															while(rs3.next())
															{
																title_book=rs3.getString("book_name");
																total_copies=rs3.getInt("total_copies");
																available_Copies=rs3.getInt("issue_copies");
																if(title.equals(title_book))
																{
																	book_avl_flag=1;
																	break;
																}
															}
															if(book_avl_flag==1)
															{
																int ans=total_copies-available_Copies;
																//JOptionPane.showMessageDialog(null, title_book+" has "+total_copies+ " total copies and available copies are "+ans);
															}
														}
													}
													else
													{
														//JOptionPane.showMessageDialog(null, "something went wrong");
													}
												}
											}





											else  // if book found in avl
											{
												// JOptionPane.showMessageDialog(null, "something went wrong22");

												String mangu=null;
												int result=0;
												ResultSet rs1=stmt.executeQuery("select * from avl");
												while(rs1.next())
												{
													mangu=rs1.getString("book_name");

												}


												ResultSet rs2=stmt.executeQuery("select bt.book_title,count(no_of_copies) from book b,book_title  bt where bt.book_title_id=b.book_title_id and bt.book_title='"+book_name+"' group by book_title");
												while(rs2.next())
												{
													String name=rs2.getString("book_title");
													int copy=rs2.getInt(2);
													result=stmt.executeUpdate("update avl set book_name ='"+name+"',total_copies='"+copy+"' where book_name='"+name+"';");
													if(result!=0)
													{
														int book_avl_flag=0;
														String title_book=null;
														int total_copies=0;
														int available_Copies=0;
														ResultSet rs3=stmt.executeQuery("select * from avl");
														while(rs3.next())
														{
															title_book=rs3.getString("book_name");
															total_copies=rs3.getInt("total_copies");
															available_Copies=rs3.getInt("issue_copies");
															if(title.equals(title_book))
															{
																book_avl_flag=1;
																break;
															}
														}
														if(book_avl_flag==1)
														{
															int ans=total_copies-available_Copies;
															//JOptionPane.showMessageDialog(null, title_book+" has "+total_copies+ " total copies and available copies are "+ans);
														}
													}
												}
											}
										}





										if(found_flag==1)
										{
											JOptionPane.showMessageDialog(null, "please check spellings or book not found");
										}
									}
									catch(Exception e)
									{
										// JOptionPane.showMessageDialog(null,e);

									}




									//----------------------------------------------------------------------------------------

								}
								else
								{
									JOptionPane.showMessageDialog(null, "something went wrong");

								}
							}
						}
						else
						{
							//flag=0;
							//System.out.println(" -"+bbflag+"-"+bcflag+" -"+bcopiesflag+" -"+rackflag+" -"+rowflag+" -"+stringflag);

						}
					} 

				}

				//----------------------------------------------------------------------------------

				/*	String bt=book_title_txtfield.getText();
				String ba=book_author_txtfield.getText();
				String bp=book_publication_txtfield.getText();
				String be=book_edition_txtfield.getText();
				String bey=book_edition_year_txtfield.getText();

				int stringflag=0;
				//int btflag=0,baflag=0,bpflag=0,beflag=0,beyflag=0;
				if(flag==0)
				//try
				{
				if(bt.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter title");

				}
				else if(ba.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter author name");
				}
				else if(bp.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter publication");
				}
				else if(be.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter edition");
				}
				else if(bey.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter year of printing");
				}
				else
				{
					stringflag=1;
					//JOptionPane.showMessageDialog(null, "string ok");

				}

				//catch(Exception e)
				//{
					//JOptionPane.showMessageDialog(null, "please fill all the fields");
			//	}

					if(bbflag==1 && bcflag==1 && bcopiesflag==1 && rackflag==1 && rowflag==1 && stringflag==1)
					{
						//JOptionPane.showMessageDialog(null, "hello world");

				dbconnections.add_book_panel(bb,bt,ba,bp,be,bey,bc,bcopies,rack,row);

					}
					else
					{
						JOptionPane.showMessageDialog(null, "Something Went Wrong");

					}

				}*/
			}

		});

		btnAddBook.setBounds(834, 434, 164, 37);
		add_book_panel.add(btnAddBook);

		JButton button_1 = new JButton("Cancel");
		button_1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
				book_barcode_txtfield.setText("");

				book_author_txtfield.setText("");

				book_edition_txtfield.setText("");

				book_edition_year_txtfield.setText("");

				book_no_of_copies_txtfield.setText("");

				book_price_txtfield.setText("");

				book_publication_txtfield.setText("");

				book_rack_no_txtfield.setText("");

				book_row_no_txtfield.setText("");

				book_title_txtfield.setText("");
			}
		});
		button_1.setFont(new Font("Likhan", Font.BOLD, 18));
		button_1.setBackground(Color.PINK);
		button_1.setBounds(326, 435, 137, 37);
		add_book_panel.add(button_1);







		//---------------------------------------------------------------------------------------------------------------	
		JPanel add_student_panel = new JPanel();
		add_student_panel.setBackground(new Color(105, 105, 105));
		tabbedPane.addTab("Add Student", null, add_student_panel, null);
		add_student_panel.setLayout(null);

		JPanel panel_9 = new JPanel();
		panel_9.setBackground(new Color(255, 140, 0));
		panel_9.setBounds(95, 24, 1064, 35);
		add_student_panel.add(panel_9);

		JLabel lblStudentInformation = new JLabel("Student Information");
		lblStudentInformation.setHorizontalAlignment(SwingConstants.CENTER);
		lblStudentInformation.setForeground(Color.WHITE);
		lblStudentInformation.setFont(new Font("Likhan", Font.BOLD, 20));
		lblStudentInformation.setFocusTraversalPolicyProvider(true);
		lblStudentInformation.setFocusCycleRoot(true);
		panel_9.add(lblStudentInformation);

		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.LIGHT_GRAY);
		panel_10.setBounds(95, 94, 1064, 325);
		add_student_panel.add(panel_10);
		panel_10.setLayout(null);

		student_barcode = new JTextField();
		student_barcode.setColumns(10);
		student_barcode.setBounds(252, 27, 245, 27);
		panel_10.add(student_barcode);

		JLabel lblBarcode_1 = new JLabel("Barcode : ");
		lblBarcode_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBarcode_1.setForeground(new Color(0, 0, 0));
		lblBarcode_1.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblBarcode_1.setBounds(68, 25, 112, 27);
		panel_10.add(lblBarcode_1);

		JLabel lblName = new JLabel("Name : ");
		lblName.setHorizontalAlignment(SwingConstants.TRAILING);
		lblName.setForeground(new Color(0, 0, 0));
		lblName.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblName.setBounds(68, 94, 112, 27);
		panel_10.add(lblName);

		student_name = new JTextField();
		student_name.setColumns(10);
		student_name.setBounds(252, 95, 245, 27);
		panel_10.add(student_name);

		JLabel lblAddress = new JLabel("Select Class : ");
		lblAddress.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAddress.setForeground(new Color(0, 0, 0));
		lblAddress.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblAddress.setBounds(41, 162, 139, 27);
		panel_10.add(lblAddress);

		JLabel lblBarcode_2 = new JLabel("Department :  ");
		lblBarcode_2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBarcode_2.setForeground(new Color(0, 0, 0));
		lblBarcode_2.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblBarcode_2.setBounds(41, 232, 139, 27);
		panel_10.add(lblBarcode_2);

		JLabel lblMobileNumber = new JLabel("Contact : ");
		lblMobileNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMobileNumber.setForeground(new Color(0, 0, 0));
		lblMobileNumber.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblMobileNumber.setBounds(585, 94, 112, 27);
		panel_10.add(lblMobileNumber);

		student_contact = new JTextField();
		student_contact.setColumns(10);
		student_contact.setBounds(769, 95, 245, 27);
		panel_10.add(student_contact);

		JLabel lblEmail = new JLabel("Email : ");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setForeground(new Color(0, 0, 0));
		lblEmail.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblEmail.setBounds(585, 162, 112, 27);
		panel_10.add(lblEmail);

		student_email = new JTextField();
		student_email.setColumns(10);
		student_email.setBounds(769, 163, 245, 27);
		panel_10.add(student_email);

		JLabel lblDepart = new JLabel("Address : ");
		lblDepart.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDepart.setForeground(new Color(0, 0, 0));
		lblDepart.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblDepart.setBounds(585, 232, 112, 27);
		panel_10.add(lblDepart);

		student_address = new JTextField();
		student_address.setColumns(10);
		student_address.setBounds(769, 233, 245, 27);
		panel_10.add(student_address);


		String[] classes= {"First Year","Second Year","Third Year"};
		student_class= new JComboBox(classes);
		student_class.setBounds(252, 165, 245, 27);
		panel_10.add(student_class);

		String[] dept = {"Computer Science","Science","Commerce","Arts","Bussiness Administration","Computer Application",};
		JComboBox student_department = new JComboBox(dept);
		student_department.setBounds(252, 233, 245, 27);
		panel_10.add(student_department);

		JLabel lblRegisterNumber = new JLabel("Register No : ");
		lblRegisterNumber.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRegisterNumber.setForeground(Color.BLACK);
		lblRegisterNumber.setFont(new Font("Likhan", Font.PLAIN, 18));
		lblRegisterNumber.setBounds(570, 26, 127, 27);
		panel_10.add(lblRegisterNumber);

		student_register_no = new JTextField();
		student_register_no.setColumns(10);
		student_register_no.setBounds(769, 27, 245, 27);
		panel_10.add(student_register_no);

		JLabel label_1 = new JLabel("All Fields are Required");
		label_1.setForeground(new Color(255, 69, 0));
		label_1.setFont(new Font("Likhan", Font.BOLD, 16));
		label_1.setBounds(860, 298, 153, 15);
		panel_10.add(label_1);

		JButton btnAddStudent = new JButton("Add Student");
		btnAddStudent.setBackground(new Color(50, 205, 50));
		btnAddStudent.setFont(new Font("Likhan", Font.BOLD, 20));
		btnAddStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 

			{
				//========================================================================


				//variable declaration
				int sb = 0,srn = 0;
				int sbflag = 0,srnflag = 0,snflag = 0,scflag = 0,smailflag = 0,sclassflag = 0,sdflag = 0,saddflag = 0;
				String sn = null,sc = null,sclass = null,smail = null,sd = null,sadd = null;
				int flag = 0 ;



				//here check for barcode		
				try 
				{
					sb = Integer.parseInt(student_barcode.getText());
					if(sb>0 && flag == 0)	
					{
						if(dublicate_entry.check_dublicate_sbarcode(sb))
						{
							JOptionPane.showMessageDialog(null, "Duplicate Barcode Found");
							flag = 1;
						}
						else
						{
							sbflag = 1;
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please Scan barcode");
						flag = 1;
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please Scan Proper Barcode");
					flag = 1;
				}





				// here check for registration number validation
				//++++++++++++++++++++++++++++++++++++++++++

				if(flag==0)
				{
					try 
					{
						srn = Integer.parseInt(student_register_no.getText());
						if(srn >0 )
						{
							if(dublicate_entry.check_dublicate_srno(srn))
							{
								JOptionPane.showMessageDialog(null, "Duplicate Register Number Found");
								flag = 1;
							}
							else
							{
								srnflag = 1;
							}
						}
						else 
						{
							JOptionPane.showMessageDialog(null, "Please Enter Valid Register Number");
							flag = 1 ;

						}
					}
					catch(Exception E)
					{
						JOptionPane.showMessageDialog(null, "Please enter valid registration number");
						flag = 1;
					}
				}
				else
				{
					//JOptionPane.showMessageDialog(null, "Please Enter Valid Register Number");
					flag = 1 ;	
				}

				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++





				//here we check for student name validation
				try
				{
					if(student_name.getText().trim().equals("") && flag==0)
					{
						JOptionPane.showMessageDialog(null, "Please Enter Name");
						flag = 1;
					}
					else
					{

						sn = student_name.getText();
						snflag = 1;
					}

				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					flag = 1;
				}




				//here check for the valid contact number
				if(flag == 0)
				{
					try
					{
						if(student_contact.getText().trim().equals("") )
						{
							JOptionPane.showMessageDialog(null, "Please Enter Contact Number");
							flag=1;
						}
						else
						{
							sc = student_contact.getText();

							if(sc.length()==10)
							{
								if(dublicate_entry.check_duplicate_contact(sc))
								{
									JOptionPane.showMessageDialog(null, "Contact Already Exist");
									flag = 1;
								}
								else
								{
									scflag = 1;
								}

							}
							else
							{
								JOptionPane.showMessageDialog(null, "lenght of mobile number must be 10 digit");
								flag=1;
							}
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Please Enter contact number");
						flag = 1;
					}

				}



				//-----------------------------------------------------------------------
				//here we get class
				//----------------------------------------------------------------------



				if(flag == 0)
				{
					sclass = (String) student_class.getSelectedItem();
					sclassflag = 1; 
				}

				if(sclassflag==0)
				{
					flag=1;
				}




				if(flag==0)
				{
					try
					{
						if(student_email.getText().trim().equals(""))
						{

							JOptionPane.showMessageDialog(null, "Please Enter Email Address");
							flag=1;

						}
						else
						{
							smail = student_email.getText();
							if(dublicate_entry.check_duplicate_email(smail))
							{
								JOptionPane.showMessageDialog(null, "Email Already Registered");
								flag = 1;
							}
							else
							{
								smailflag = 1;
							}
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Please Enter Email Address");
						flag=1;

					}
				}


				//-------------------------------------------------------------------
				//here we get selected item from the student department JComboBox
				//-------------------------------------------------------------------


				if(flag == 0)
				{
					sd = (String) student_department.getSelectedItem();
					sdflag = 1; 
				}
				if(sdflag==0)
				{
					flag=1;
				}






				if(student_address.getText().trim().equals("") && sdflag == 1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Address");
				}
				else
				{
					sadd = student_address.getText();
					saddflag = 1; 
				}

				if(sbflag==1 && srnflag==1 && snflag==1 && sclassflag==1 && scflag==1 && sdflag==1 && saddflag==1 && smailflag==1)
				{
					int result =dbconnections.dbStudentConnections(sb,srn,sn,sc,sclass,smail,sd,sadd);
					if(result == 1)
					{
						student_barcode.setText(null);
						student_register_no.setText(null);
						student_name.setText(null);
						student_contact.setText(null);
						student_email.setText(null);
						student_address.setText(null);
						student_class.setSelectedIndex(0);
						student_department.setSelectedIndex(0);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "failed");
					}
				}
				else if(flag==0)
				{
					//	System.out.println("1"+sbflag+ "2"+srnflag+ "3"+snflag+"4"+sclassflag+ "5"+scflag+ "6"+sdflag+ "7"+saddflag+ "8"+smailflag);
					JOptionPane.showMessageDialog(null, "something went wrong");
				}



			}
		});


		//========================================================================







		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//Delete book from entity
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		btnAddStudent.setBounds(822, 445, 154, 40);
		add_student_panel.add(btnAddStudent);

		JButton button_2 = new JButton("Cancel");
		button_2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				student_barcode.setText(null);
				student_register_no.setText(null);
				student_name.setText(null);
				student_contact.setText(null);
				student_email.setText(null);
				student_address.setText(null);
				student_class.setSelectedIndex(0);
				student_department.setSelectedIndex(0);
			}
		});
		button_2.setFont(new Font("Likhan", Font.BOLD, 18));
		button_2.setBackground(Color.PINK);
		button_2.setBounds(388, 448, 137, 37);
		add_student_panel.add(button_2);


		String[] e = {"Book Barcode","Book Title"};
		String dstudent[]= {"Student barcode","Register number"};

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(105, 105, 105));
		tabbedPane.addTab("Delete", null, panel_1, null);
		panel_1.setLayout(null);

		JPanel Delete_book_heading_panel = new JPanel();
		Delete_book_heading_panel.setBackground(new Color(255, 140, 0));
		Delete_book_heading_panel.setBounds(30, 45, 601, 35);
		panel_1.add(Delete_book_heading_panel);

		JLabel lblDeleteBook = new JLabel("Delete Book");
		lblDeleteBook.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteBook.setForeground(Color.WHITE);
		lblDeleteBook.setFont(new Font("Likhan", Font.BOLD, 20));
		lblDeleteBook.setFocusTraversalPolicyProvider(true);
		lblDeleteBook.setFocusCycleRoot(true);
		Delete_book_heading_panel.add(lblDeleteBook);

		JPanel delete_book_panel = new JPanel();
		delete_book_panel.setLayout(null);
		delete_book_panel.setBackground(Color.LIGHT_GRAY);
		delete_book_panel.setBounds(30, 119, 601, 340);
		panel_1.add(delete_book_panel);
		JComboBox deletebookcombobox = new JComboBox(e);
		deletebookcombobox.setBounds(227, 53, 146, 33);
		delete_book_panel.add(deletebookcombobox);

		delete_book_textField = new JTextField();
		delete_book_textField.setBounds(154, 155, 295, 41);
		delete_book_panel.add(delete_book_textField);
		delete_book_textField.setColumns(10);




		JButton btnDeleteBook = new JButton("Delete ");
		btnDeleteBook.setFont(new Font("Likhan", Font.BOLD, 18));
		btnDeleteBook.setBackground(new Color(72, 209, 204));
		btnDeleteBook.setForeground(Color.BLACK);
		btnDeleteBook.setBounds(354, 234, 128, 39);
		delete_book_panel.add(btnDeleteBook);

		JButton button_3 = new JButton("Cancel");
		button_3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				delete_book_textField.setText(null);
			}
		});
		button_3.setFont(new Font("Likhan", Font.BOLD, 18));
		button_3.setBackground(Color.PINK);
		button_3.setBounds(87, 236, 137, 37);
		delete_book_panel.add(button_3);

		JPanel delete_student_panel = new JPanel();
		delete_student_panel.setLayout(null);
		delete_student_panel.setBackground(Color.LIGHT_GRAY);
		delete_student_panel.setBounds(695, 119, 548, 340);
		panel_1.add(delete_student_panel);
		JComboBox deletestudentcombobox = new JComboBox(dstudent);
		deletestudentcombobox.setBounds(201, 53, 146, 33);
		delete_student_panel.add(deletestudentcombobox);

		delete_student_textField = new JTextField();
		delete_student_textField.setBounds(140, 149, 267, 41);
		delete_student_panel.add(delete_student_textField);
		delete_student_textField.setColumns(10);


		JButton btnDeleteStudent = new JButton("Delete ");
		btnDeleteStudent.setFont(new Font("Likhan", Font.BOLD, 18));
		btnDeleteStudent.setBackground(new Color(72, 209, 204));
		btnDeleteStudent.setForeground(Color.BLACK);
		btnDeleteStudent.setBounds(312, 231, 128, 39);
		delete_student_panel.add(btnDeleteStudent);

		JButton button_4 = new JButton("Cancel");
		button_4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				delete_student_textField.setText(null);
			}
		});
		button_4.setFont(new Font("Likhan", Font.BOLD, 18));
		button_4.setBackground(Color.PINK);
		button_4.setBounds(97, 231, 137, 39);
		delete_student_panel.add(button_4);
		btnDeleteStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index=deletestudentcombobox.getSelectedIndex();
				if(index==0)
				{
					int class_id = 0;
					int department_id = 0;
					int bar=0;
					try {
						bar=Integer.parseInt(delete_student_textField.getText());
						if(bar<=0)
						{
							JOptionPane.showMessageDialog(null, "Kindly Scan Valid Barcode");
						}
						else
						{
							try
							{
								Connection con=Login.db();
								Statement stmt=con.createStatement();
								ResultSet rs1 = stmt.executeQuery("select s.class_id,c.department_id from class c,student_department sd,student s where s.class_id=c.class_id and c.department_id=sd.department_id and student_barcode='"+bar+"'");
								while(rs1.next())
								{
									class_id=rs1.getInt("class_id");
									department_id=rs1.getInt("department_id");

								}

								int result=stmt.executeUpdate("delete from student where student_barcode='"+bar+"'");
								if(result>=1)
								{
									stmt.executeUpdate("delete from class where class_id='"+class_id+"'");
									stmt.executeUpdate("delete from student_department where department_id='"+department_id+"'");
									JOptionPane.showMessageDialog(null, "Student Deleted Successfully...");
									delete_student_textField.setText(null);
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Student Not Found");
								}
							}
							catch(Exception e)
							{
								JOptionPane.showMessageDialog(null, "Student Not Found");
							}
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Kindly Scan Valid Barcode");
					}
				}
				else
				{
					int rno=0;
					try {
						rno=Integer.parseInt(delete_student_textField.getText());
						if(rno<=0)
						{
							JOptionPane.showMessageDialog(null, "Kindly Enter Valid Register Number");
						}
						else
						{
							try
							{

								int class_id = 0;
								int department_id = 0;
								Connection con=Login.db();
								Statement stmt=con.createStatement();

								ResultSet rs1 = stmt.executeQuery("select s.class_id,c.department_id from class c,student_department sd,student s where s.class_id=c.class_id and c.department_id=sd.department_id and student_register_number='"+rno+"'");
								while(rs1.next())
								{
									class_id=rs1.getInt("class_id");
									department_id=rs1.getInt("department_id");

								}


								int result=stmt.executeUpdate("delete from student where student_register_number='"+rno+"'");
								if(result>=1)
								{
									stmt.executeUpdate("delete from class where class_id='"+class_id+"'");
									stmt.executeUpdate("delete from student_department where department_id='"+department_id+"'");
									JOptionPane.showMessageDialog(null, "Student Deleted Successfully...");
									delete_student_textField.setText(null);
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Student Not Found");
								}
							}
							catch(Exception e)
							{
								JOptionPane.showMessageDialog(null, "Student Not Found");
							}
						}
					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Kindly Scan Valid Barcode");
					}
				}

			}
		});

		JPanel Delete_student_heading_panel = new JPanel();
		Delete_student_heading_panel.setBackground(new Color(255, 140, 0));
		Delete_student_heading_panel.setBounds(695, 45, 548, 35);
		panel_1.add(Delete_student_heading_panel);

		JLabel lblDeleteStudent = new JLabel("Delete Student");
		lblDeleteStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeleteStudent.setForeground(Color.WHITE);
		lblDeleteStudent.setFont(new Font("Likhan", Font.BOLD, 20));
		lblDeleteStudent.setFocusTraversalPolicyProvider(true);
		lblDeleteStudent.setFocusCycleRoot(true);
		Delete_student_heading_panel.add(lblDeleteStudent);
		btnDeleteBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				int entity = 10;
				entity=	deletebookcombobox.getSelectedIndex();
				//'''''''''''''''''''''''''''''''''''''''''''''''''''''''
				//delete from selected barcode
				//'''''''''''''''''''''''''''''''''''''''''''''''''''''''

				int barcode=0;
				int flag=0;
				if(entity==0)
				{
					try
					{

						barcode=Integer.parseInt(delete_book_textField.getText());

					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Kindly Scan Valid Barcode");
						flag=1;
					}
					if(flag==0)
					{
						try
						{
							int book_title_id=0;
							int b_author_id=0;
							int b_publication_id=0;
							int b_edition_id=0;
							int b_position_id=0;


							Connection con = Login.db();
							Statement stmt = con.createStatement();
							ResultSet rs1 = stmt.executeQuery("select b.book_title_id,b.b_author_id,b.b_publication_id,b.b_edition_id,b.b_position_id from book b, book_title bt,book_author ba,book_publication bp,book_edition be,book_position bpos where b.book_title_id=bt.book_title_id and b.b_author_id=ba.b_author_id and b.b_publication_id=bp.b_publication_id and b.b_edition_id=be.b_edition_id and b.b_position_id=bpos.b_position_id and b_barcode='"+barcode+"'");
							while(rs1.next())
							{
								book_title_id=rs1.getInt("book_title_id");
								b_author_id=rs1.getInt("b_author_id");
								b_publication_id=rs1.getInt("b_publication_id");
								b_edition_id=rs1.getInt("b_edition_id");
								b_position_id=rs1.getInt("b_position_id");
							}

							int rs = stmt.executeUpdate("delete from book where b_barcode='"+barcode+"'");

							if(rs==1)
							{
								stmt.executeUpdate("delete from book_title where book_title_id='"+book_title_id+"'");
								stmt.executeUpdate("delete from book_author where b_author_id='"+b_author_id+"'");
								stmt.executeUpdate("delete from book_publication where b_publication_id='"+b_publication_id+"'");
								stmt.executeUpdate("delete from book_edition where b_edition_id='"+b_edition_id+"'");
								stmt.executeUpdate("delete from book_position where b_position_id='"+b_position_id+"'");

								JOptionPane.showMessageDialog(null, "Book Deleted Successfully...");
								delete_book_textField.setText(null);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Book Not Found");
							}
						}
						catch(Exception Ex)
						{
							JOptionPane.showMessageDialog(null, "Book Not Found");
						}
					}
				}



				//''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
				//Delete book from book title
				//''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''

				String bname = null;
				int flag2=0;
				if(entity==1)
				{
					try
					{
						if(delete_book_textField.getText().trim().equals(""))
						{
							JOptionPane.showMessageDialog(null, "Please Enter Title Name");
							flag2=1;
						}
						else
						{
							bname = delete_book_textField.getText();
						}

					}
					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, "Enter Valid Title");
						flag2=1;
					}
					if(flag2==0)
					{
						int delete_flag=0;

						try
						{
							Connection con1=Login.db();
							Statement stmt1=con1.createStatement();
							Connection con3=Login.db();
							Statement stmt3=con3.createStatement();
							ResultSet rs1=stmt3.executeQuery("select book_title_id from book_title where book_title='"+bname+"'");
							stmt1.executeUpdate("delete from book_title where book_title='"+bname+"'");
							Connection con2=Login.db();
							Statement stmt2=con2.createStatement();
							while(rs1.next())
							{
								int title_id=rs1.getInt("book_title_id");
								stmt2.executeUpdate("delete from book where book_title_id='"+title_id+"'");
								System.out.println(title_id);
								delete_flag++;
							}


							if(delete_flag>=1)
							{
								JOptionPane.showMessageDialog(null, "Book Deleted Successfully...");
								delete_book_textField.setText(null);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Book Not Found");
							}
						}
						catch(Exception Ex)
						{
							//JOptionPane.showMessageDialog(null, Ex);
						}
					}
				}




			}
		});

		/*if(entity.equals("Book Barcode"))
		{
			try
			{
				Connection con = Login.db();
				Statement stmt = con.createStatement();
				int rs = stmt.executeUpdate("delete * from book")
			}
			catch(Exception Ex)
			{
				JOptionPane.showMessageDialog(null, "Book Not Found");
			}
		}*/


		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



		//--------------------------------------------------------------------------------------------------------
		/*
		btnAddStudent.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				//variable declaration
				int sb = 0,srn = 0;
				int sbflag = 0,srnflag = 0,snflag = 0,scflag = 0,smailflag = 0,sclassflag = 0,sdflag = 0,saddflag = 0;
				String sn = null,sc = null,sclass = null,smail = null,sd = null,sadd = null;
				int flag = 0 ;



		//here check for barcode		
				try 
				{
					sb = Integer.parseInt(student_barcode_textfield.getText());
					if(sb>0 && flag == 0)	
					{
						sbflag = 1;
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Please Scan barcode");
						flag = 1;
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please Scan Proper Barcode");
					flag = 1;
				}





		// here check for registration number validation
				if(flag==0)
				{
				try 
				{
					srn = Integer.parseInt(student_register_no_textfield.getText());
					if(srn >0 )
					{

						srnflag = 1;			
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "Please Enter Valid Register Number");
						flag = 1 ;

					}
				}
				catch(Exception E)
				{
					JOptionPane.showMessageDialog(null, "Please enter valid registration number");
					flag = 1;
				}
				}
				else
				{
					//JOptionPane.showMessageDialog(null, "Please Enter Valid Register Number");
					flag = 1 ;	
				}






				//here we check for student name validation
				try
				{
					if(student_name_textfield.getText().trim().equals("") && flag==0)
					{
						JOptionPane.showMessageDialog(null, "Please Enter Name");
						flag = 1;
					}
					else
					{

						sn = student_name_textfield.getText();
						snflag = 1;
					}

				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Name");
					flag = 1;
				}




				//here check for the valid contact number

				try
				{
				if(student_contact_textfield.getText().trim().equals("") && flag == 0)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Contact Number");
					flag=1;
				}
				else
				{
					sc = student_contact_textfield.getText();
					scflag = 1; 
				}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please Enter contact number");
					flag = 1;
				}






				//here we get class
				if(flag == 0)
				{
					sclass = (String) student_class_field.getSelectedItem();
					sclassflag = 1; 
				}



			if(flag==0)
			{
				try
				{
					if(student_email_textfield.getText().trim().equals(""))
					{

						JOptionPane.showMessageDialog(null, "Please Enter Email Address1");
						flag=1;

					}
					else
					{
						smail = student_email_textfield.getText();
						smailflag = 1;
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Email Address2");
					flag=1;

				}
			}





				if(flag == 0)
				{
					sd = (String) student_department_field.getSelectedItem();
					sdflag = 1; 
				}






				if(student_address_textfield.getText().trim().equals("") && sdflag == 1)
				{
					JOptionPane.showMessageDialog(null, "Please Enter Address");
				}
				else
				{
					sadd = student_address_textfield.getText();
					saddflag = 1; 
				}

				if(sbflag==1 && srnflag==1 && snflag==1 && sclassflag==1 && scflag==1 && sdflag==1 && saddflag==1 && smailflag==1)
				{
					int result = Connections.dbStudentConnections(sb,srn,sn,sc,sclass,smail,sd,sadd);
					if(result == 1)
					{
						//JOptionPane.showMessageDialog(null, "Student Added");
						reset();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "failed");
					}
				}
				else if(flag==0)
				{
				//	System.out.println("1"+sbflag+ "2"+srnflag+ "3"+snflag+"4"+sclassflag+ "5"+scflag+ "6"+sdflag+ "7"+saddflag+ "8"+smailflag);
					JOptionPane.showMessageDialog(null, "something went wrong");
				}



			}
		});




		 */

		//JScrollPane scrollPane = new JScrollPane(show_book_panel);
		//scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//scrollPane.setBounds(-14, 0, 1301, 484);
		//show_book_panel.add(scrollPane);

		//--------------------------------------------------------------------

		int y = 92;
		int n = 0;
		int increment=47;


		try 
		{
			Connection con = Login.db();
			Statement stmt= con.createStatement();
			ResultSet rs = stmt.executeQuery("select book_title from book_title");
			while(rs.next())
			{
				n++;
			}
			//JOptionPane.showMessageDialog(null,n);
		}
		catch(Exception EEe )
		{

			JOptionPane.showMessageDialog(null,"Unable to Show Books");
		}


		/*	for(int i=0;i<n;i++)
		{
			show_title_textfield = new JTextField();
			show_title_textfield.setHorizontalAlignment(SwingConstants.CENTER);
			show_title_textfield.setFont(new Font("Dialog", Font.BOLD, 18));
			show_title_textfield.setEditable(false);
			show_title_textfield.setColumns(10);
			show_title_textfield.setBounds(10,y=y+increment, 240, 34);
			show_book_panel.add(show_title_textfield);

			try 
			{
				Connection con = Login.db();
				Statement stmt= con.createStatement();
				ResultSet rs = stmt.executeQuery("select book_title from book_title");

				while(rs.next())
				{
					String title = rs.getString("book_title");
					show_title_textfield.setText(title);
					break;

				}
			}
			catch(Exception e )
			{

				JOptionPane.showMessageDialog(null,"Unable to Show Books");
			}

			break;
		}
		 */


		//--------------------------------------------------------------------



		//=================================================================================================================




		student_search_txtfield = new JTextField();
		student_search_txtfield.setFont(new Font("Likhan", Font.PLAIN, 16));
		student_search_txtfield.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				student_search_txtfield.setText("");
			}
		});
		student_search_txtfield.setBounds(726, 71, 214, 37);
		student_search_txtfield.setText(" Click Here For Student Search");
		frame.getContentPane().add(student_search_txtfield);
		student_search_txtfield.setColumns(10);

		JTextField book_search_txtfield =  new JTextField();
		book_search_txtfield.setFont(new Font("Likhan", Font.PLAIN, 16));
		book_search_txtfield.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				student_search_txtfield.setText("");
			}
			@Override
			public void keyTyped(KeyEvent arg0) {

				student_search_txtfield.setText("");

			}
		});

		book_search_txtfield.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				book_search_txtfield.setText("");
			}
		});
		book_search_txtfield.setBounds(83, 71, 214, 37);
		book_search_txtfield.setText("Click Here For Book Search");
		book_search_txtfield.setColumns(10);
		frame.getContentPane().add(book_search_txtfield);










		JButton btnLogOut = new JButton("LOG OUT");
		btnLogOut.setFont(new Font("Likhan", Font.BOLD, 18));
		btnLogOut.setBounds(1216, 59, 122, 37);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) 
			{
				JFrame frm = new JFrame();
				frm.setTitle("LogOut..?");
				if(ae.getSource()==btnLogOut)
				{
					int msg = JOptionPane.showConfirmDialog(frm, "Are You Sure You Want LogOut ?");
					if(msg==JOptionPane.YES_OPTION)
					{
						Login.main(null);
					}
				}
			}
		});
		btnLogOut.setForeground(new Color(255, 255, 255));
		btnLogOut.setBackground(new Color(255, 69, 0));
		frame.getContentPane().add(btnLogOut);







		JButton btnSearchBook = new JButton("Book Search");
		btnSearchBook.setToolTipText("book search");
		btnSearchBook.setBounds(345, 73, 137, 37);
		JScrollPane ss = new JScrollPane();


		btnSearchBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) 
			{
				String search=book_search_txtfield.getText();
				if(search.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Plese Enter Something in SearchBox");
				}
				else
				{
					book_search.book_search1(search);
					book_search.main(null);
				}
			}
		});

		JLabel lblAll = new JLabel("\u00A9 2020 All Rights Are Reserved");
		lblAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {


				About_us.main(null);




			}
		});
		lblAll.setForeground(Color.WHITE);
		lblAll.setFont(new Font("Likhan", Font.BOLD, 18));
		lblAll.setBounds(1034, 12, 261, 26);
		frame.getContentPane().add(lblAll);

		JLabel pannelbg = new JLabel("");
		pannelbg.setBounds(0, 0, 1255, 513);
		pannelbg.setIcon(new ImageIcon("/home/mangesh/EclipseProject/3360922-book-wallpapers_resize_44.jpg"));




		btnSearchBook.setFont(new Font("Likhan", Font.BOLD, 18));
		btnSearchBook.setBackground(new Color(50, 205, 50));
		frame.getContentPane().add(btnSearchBook);

		JButton btnStudentSearch = new JButton("Student Search");
		btnStudentSearch.setToolTipText("student search");
		btnStudentSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String student_name=student_search_txtfield.getText();
				if(student_name.trim().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Please Enter Something in SearchBox");
				}
				else
				{
					search_student.student_search(student_name);	
					search_student.main(null);
				}


			}
		});
		btnStudentSearch.setBounds(982, 71, 145, 37);
		btnStudentSearch.setFont(new Font("Likhan", Font.BOLD, 18));
		btnStudentSearch.setBackground(new Color(50, 205, 50));
		frame.getContentPane().add(btnStudentSearch);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("/home/mangesh/EclipseProject/Project/ExternalImages/Main_Frame_BG.jpg"));
		label.setBounds(0, 0, 1365,730);
		frame.getContentPane().add(label);
		
	}




	private void issue_book()
	{
		JPanel issue_panel = new JPanel();
		issue_panel.setBackground(new Color(105, 105, 105));
		tabbedPane.addTab("Issue Book", null, issue_panel, null);
		issue_panel.setLayout(null);

		JLabel lblBookBarcode = new JLabel("Book Barcode : ");
		lblBookBarcode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBookBarcode.setFont(new Font("Likhan", Font.PLAIN, 20));
		lblBookBarcode.setForeground(Color.WHITE);
		lblBookBarcode.setBounds(460, 114, 161, 29);
		issue_panel.add(lblBookBarcode);

		JLabel lblStudentBarcode = new JLabel("Student Barcode : ");
		lblStudentBarcode.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStudentBarcode.setVerticalAlignment(SwingConstants.TOP);
		lblStudentBarcode.setForeground(Color.WHITE);
		lblStudentBarcode.setFont(new Font("Likhan", Font.PLAIN, 20));
		lblStudentBarcode.setBounds(403, 222, 218, 29);
		issue_panel.add(lblStudentBarcode);

		JButton btnIssue = new JButton("Issue");
		btnIssue.setBackground(new Color(50, 205, 50));
		btnIssue.setFont(new Font("Likhan", Font.BOLD, 18));
		btnIssue.setBounds(760, 428, 137, 37);
		issue_panel.add(btnIssue);

		issue_book_barcode_ = new JTextField();
		issue_book_barcode_.setAlignmentX(Component.RIGHT_ALIGNMENT);
		issue_book_barcode_.setBounds(679, 116, 218, 29);
		issue_panel.add(issue_book_barcode_);
		issue_book_barcode_.setColumns(10);


		issue_student_barcode = new JTextField();
		issue_student_barcode.setColumns(10);
		issue_student_barcode.setBounds(679, 224, 218, 29);
		issue_panel.add(issue_student_barcode);


		JLabel lblDate = new JLabel("Date : ");
		lblDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("Likhan", Font.PLAIN, 20));
		lblDate.setBounds(460, 319, 161, 29);
		issue_panel.add(lblDate);

		//java.util.Date dt = Calendar.getInstance().getTime();
		issue_date = new JTextField();
		issue_date.setEditable(false);
		issue_date.setToolTipText("if date is wrong please cheack your system data.");
		issue_date.setColumns(10);
		issue_date.setBounds(679, 321, 218, 29);
		issue_panel.add(issue_date);


		//-----------------------------------------------------------------------------

		//here we get current date of the system for issue book panel

		//-----------------------------------------------------------------------------


		DateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd");
		Date date = new Date();
		issue_date.setText(dateFormat.format(date));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{

				issue_book_barcode_.setText(null);
				issue_student_barcode.setText(null);

			}
		});
		btnCancel.setFont(new Font("Likhan", Font.BOLD, 18));
		btnCancel.setBackground(Color.PINK);
		btnCancel.setBounds(460, 428, 137, 37);
		issue_panel.add(btnCancel);


		btnIssue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				//int barcode_textfield=0;
				//int book_barcode_textfield_flag=0;
				int student_barcode_textfield_flag=0;
				int student_barcode=0;
				//int book_foundflag=0;
				int student_foundflag=0;
				int student_database_barcode=0;
				//int book_database_barcode=0;
				int barcode_textfield=0;
				int book_barcode_textfield_flag=0;
				//int student_barcode_textfield_flag=0;
				//int student_barcode=0;
				int book_foundflag=0;
				//int student_foundflag=0;
				//int student_database_barcode=0;
				int book_database_barcode=0;

				int flag = 0;
				if(flag == 0)
				{


					try
					{
						barcode_textfield=Integer.parseInt(issue_book_barcode_.getText());
					}

					catch(Exception e)
					{
						book_barcode_textfield_flag=1;
						flag = 1;
						JOptionPane.showMessageDialog(null, "Please Scan Book Barcode First");
					}
				}
				if(flag == 0)
				{
					try 
					{

						Connection con= Login.db();
						Statement stmt=con.createStatement();
						ResultSet rs1=stmt.executeQuery("select * from book");


						while(rs1.next())
						{
							book_database_barcode=rs1.getInt("b_barcode");
							if(barcode_textfield==book_database_barcode)
							{
								book_foundflag=1;
								break;
							}
						}
						if(book_foundflag == 0)
						{
							flag =1;
							JOptionPane.showMessageDialog(null, "Book Not Found");
						}

					}

					catch(Exception e)
					{
						flag = 1;
						JOptionPane.showMessageDialog(null, e);
					}




				}
				if(flag == 0)
				{



					try
					{
						student_barcode=Integer.parseInt(issue_student_barcode.getText());
					}

					catch(Exception e)
					{
						student_barcode_textfield_flag=1;
						flag = 1;
						JOptionPane.showMessageDialog(null, "Please Scan Student Barcode Properly");
					}


				}
				if(flag == 0)
				{

					try 
					{
						//int flag=0;
						Connection con= Login.db();
						Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery("select * from student");

						while(rs.next())
						{
							student_database_barcode=rs.getInt("student_barcode");
							if(student_barcode==student_database_barcode)
							{
								student_foundflag=1;
								break;
							}
						}
						if(student_foundflag==0)
						{
							flag =1;
							JOptionPane.showMessageDialog(null, "Student Not Found");
						}

					}

					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, e);
						flag = 1;
					}



				}
				if(book_foundflag==1 && student_foundflag==1 && flag ==0)
				{
					String dt = issue_date.getText();
					int result=dbconnections.book_issue_dbconnection(barcode_textfield,student_barcode,dt);
					if(result==1)
					{
						JOptionPane.showMessageDialog(null, "Issued Successfully");
						issue_book_barcode_.setText(null);
						issue_student_barcode.setText(null);

						try 
						{
							String bookname= null;

							Connection con = Login.db();
							Statement stmt = con.createStatement();
							ResultSet rs99 = stmt.executeQuery("select bt.book_title from book b, book_title bt where bt.book_title_id=b.book_title_id and b_barcode='"+barcode_textfield+"'");
							while(rs99.next())
							{
								bookname = rs99.getString("book_title");
							}

							int no1=0;


							ResultSet rs98 = stmt.executeQuery("select issue_copies from avl where book_name='"+bookname+"'");
							while(rs98.next())
							{
								no1=rs98.getInt("issue_copies");

							}
							//System.out.println(no1);
							no1 = no1+1;
							stmt.executeUpdate(" update avl set issue_copies='"+no1+"' where book_name='"+bookname+"'");
						}
						catch(Exception e)
						{

						}

					}
					else
					{
						JOptionPane.showMessageDialog(null, "database connection error");
					}

				}
				else
				{

					//JOptionPane.showMessageDialog(null, book_foundflag);
					//JOptionPane.showMessageDialog(null, student_foundflag);
					//JOptionPane.showMessageDialog(null, "not issued");
				}

			}
		});















	}


	private void return_book()
	{
		JPanel return_book_panel = new JPanel();
		return_book_panel.setBackground(new Color(105, 105, 105));
		tabbedPane.addTab("Return Book", null, return_book_panel, null);
		return_book_panel.setLayout(null);


		JLabel lblBookBarcode_1 = new JLabel("Book Barcode : ");
		lblBookBarcode_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBookBarcode_1.setFont(new Font("Likhan", Font.PLAIN, 20));
		lblBookBarcode_1.setForeground(Color.WHITE);
		lblBookBarcode_1.setBounds(460, 100, 161, 29);
		return_book_panel.add(lblBookBarcode_1);

		JLabel lblStudentBarcode_1 = new JLabel("Student Barcode : ");
		lblStudentBarcode_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStudentBarcode_1.setVerticalAlignment(SwingConstants.TOP);
		lblStudentBarcode_1.setForeground(Color.WHITE);
		lblStudentBarcode_1.setFont(new Font("Likhan", Font.PLAIN, 20));
		lblStudentBarcode_1.setBounds(429, 182, 192, 29);
		return_book_panel.add(lblStudentBarcode_1);

		JButton btnReturn = new JButton("Return");

		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{

				//-------------------------------------------------










				//int barcode_textfield=0;
				//int book_barcode_textfield_flag=0;
				int student_barcode_textfield_flag=0;
				int student_barcode=0;
				//int book_foundflag=0;
				int student_foundflag=0;
				int student_database_barcode=0;
				//int book_database_barcode=0;



				int flag = 0;


				int barcode_textfield=0;
				int book_barcode_textfield_flag=0;
				//int student_barcode_textfield_flag=0;
				//int student_barcode=0;
				int book_foundflag=0;
				//int student_foundflag=0;
				//int student_database_barcode=0;
				int book_database_barcode=0;
				if(flag == 0)
				{

					try
					{
						barcode_textfield=Integer.parseInt(return_book_barcode.getText());
					}

					catch(Exception e)
					{
						book_barcode_textfield_flag=1;
						flag = 1;
						JOptionPane.showMessageDialog(null, "Please Scan Book Barcode First");
					}

					if(flag==0)
					{try 
					{

						Connection con= Login.db();
						Statement stmt=con.createStatement();
						ResultSet rs1=stmt.executeQuery("select * from book");


						while(rs1.next())
						{
							book_database_barcode=rs1.getInt("b_barcode");
							if(barcode_textfield==book_database_barcode)
							{
								book_foundflag=1;
								break;
							}

						}	
						if(book_foundflag==0)
						{
							flag = 1;
							JOptionPane.showMessageDialog(null,"Book Not Found");

						}
					}


					catch(Exception e)
					{
						flag = 1;
						JOptionPane.showMessageDialog(null, e);
					}
					}
				}
				if (flag == 0)
				{
					try
					{
						student_barcode=Integer.parseInt(return_student_barcode.getText());
					}

					catch(Exception e)
					{
						student_barcode_textfield_flag=1;
						flag = 1;
						JOptionPane.showMessageDialog(null, "please scan student barcode properly");
					}


				}
				if(flag == 0)
				{
					try 
					{
						//int flag=0;
						Connection con= Login.db();
						Statement stmt=con.createStatement();
						ResultSet rs=stmt.executeQuery("select * from student");

						while(rs.next())
						{
							student_database_barcode=rs.getInt("student_barcode");
							if(student_barcode==student_database_barcode)
							{
								student_foundflag=1;
								break;
							}

						}
						if(student_foundflag==0)
						{
							flag = 1;
							JOptionPane.showMessageDialog(null, "Student Not Found");

						}

					}

					catch(Exception e)
					{
						JOptionPane.showMessageDialog(null, e);
					}

				}




				if(book_foundflag==1 && student_foundflag==1 && flag ==0)
				{
					String dt = return_date.getText();
					int result=dbconnections.book_return_dbconnection(barcode_textfield,student_barcode,dt);
					if(result==1)
					{
						try
						{
							Connection con=Login.db();
							Statement stmt=con.createStatement();
							int result2=stmt.executeUpdate("delete from book_issue where b_barcode='"+barcode_textfield+"' and student_barcode='"+student_barcode+"'");
							if(result2==1)
							{

								JOptionPane.showMessageDialog(null, "Return Successfully");

								return_book_barcode.setText(null);
								return_student_barcode.setText(null);
								fine_textField.setText(null);


								try 
								{
									String bookname= null;

									Connection con2 = Login.db();
									Statement stmt2 = con.createStatement();
									ResultSet rs99 = stmt2.executeQuery("select bt.book_title from book b, book_title bt where bt.book_title_id=b.book_title_id and b_barcode='"+barcode_textfield+"'");
									while(rs99.next())
									{
										bookname = rs99.getString("book_title");
									}

									int no1=0;


									ResultSet rs98 = stmt.executeQuery("select issue_copies from avl where book_name='"+bookname+"'");
									while(rs98.next())
									{
										no1=rs98.getInt("issue_copies");

									}
									//System.out.println(no1);
									no1 = no1-1;
									stmt.executeUpdate(" update avl set issue_copies='"+no1+"' where book_name='"+bookname+"'");
								}
								catch(Exception e)
								{

								}




							}
							else
							{
								JOptionPane.showMessageDialog(null, "Please Confirm Book is Issued or Not");
							}
						}
						catch(Exception e)
						{
							JOptionPane.showMessageDialog(null, "database connection error");
						}

					}
					else
					{
						JOptionPane.showMessageDialog(null, "database connection error");
					}

				}
			}
		});





		//---------------------------------------------------------------------------
		btnReturn.setDoubleBuffered(true);
		btnReturn.setBackground(new Color(144, 238, 144));
		btnReturn.setFont(new Font("Likhan", Font.BOLD, 18));
		btnReturn.setBounds(813, 433, 137, 37);
		return_book_panel.add(btnReturn);

		return_book_barcode = new JTextField();
		return_book_barcode.setBounds(679, 100, 218, 29);
		return_book_panel.add(return_book_barcode);
		return_book_barcode.setColumns(10);

		return_student_barcode = new JTextField();
		return_student_barcode.setColumns(10);
		return_student_barcode.setBounds(679, 185, 218, 29);
		return_book_panel.add(return_student_barcode);

		JLabel label = new JLabel("Date : ");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Likhan", Font.PLAIN, 20));
		label.setBounds(460, 268, 161, 29);
		return_book_panel.add(label);

		return_date = new JTextField();
		return_date.setEditable(false);
		return_date.setColumns(10);
		return_date.setBounds(679, 272, 218, 29);
		return_book_panel.add(return_date);

		DateFormat dateFormat = new SimpleDateFormat("yyy/MM/dd");
		Date date = new Date();
		return_date.setText(dateFormat.format(date));






		//-----------------------------------------------------------------------------------------
		//Fine button code
		//-----------------------------------------------------------------------------------------




		fine_textField = new JTextField();
		fine_textField.setEditable(false);
		fine_textField.setColumns(10);
		fine_textField.setBounds(679, 356, 218, 29);
		return_book_panel.add(fine_textField);



		JButton fine_button = new JButton("Fine");
		fine_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				float fineamt=0;
				Date issue_date=null;
				Date current_date=null;
				int student_barcode1=0;
				int book_barcode1=0;
				int student_barcode_textfield_flag1=0;

				try 
				{
					Connection con=Login.db();
					Statement stmt=con.createStatement();



					try
					{
						student_barcode1=Integer.parseInt(return_student_barcode.getText());
					}

					catch(Exception e)
					{
						student_barcode_textfield_flag1=1;
						JOptionPane.showMessageDialog(null, "please scan student barcode properly");
					}
					if(student_barcode_textfield_flag1==0)
					{
						try
						{
							book_barcode1=Integer.parseInt(return_book_barcode.getText());
						}

						catch(Exception e)
						{
							student_barcode_textfield_flag1=1;
							JOptionPane.showMessageDialog(null, "please scan book barcode properly");
						}


					}

					//here we get issue date from the issue table;

					ResultSet rs=stmt.executeQuery("select issue_date from book_issue where b_barcode='"+book_barcode1+"' and student_barcode='"+student_barcode1+"';");
					while(rs.next())
					{
						issue_date=rs.getDate("issue_date");
						//	System.out.println(issue_date);
					}
					String temp_date = return_date.getText();

					//here we send return date to the database for converting string to the date

					stmt.executeUpdate("insert into temp_date(tempdate)"+"values('"+temp_date+"')");

					//here we get current date which we send on above line to the database

					ResultSet rs1=stmt.executeQuery("select * from temp_date");

					while(rs1.next())
					{
						current_date=rs1.getDate("tempdate");
						//System.out.println(issue_date);
					}

					//here we delete date which we earlier inserted into temp_date table

					int result=stmt.executeUpdate("delete from temp_date");
					//System.out.println(current_date);


					//here we calculate total days

					long diffrence=current_date.getTime() - issue_date.getTime();


					//System.out.println(date1);



					float total_days=(diffrence / (1000*60*60*24));
					// System.out.println(total_days);
					fineamt=1*total_days;
					String s=String.valueOf(fineamt);
					if(total_days>10)
					{
						fine_textField.setText(s);
					}
					else
					{
						fine_textField.setText("0.0");
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null,"Unable to calculate fine please check barcodes");
				}

			}
		});
		fine_button.setFont(new Font("Likhan", Font.BOLD, 18));
		fine_button.setDoubleBuffered(true);
		fine_button.setBackground(new Color(218, 165, 32));
		fine_button.setBounds(619, 433, 137, 37);
		return_book_panel.add(fine_button);



		JLabel lblFineAmount = new JLabel("Fine Amount :");
		lblFineAmount.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFineAmount.setForeground(Color.WHITE);
		lblFineAmount.setFont(new Font("Likhan", Font.PLAIN, 20));
		lblFineAmount.setBounds(429, 356, 192, 29);
		return_book_panel.add(lblFineAmount);

		JButton button = new JButton("Cancel");
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				return_book_barcode.setText(null);
				return_student_barcode.setText(null);
				fine_textField.setText(null);
			}
		});
		button.setFont(new Font("Likhan", Font.BOLD, 18));
		button.setBackground(Color.PINK);
		button.setBounds(429, 433, 137, 37);
		return_book_panel.add(button);

	}
}

