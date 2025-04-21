package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class BatchProcessing {
	
	public static void main(String args[])
	{
		
		String url = "jdbc:mysql://localhost:3306/employee";
		String un = "root";
		String pwd = "*******";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded sucessfully");
			
			
			con = DriverManager.getConnection(url, un, pwd);
			System.out.println("Connection established");
			
			String query = "insert into emp (`id`,`Name`,`salary`,`designation`) values (?,?,?,?)";
			
			pstmt = con.prepareStatement(query);
			
			Scanner sc = new Scanner(System.in);
			
			System.out.println("Enter Id :");
			int id = sc.nextInt();
			System.out.println("Enter Name:");
			String Name = sc.next();
			System.out.println("Enter salary :");
			int salary = sc.nextInt();
			System.out.println("Enter designation :");
			String designation = sc.next();
			
			
			
			pstmt.setInt(1,id);
			pstmt.setString(2,Name);
			pstmt.setInt(3,salary);
			pstmt.setString(4,designation);
			
			pstmt.execute();
			System.out.println("query executed successfully");
			
			
			
			
			
			
			
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Driver not Loaded sucessfully");
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
			
		}
		try {

			pstmt.close();
			con.close();
			
			
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
			
		}
		
	}
}
