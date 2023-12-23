package com.privileged;

import com.book.LibrarySystem;
import com.util.ScannerWrapped;

import java.sql.SQLException;
import java.util.Scanner;

public class User {

    private String username;
    private String password;
    private String email;
    private String role;
    public User(String username,String password,String email,String role){

        this.username=username;
        this.password=password;
        this.email=email;
        this.role=role;
    }
    public User(String username){
        this.username=username;
    }
    public String getUsername() {
        return username;
    }
    public String getRole(){
        return role;
    }
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("username: "+username);
        sb.append("role: "+role);
        sb.append("email: "+email);
        return sb.toString();
    }
    public void showUserInterface() throws SQLException {
        Scanner sc= ScannerWrapped.getScanner();
        LibrarySystem librarySystem=new LibrarySystem();
        while (true){
            System.out.println("Enter 1 to borrow a book");
            System.out.println("Enter 2 to search a book");
            System.out.println("Enter 3 to display all books");
            System.out.println("Enter 4 to logout");
            System.out.println("Enter 5 to return a book");
            System.out.println("Enter 6 display the borrowed books");
            int option=sc.nextInt();
            if(option==1){
                sc.nextLine();
                System.out.println("Enter title to borrow");
                String title=sc.nextLine();
                librarySystem.borrowBook(title,getUsername());
            }
            else if(option==2){
                sc.nextLine();
                System.out.println("Enter a title to search");
                String query=sc.nextLine();
                librarySystem.search(query);
            }
            else if(option==3){
                System.out.println("The books available are");
                librarySystem.displayAll();
            }
            else if(option==4){
                break;
            }
            else if(option==5){
                sc.nextLine();
                System.out.println("Enter the book title you want to return");
                String book=sc.nextLine();
                librarySystem.returnBook(username,book);
            }
            else if(option==6){
                librarySystem.displayBorrowedBooks(username);
            }
        }
    }

}
