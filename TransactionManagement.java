package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.Scanner;

import com.mysql.cj.jdbc.MysqlSavepoint;

public class TransactionManagement {

	public static void main(String args[])
	{
		
		String url = "jdbc:mysql://localhost:3306/Account";
		String un = "root";
		String pwd = "#siD_987dhI";
		
	
		try 
		{
		
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Driver Loaded sucessfully");
				
				
				Connection con = DriverManager.getConnection(url, un, pwd);
				System.out.println("Connection established");
				
				
				Scanner sc = new Scanner(System.in);
				
				System.out.println("Welcome to tap employee");
				
				System.out.println("Enter acc_num");
				int acc_num = sc.nextInt();
				System.out.println("Enter the pin");
				int pin = sc.nextInt();
				
				
				PreparedStatement pstmt = con.prepareStatement("select * from acc where acc_num = ? and pin = ?");
				pstmt.setInt(1,acc_num);
				pstmt.setInt(2, pin);
				
				ResultSet res = pstmt.executeQuery();
				res.next();
				String name = res.getString(2);
				int balance = res.getInt(5);
				
				System.out.println("Welcome " + name);
				System.out.println("Your salary is "+ balance);
				
				
				//Transfer module 
				System.out.println("<-----Transfer details------>");
				System.out.println("Enter the beneficiary account number : ");
				int bacc_num = sc.nextInt();
				
				System.out.println("Enter the transfer amount :");
				int t_amt = sc.nextInt();
				
				
				con.setAutoCommit(false);
				Savepoint s = con.setSavepoint();
				
				PreparedStatement pstmt1 = con.prepareStatement("update acc set balance = balance - ? where acc_num = ?");
				pstmt1.setInt(1, t_amt);
				pstmt1.setInt(2, acc_num);
				pstmt1.executeUpdate();
				
				System.out.println("Incoming credit request :");
				System.out.println(name + "Account number" + acc_num + "wants to transfer" + t_amt);
				
				System.out.println("Press Y to receive");
				System.out.println("Press N to reject");
				
				String choice = sc.next();
				
				if(choice.equals("Y"))
				{
					
					PreparedStatement pstmt2 = con.prepareStatement("update acc set balance = balance + ? where acc_num = ?");
					pstmt2.setInt(1, t_amt);
					pstmt2.setInt(2, acc_num);
					pstmt2.executeUpdate();
					
					PreparedStatement pstmt3 = con.prepareStatement("select * from acc where acc_num = ?");
					pstmt3.setInt(1, bacc_num);
					ResultSet  res2 = pstmt3.executeQuery();
					res2.next();
					
					System.out.println("updated balance is :" + res2.getInt(4));
				}
				else {
					
					
					con.rollback(s);
					PreparedStatement pstmt4 = con.prepareStatement("select * from acc where acc_num = ?");
					pstmt4.setInt(1, bacc_num);
					ResultSet  res2 = pstmt4.executeQuery();
					res2.next();
					
					System.out.println("updated balance is :" + res2.getInt(4));
					
				}
				
				con.commit();
				
				
		}
		
		catch(ClassNotFoundException e) 
		{
			
			System.out.println("Connection not established");
					
			
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
			
		}
	}

}
