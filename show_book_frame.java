

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;


public class show_book_frame{

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
					show_book_frame window = new show_book_frame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public show_book_frame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame("All Books");
		frame.setBounds(108, 220, 1160, 500);
		JFrame f = new JFrame("show");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		frame.setContentPane(contentPane);

		String data[][]=null;
		String column[]=null;
		try
		{
			Connection con=Login.db();
			PreparedStatement ps=con.prepareStatement("select DISTINCT book_title,b_author_name,b_publication_name,b_edition,b_edition_year,rack_no,row_no,book_price from book b,book_title bt,book_author ba,book_publication bp,book_edition be,book_position bpos where bt.book_title_id=b.book_title_id and ba.b_author_id=b.b_author_id and bp.b_publication_id=b.b_publication_id and be.b_edition_id=b.b_edition_id and bpos.b_position_id=b.b_position_id",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);

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













