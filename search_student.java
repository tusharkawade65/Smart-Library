

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;


public class search_student {

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
					search_student window = new search_student();
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
	public search_student() {
		initialize();
	}
	static String student_name=null;

	static void student_search(String temp_student_name)
	{
		student_name=temp_student_name;
	}













	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Searching Student...");
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
			Connection con=Login.db();
			PreparedStatement ps=con.prepareStatement("select student_register_number,student_name,student_contact,student_mail,class_name,department_name from student s,class c,student_department sd where sd.department_id=c.department_id and c.class_id=s.class_id and student_name like '%"+student_name+"%'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

			ResultSet rs=ps.executeQuery();
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
				}
				count++;
			}
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













