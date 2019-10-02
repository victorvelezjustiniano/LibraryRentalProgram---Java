import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.*;

public class libMain
{
	public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException
	{
		if (args.length == 0)
		{

		}
		SimpleDataSource.init(args[0]);
		try (Connection conn = SimpleDataSource.getConnection(); Statement stat = conn.createStatement())
		{
			try
			{
				stat.execute("DROP TABLE book");
			}
			catch (SQLException e)
			{

			}
			stat.execute("CREATE TABLE book (Name VARCHAR(50)," + "Author VARCHAR(50), " + "BookId VARCHAR(4))");

			File inputFile = new File("booksArray.txt");
			Scanner books = new Scanner(inputFile);
			while (books.hasNextLine())
			{
				String name = books.next();
				String author = books.next();
				String id = books.next();

				stat.execute("INSERT INTO book VALUES ('" + name + "', '" + author + "', '" + id + "')");
			}
		}
		// get exception if table doesn't exist yet
		{
			SimpleDataSource.init(args[0]);
			try (Connection conn = SimpleDataSource.getConnection(); Statement stat = conn.createStatement())
			{
				try
				{
					stat.execute("DROP TABLE rentedbook");
				}
				catch (SQLException e)
				{

				}
				stat.execute("CREATE TABLE rentedbook (Name VARCHAR(50)," + "Author VARCHAR(50), " + "BookId VARCHAR(4))");
			}
			// get exception if table doesn't exist yet
			{
				System.out.println("If you are a student enter yes, if not enter no: ");

				Scanner user = new Scanner(System.in);
				String student = user.next();
				String faculty = user.nextLine();
				{
					Connection conn = SimpleDataSource.getConnection();
					Statement stat = conn.createStatement();
					if (student.equals("yes"))
					{// student block
						Scanner in2 = new Scanner(System.in);
						boolean continueProgram = true;
						while (continueProgram)
						{
							System.out.println("Select from the following options");
							System.out.println("(Q) Quit");
							System.out.println("(R) Rent book");
							System.out.println("(F) Find late fee");
							System.out.println("(P) Print list of books");
							String select = in2.next();
							if (select.equals("Q"))
							{
								continueProgram = false;
							}
							else if (select.equals("F"))
							{
								Student fee = new Student();
								int daysOver = fee.daysLeft();
								int Sfee = 2 * daysOver;
								System.out.println("You are " + daysOver + " days over the return date. Your fee is $" + Sfee);
							}	
							else if (select.equals("R"))
							{
								try (ResultSet result = stat.executeQuery("SELECT * FROM book, rentedbook"))
								{
									Scanner rent = new Scanner(System.in);
									System.out.println("Please enter a book you would like to rent(Name Author id)");
									String name = rent.next();
									String author = rent.next();
									String id = rent.next();
									stat.execute("INSERT INTO rentedbook VALUES ('" + name + "', '" + author + "', '" + id + "')");
									stat.execute("DELETE FROM book WHERE name = ('" + name + "')");
								}
								try (ResultSet result = stat.executeQuery("SELECT * FROM rentedbook"))
								{
									System.out.println("Rented books:");
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									System.out.println("Books available to rent:");
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
							}
							else if (select.equals("P"))
							{
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									System.out.println("Books available to rent:");
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
							}
						}
					}
					else
					{// faculty block
						System.out.println("You are apart of the faculty");
						Scanner in2 = new Scanner(System.in);
						boolean continueProgram = true;
						while (continueProgram)
						{
							System.out.println("Select from the following options");
							System.out.println("(Q) Quit");
							System.out.println("(A) Add a book");
							System.out.println("(D) Delete a book");
							System.out.println("(R) Rent book");
							System.out.println("(F) Find late fee");
							System.out.println("(P) Print list of books");
							String select = in2.next();
							if (select.equals("Q"))
							{
								continueProgram = false;
							}
							else if (select.equals("F"))
							{
								Faculty fee = new Faculty();
								int daysOver = fee.daysLeft();
								int Ffee = 1 * daysOver;
								System.out.println("You are " + daysOver + " days over the return date. Your fee is $" + Ffee);
							}
							else if (select.equals("R"))
							{
								try (ResultSet result = stat.executeQuery("SELECT * FROM book, rentedbook"))
								{
									Scanner rent = new Scanner(System.in);
									System.out.println("Please enter a book you would like to rent(Name Author id)");
									String name = rent.next();
									String author = rent.next();
									String id = rent.next();
									stat.execute("INSERT INTO rentedbook VALUES ('" + name + "', '" + author + "', '" + id + "')");
									stat.execute("DELETE FROM book WHERE name = ('" + name + "')");
								}
								try (ResultSet result = stat.executeQuery("SELECT * FROM rentedbook"))
								{
									System.out.println("Rented books:");
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									System.out.println("Books available to rent:");
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
							}
							else if (select.equals("A"))
							{
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									Scanner add = new Scanner(System.in);
									System.out.println("Please enter new book(Name Author id)");
									String name = add.next();
									String author = add.next();
									String id = add.next();
									stat.execute("INSERT INTO book VALUES ('" + name + "', '" + author + "', '" + id + "')");
								}
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
							}
							else if (select.equals("D"))
							{
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									Scanner delete = new Scanner(System.in);
									System.out.println("Please enter book title to delete");
									String name = delete.next();
									stat.execute("DELETE FROM book WHERE name = ('" + name + "')");
								}
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
							}
							else if (select.equals("P"))
							{
								try (ResultSet result = stat.executeQuery("SELECT * FROM book"))
								{
									System.out.println("Books available to rent:");
									while (result.next())
									{
										System.out.print(result.getString("Name") + "\t");
										System.out.print(result.getString("Author") + "\t");
										System.out.print(result.getDouble("BookID") + "\t");
										System.out.println();
									}
									result.close();
								}
							}
						}
					}
				}
			}
		}
	}
}
