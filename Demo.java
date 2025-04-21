package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {
	
	public static void main(String args[])
	{
		
		String url = "jdbc:mysql://localhost:3306/employee";
		String un = "root";
		String pwd = "#siD_987dhI";
		
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded sucessfully");
			
			
			Connection con = DriverManager.getConnection(url, un, pwd);
			System.out.println("Connection established");
			
			//loading the data into the java program 
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,0);
			
			
			String query = "select * from emp";
			
			ResultSet res = stmt.executeQuery(query);
			System.out.println("Query executed");
			
			//How to access the result in result set visually
			
			
			/*while(res.next()==true) //move the cursor for non scrollable it cannot jump from 1 to 3 and all. It only moves in forward direction
			
			{
				
			System.out.println(res.getInt("id") + " " + res.getString("Name") + " " + res.getString("salary") + " " + res.getString("designation"));
			
			}
			*/
			
			//to know about the dataType in database
			ResultSetMetaData metaData = res.getMetaData();
			
			for(int i = 1; i <= metaData.getColumnCount(); i++)
			{
			System.out.println(metaData.getColumnName(i) + " " + metaData.getColumnTypeName(i));
			
			}
			
			res.absolute(2);
			
			System.out.println(res.getInt("id") + " " + res.getString("Name") + " " + res.getString("salary") + " " + res.getString("designation"));
			
		
			
			
			
			
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Driver not Loaded sucessfully");
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
			
		}
	}
}
