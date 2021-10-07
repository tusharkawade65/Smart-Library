import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class multiple_books {

	private JFrame frame;
	private JButton btnHello;
	private JTextField textField;
	static private String book_title;
	static private String book_author;
	static private String book_publication;
	static private String book_edition;
	static 	private int book_edition_year;
	static private float book_cost;
	//private int no_of_copies;
	static private int rack_no;
	static private int row_no;
	static int count=0;
	static private JPanel panel_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for(int i=1;i<Main_frame.bcopies;i++)
					{
						multiple_books window = new multiple_books();
						window.frame.setVisible(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public multiple_books() {


		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */

	static void accept(String book_title1,String book_author1,String book_publication1,String book_edition1,int book_edition_year1,float book_cost1,int rack_no1,int row_no1)
	{
		book_title = book_title1;
		book_author=book_author1;
		book_publication=book_publication1;
		book_edition=book_edition1;
		book_edition_year=book_edition_year1;
		book_cost=book_cost1;
		rack_no=rack_no1;
		row_no=row_no1;

		//System.out.println(book_title);
		//System.out.println(book_title1);



	}








	private void initialize() {
		frame = new JFrame("Scan Barcode For Multiple Copies");
		frame.getContentPane().setBackground(new Color(128, 128, 128));
		frame.setBounds(350, 200, 611, 407);
		//frame.setLocation(599, 909);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);


		textField = new JTextField();
		textField.setBounds(326, 126, 172, 38);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		btnHello = new JButton("Add Book");
		btnHello.setBackground(new Color(50, 205, 50));
		btnHello.setFont(new Font("Likhan", Font.BOLD, 18));
		btnHello.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "ok google");
				int book_barcode =0;
				int flag = 0;
				try
				{
					book_barcode=Integer.parseInt(textField.getText());
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please Scan Valid Barcode");
					flag = 1;
				}
				if(dublicate_entry.check_dublicate_book_barcode(book_barcode) )
				{
					JOptionPane.showMessageDialog(null, "Barcode Already Exist");
				}
				else if (flag == 0)
				{
					dbconnections.add_book_panel(book_barcode, book_title, book_author, book_publication, book_edition, book_edition_year, book_cost, Main_frame.bcopies, rack_no, row_no);
					count++;
					if( (count+1) == Main_frame.bcopies)
					{
						Main_frame.clear();
						JOptionPane.showMessageDialog(null, "Books Added SuccessFully");
						
					}
					//System.out.println(count+"\t"+Main_frame.bcopies);
					//--------------------------------------------------
								//check for availability
					//--------------------------------------------------
					
					
					
					 String book_name=book_title;
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

					
					
					
					//----------------------------------------------------
					
					
					
					frame.dispose();
				}
			}
		});
		btnHello.setBounds(248, 227, 118, 38);
		frame.getContentPane().add(btnHello);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 140, 0));
		panel.setBounds(26, 22, 559, 38);
		frame.getContentPane().add(panel);

		JLabel lblScanBarcode = new JLabel("Scan Barcode");
		lblScanBarcode.setHorizontalAlignment(SwingConstants.CENTER);
		lblScanBarcode.setForeground(Color.WHITE);
		lblScanBarcode.setFont(new Font("Likhan", Font.BOLD, 22));
		lblScanBarcode.setFocusTraversalPolicyProvider(true);
		lblScanBarcode.setFocusCycleRoot(true);
		panel.add(lblScanBarcode);

		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(SystemColor.windowBorder);
		panel_1.setBounds(57, 91, 497, 248);
		frame.getContentPane().add(panel_1);
		
		JLabel label = new JLabel("Barcode : ");
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		label.setForeground(Color.BLACK);
		label.setFont(new Font("Likhan", Font.PLAIN, 18));
		label.setBounds(110, 57, 112, 27);
		panel_1.add(label);
	}
}
