package assignment1;

import java.util.*;
import java.sql.*;

public class Account {
	String fname;
	String lname;
	String phone;
	String pass;
	int isLoggedIn;
	Connection con;
	Scanner sc;
	Account() throws ClassNotFoundException{
		isLoggedIn = 0;
		this.sc = new Scanner(System.in);
		try {
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee","root","Pass@123");
		} catch (SQLException e) {}
	}
	
	public void register() throws ClassNotFoundException {
		System.out.println("Register");
		System.out.println("Enter first name:");
		fname = sc.next();
		System.out.println("Enter last name:");
		lname = sc.next();
		System.out.println("Enter phone number:");
		phone = sc.next();
		System.out.println("Enter password:");
		pass = sc.next();
		try {
			
			PreparedStatement ps = this.con.prepareStatement("insert into newbank values(?, ?, ?, ?, 100000)");
			ps.setString(1, phone);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setString(4, pass);
			int result = ps.executeUpdate();
			System.out.println("User added");
//			
		} catch (SQLException e) {}
	}
	
	public void login() {
		System.out.println("Login");
		System.out.println("Enter first name or phone:");
		String username = sc.next();
		System.out.println("Enter password:");
		String tmppass = sc.next();
		
		try {
			PreparedStatement ps = this.con.prepareStatement("select * from newbank where (phone=? OR fname=?) AND pass=?;");
			ps.setString(1, username);
			ps.setString(2, username);
			ps.setString(3, tmppass);
			ResultSet R = ps.executeQuery();
			while(R.next()) {
				this.isLoggedIn = 1;
			}
			if(this.isLoggedIn == 1) {
				System.out.println("Logged in!");
				System.out.println("Enter choice");
				System.out.println("1. transfer");
				System.out.println("2. view balance");
				System.out.println("3. exit");

				int ch = sc.nextInt();
				if(ch == 1) {
					this.transfer();
				}
				else if(ch == 2) {
					this.display();
				}
				else if(ch == 3) {return;}
				else {
					System.out.println("Wrong choice");
				}
			}
			else System.out.println("Wrong credentials");
		} catch (SQLException e) {}
	}
	

	public void f_display(String phone) {
		try {
			PreparedStatement ps1 = this.con.prepareStatement("select * from newbank where phone=?;");
			ps1.setString(1, phone);
			ResultSet R = ps1.executeQuery();
			while(R.next()) {
				System.out.println("Name : " + R.getString("fname") + " " + R.getString("lname"));
				System.out.println("Funds available : " + R.getString("funds"));
			}
			System.out.println();
		} catch (SQLException e) {}
	}
	
	
	public void transfer() {
		System.out.println("Transfer");
		System.out.println("Enter first phone number:");
		String p1 = sc.next();
		System.out.println("Enter second phone number:");
		String p2 = sc.next();
		System.out.println("Enter amount to transfer:");
		String amt = sc.next();
		try {
			PreparedStatement ps1 = this.con.prepareStatement("update newbank set funds = funds - ? where phone = ?;");
			ps1.setString(1, amt);
			ps1.setString(2, p1);
			PreparedStatement ps2 = this.con.prepareStatement("update newbank set funds = funds + ? where phone = ?;");
			ps2.setString(1, amt);
			ps2.setString(2, p2);
			int i1 = ps2.executeUpdate();
			int i2 = ps1.executeUpdate();
			this.f_display(p1);
			this.f_display(p2);
		} catch (SQLException e) {}
		
	}
	
	public void display() {
		System.out.println("Display");
		System.out.println("Enter phone number:");
		String p1 = sc.next();
		this.f_display(p1);
	}
	
	public void finalize() {
		sc.close();
	}
}
