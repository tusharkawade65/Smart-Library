


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

//import java_project1.Java_project.login.Login;

public class dbconnections
{

	static int add_book_panel(int book_barcode,String book_title,String book_author,String book_publication,String book_edition,int book_edition_year,float book_cost,int no_of_copies,int rack_no,int row_no)
	{
		no_of_copies=1;
		int title_pk=0,author_pk=0,publication_pk=0,edition_pk=0,position_pk=0;
		try
		{
			Connection con=Login.db();
			Statement stmt=con.createStatement();
			int result1=stmt.executeUpdate("insert into book_title(book_title)"+"values('"+book_title+"')");
			if(result1==1)
			{
				ResultSet rs1=stmt.executeQuery("select * from book_title");
				while(rs1.next())
				{
					title_pk=rs1.getInt("book_title_id");
				}
				//JOptionPane.showMessageDialog(null, "added title");

			}
			else
			{

				JOptionPane.showMessageDialog(null, "something went wrong with title");

			}
			int result2=stmt.executeUpdate("insert into book_author(b_author_name)"+"values('"+book_author+"')");
			if(result2==1)
			{
				ResultSet rs2=stmt.executeQuery("select * from book_author");
				while(rs2.next())
				{
					author_pk=rs2.getInt("b_author_id");
				}
				//	JOptionPane.showMessageDialog(null, "author added successfully");

			}



			int result3=stmt.executeUpdate("insert into book_publication(b_publication_name)"+"values('"+book_publication+"')");
			if(result3==1)
			{
				ResultSet rs3=stmt.executeQuery("select * from book_publication");
				while(rs3.next())
				{
					publication_pk=rs3.getInt("b_publication_id");
				}
				//JOptionPane.showMessageDialog(null, "publication added successfully");

			}


			int result4=stmt.executeUpdate("insert into book_edition(b_edition,b_edition_year)"+"values('"+book_edition+"','"+book_edition_year+"')");
			if(result4==1)
			{

				ResultSet rs4=stmt.executeQuery("select * from book_edition");
				while(rs4.next())
				{
					edition_pk=rs4.getInt("b_edition_id");
				}
				//	JOptionPane.showMessageDialog(null, "edition added successfully");

			}



			int result5=stmt.executeUpdate("insert into book_position(rack_no,row_no)"+"values('"+rack_no+"','"+row_no+"')");
			if(result5==1)
			{
				ResultSet rs5=stmt.executeQuery("select * from book_position");
				while(rs5.next())
				{
					position_pk=rs5.getInt("b_position_id");
				}
				//JOptionPane.showMessageDialog(null, "position added successfully");

			}
			
			
			//-------------------------------------------------------------------------------------------
									//if duplicate book found then no of copies ++
			//-------------------------------------------------------------------------------------------
			
		/*	int copyflag=0;
			
			
			
			ResultSet RS = stmt.executeQuery("select b_barcode,book_title,b_author_name,b_publication_name,b_edition,b_edition_year,no_of_copies,book_price from book b,book_title bt,book_author ba,book_publication bp,book_edition be where bt.book_title_id=b.book_title_id and ba.b_author_id=b.b_author_id and bp.b_publication_id=b.b_publication_id and be.b_edition_id=b.b_edition_id");
			int bar=0;
			
			while(RS.next())
			{
				if(book_title.equals(RS.getString("book_title"))&& book_author.equals(RS.getString("b_author_name")) && book_publication.equals(RS.getString("b_publication_name")) && book_edition.equals(RS.getString("b_edition")) && book_edition_year == Integer.parseInt(RS.getString("b_edition_year")) && book_cost == RS.getFloat("book_price"))
				{
					bar=RS.getInt("b_barcode");
					no_of_copies=RS.getInt("no_of_copies");
					no_of_copies++;
					copyflag++;
					break;
					//int bar=RS.getInt("b_barcode");
				}
			}
			if(copyflag==1)
			{
			//	stmt.executeUpdate("update book set no_of_copies='"+no_of_copies+"' where book_title='"+book_title+"'and book_author_name='"+book_author+"' and b_publication_name='"+book_publication+"'and b_edition='"+book_edition+"' and b_edition_year='"+book_edition_year+"' and book_price='"+book_cost+"'");

			}
			
			*/
				no_of_copies=1;
			
			//-------------------------------------------------------------------------------------------
			int result6=stmt.executeUpdate("insert into book(b_barcode,book_title_id,b_author_id,b_publication_id,b_edition_id,b_position_id,book_price,no_of_copies)"+"values('"+book_barcode+"','"+title_pk+"','"+author_pk+"','"+publication_pk+"','"+edition_pk+"','"+position_pk+"','"+book_cost+"','"+no_of_copies+"')");
			if(result6==1)
			{
				return 1;
				//JOptionPane.showMessageDialog(null, "Book Added Successfully");

			}
			else
			{

				//JOptionPane.showMessageDialog(null, "something went wrong");
				return 0;

			}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return 0;
		}
	}













	static int dbStudentConnections(int student_barcode,int student_register_no,String student_name,String student_contact,String student_class,String student_mail,String student_department,String student_address)
	{
		int sdi = 0,sci = 0;
		try
		{
			Connection con = Login.db();
			Statement stmt = con.createStatement();
			ResultSet rs ;

			int result1 = stmt.executeUpdate("insert into student_department(department_name)"+"values('"+student_department+"')");
			if(result1 == 1)
			{
				rs = stmt.executeQuery("select * from student_department");
				while(rs.next())
				{
					sdi = rs.getInt("department_id");
				}
			}
			int result2 = stmt.executeUpdate("insert into class(class_name,department_id)"+"values('"+student_class+"','"+sdi+"')");
			if(result2 == 1)
			{
				rs = stmt.executeQuery("select * from class");
				while(rs.next())
				{
					sci = rs.getInt("class_id");
				}
			}
			try
			{
				int result3 = stmt.executeUpdate("insert into student(student_barcode,student_register_number,student_name,student_address,student_contact,student_mail,class_id)"+"values('"+student_barcode+"','"+student_register_no+"','"+student_name+"','"+student_address+"','"+student_contact+"','"+student_mail+"','"+sci+"')");
				if(result3 == 1)
				{
					JOptionPane.showMessageDialog(null, "Student Added SuccessFully");


				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(null, e);
				return 0;
			}
			return 1;
		}
		catch(Exception e)
		{
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "Something Went Wrong in Student Database");
			return 0;
		}
	}








	//----------------------------------------------------------------
	//book issue database connection
	//----------------------------------------------------------------




	static int book_issue_dbconnection(int book_barcode,int student_barcode,String issue_date)
	{
		try 
		{
			Connection con = Login.db();
			Statement stmt = con.createStatement();
			int result=stmt.executeUpdate("insert into book_issue(b_barcode,student_barcode,issue_date)"+"values('"+book_barcode+"','"+student_barcode+"','"+issue_date+"')");
			return result;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "SomeThing Went Wrog");
			return 0;
		}
	}



	//----------------------------------------------------------------------
	//book return connection
	//----------------------------------------------------------------------



	static int book_return_dbconnection(int book_barcode,int student_barcode,String issue_date)
	{
		try 
		{
			Connection con = Login.db();
			Statement stmt = con.createStatement();
			int result=stmt.executeUpdate("insert into book_return(b_barcode,student_barcode,return_date)"+"values('"+book_barcode+"','"+student_barcode+"','"+issue_date+"')");
			return result;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, "SomeThing Went Wrog");
			return 0;
		}
	}









}