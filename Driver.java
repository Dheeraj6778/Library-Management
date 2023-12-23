import com.privileged.Admin;
import com.privileged.Authentication;
import com.sqlite.SQLite;
import com.privileged.User;

import java.sql.*;
import java.util.Scanner;

import com.util.ScannerWrapped;

public class Driver {

    public static void createUsersTable() throws SQLException{
        SQLite sqLite=new SQLite();
        Statement statement=sqLite.getStatement("UserInfo.db");
        StringBuilder createTableSql=new StringBuilder();
        createTableSql.append("create table if not exists users (username text primary key, password text,email text, role text)");
        statement.execute(createTableSql.toString());
        sqLite.closeConnection();
    }
    public static void createBooksTable() throws SQLException{
        SQLite sqLite=new SQLite();
        Statement statement=sqLite.getStatement("Books.db");
        StringBuilder createTableSql=new StringBuilder();
        createTableSql.append("create table if not exists books (title text primary key,author text, isbn text,price INT, pages INT,available INT)");
        statement.execute(createTableSql.toString());
        sqLite.closeConnection();
    }
    public static void createBorrowTable() throws SQLException{
        SQLite sqLite=new SQLite();
        Statement statement=sqLite.getStatement("Books.db");
        StringBuilder createTableSql=new StringBuilder();
        createTableSql.append("create table if not exists borrow (borrower text,title text, author text, isbn text primary key)");
        statement.execute(createTableSql.toString());
        sqLite.closeConnection();
    }
    public static void initialisation() throws SQLException {
        //create all the databases
        createUsersTable();
        createBooksTable();
        createBorrowTable();
    }
    public static String getRole(String username) throws SQLException {
        SQLite sqLite=new SQLite();
        Statement statement= sqLite.getStatement("UserInfo.db");
        Connection connection =sqLite.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("select * from users");
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            String uname=resultSet.getString("username");

            if(uname.equals(username)){
                return resultSet.getString("role");
            }
        }
        return "$";
    }
    public static void main(String[] args) throws SQLException {
        //creates the databases (if not created)
        initialisation();
        String resp;
        Scanner sc= ScannerWrapped.getScanner();
        while (true){
            System.out.println("Enter exit to exit");
            System.out.println("******Login if you have an account or register******");
            resp=sc.nextLine();
            if(resp.equals("login")){
                String username,password;
                System.out.println("Enter username");
                username=sc.nextLine();
                System.out.println("Enter password");
                password=sc.nextLine();
                Authentication authentication=new Authentication();
                if(authentication.login(username,password)){
                    System.out.println("successfully logged in!");
                    String role=getRole(username);
                    System.out.println("role is "+role);
                    if(role.equals("user")){
                        User user=new User(username);
                        user.showUserInterface();
                    }
                    else if(role.equals("admin")){
                        Admin admin=new Admin(username);
                        admin.showAdminInterface();
                    }

                }
                else{
                    System.out.println("invalid credentials....Try again!");
                }
            }
            else if(resp.equals("exit")){
                break;
            }
            else if(resp.equals("register")){
                System.out.println("Enter details for registration");
                String username,password,role,email;
                System.out.println("Enter username");
                username=sc.nextLine();
                System.out.println("Enter password");
                password=sc.nextLine();
                System.out.println("choose 1 for user or 2 for admin");
                String val=sc.nextLine();
                if(val.equals("1"))
                    role="user";
                else if(val.equals("2"))
                    role="admin";
                else {
                    System.out.println("invalid data");
                    break;
                }
                System.out.println("Enter email");
                email=sc.nextLine();
                User user=new User(username,password,email,role);
                Authentication authentication=new Authentication();
                if(authentication.register(user)){
                    System.out.println("User registered successfully!");
                }
                else{
                    System.out.println("unable to register user");
                }
            }
        }
    }
}
