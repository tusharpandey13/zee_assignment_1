/**
 * 
 */
package assignment1;
import java.util.*;
import java.sql.*;
import assignment1.Account;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Menu");
		System.out.println("1. login");
		System.out.println("2. register");
		System.out.println("3. exit");
		Scanner sc = new Scanner(System.in);
		int ch = sc.nextInt();
		Account ac = new Account();
		if(ch == 1) {
			ac.login();
		}
		else if(ch == 2) {
			ac.register();
		}
		else if(ch == 3) {return;}
		else {
			System.out.println("Wrong choice");
		}
	}

}
