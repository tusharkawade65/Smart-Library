import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class dublicate_entry 
{
	//--------------------------------------------------------------------
	//function for checking the duplicate entry of contact in database
	//---------------------------------------------------------------------

	static boolean check_duplicate_contact(String mo_no)
	{
		try {
			Connection con=Login.db();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select student_contact from student");
			while(rs.next())
			{
				String dbmo_no=rs.getString("student_contact");
				if(mo_no.equals(dbmo_no))
				{
					//JOptionPane.showMessageDialog(null, "mobile number already exist..!");
					return true;
				}
			}
			return false;

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return true;

		}

	}




	//--------------------------------------------------------------------
	// function for checking the duplicate entry of email in database
	//---------------------------------------------------------------------

	static boolean check_duplicate_email(String email)
	{
		try {
			Connection con=Login.db();
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select student_mail from student");
			while(rs.next())
			{
				String dbemail=rs.getString("student_mail");
				if(email.equals(dbemail))
				{
					//JOptionPane.showMessageDialog(null, "email already exist..!");
					return true;
				}
			}
			return false;

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return true;

		}

	}



	//-----------------------------------------------------------------
	//function for checkig the dublicate entry of student barcode
	//-----------------------------------------------------------------	

	static boolean check_dublicate_sbarcode(int sbarcode)
	{
		try
		{
			Connection con = Login.db();
			Statement stmt  = con.createStatement();
			ResultSet rs = stmt.executeQuery("select student_barcode from student");
			while(rs.next())
			{
				int sb = rs.getInt("student_barcode");
				if(sb == sbarcode)
				{
					return true;	
				}
			}
			return false;

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		}
	}




	//-----------------------------------------------------------------
	//function for checkig the dublicate entry of student register number
	//-----------------------------------------------------------------	

	static boolean check_dublicate_srno(int srno)
	{
		try
		{
			Connection con = Login.db();
			Statement stmt  = con.createStatement();
			ResultSet rs = stmt.executeQuery("select student_register_number from student");
			while(rs.next())
			{
				int sb = rs.getInt("student_register_number");
				if(sb == srno)
				{
					return true;	
				}
			}
			return false;

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		}
	}




	//-----------------------------------------------------------------
	//function for checkig the dublicate entry of book barcode
	//-----------------------------------------------------------------	

	static boolean check_dublicate_book_barcode(int book_karcode)
	{
		try
		{
			Connection con = Login.db();
			Statement stmt  = con.createStatement();
			ResultSet rs = stmt.executeQuery("select b_barcode from book");
			while(rs.next())
			{
				int sb = rs.getInt("b_barcode");
				if(sb == book_karcode)
				{
					return true;	
				}
			}
			return false;

		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return false;
		}
	}
}
