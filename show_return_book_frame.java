

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;


public class show_return_book_frame{

	private static JFrame frame;
	private static JTable table;
	private static JPanel contentPane;
	static String tableName ; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					show_return_book_frame window = new show_return_book_frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public  show_return_book_frame() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("All Returned Books Before 10 Days");
		frame.setBounds(108, 220, 1160, 500);
		JFrame f = new JFrame("show");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);
		
		String data[][]=null;
		String column[]=null;
		try{
			Date current_date=new Date();
			 SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
			 long mili=864000000;
			 Date date1=new Date(current_date.getTime()-mili);
			String dt = format.format(date1);
			//System.out.println(format.format(date1));
			
			
			Connection con=Login.db();
			PreparedStatement ps=con.prepareStatement("select return_date,book_title,student_name from book_title bt, book b,student s,book_return br where b.b_barcode=br.b_barcode and s.student_barcode=br.student_barcode and b.book_title_id=bt.book_title_id and br.return_date >'"+dt+"'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			
			ResultSet rs=ps.executeQuery();
			Statement stmt = con.createStatement();
			//ResultSet rr = stmt.executeQuery("select class_name from student_class;");
			
			ResultSetMetaData rsmd=rs.getMetaData();
			int cols=rsmd.getColumnCount();
			column=new String[cols];
			for(int i=1;i<=cols;i++)
			{
				column[i-1]=rsmd.getColumnName(i);
			}
			
			rs.last();
			
			int rows=rs.getRow();
			rs.beforeFirst();

			data=new String[rows][cols];
			int count=0;
			while(rs.next())
			{
				for(int i=1;i<=cols;i++)
				{
					data[count][i-1]=rs.getString(i);
					//data[count][6]= rr.getString(i);
				}
				count++;
			}
			
			/*count=0;
			while(rr.next())
			{
				for(int i=1;i<6;i++)
				{
					//data[count][i-1]=rs.getString(i);
					data[6][6]= rr.getString(i);
				}
				count++;
			}*/
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		
		table = new JTable(data,column);
		table.editingCanceled(null);
		JScrollPane sp=new JScrollPane(table);
		contentPane.add(sp, BorderLayout.CENTER);
	}
}













