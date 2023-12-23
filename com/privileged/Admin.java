package com.privileged;
import com.book.Book;
import com.book.LibrarySystem;
import com.util.ScannerWrapped;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin extends User {
    public Admin(String username,String password,String email,String role){
        super(username,password,email,role);
    }
    public Admin(String username){
        super(username);
    }
    public void showAdminInterface() throws SQLException {
        Scanner sc=ScannerWrapped.getScanner();
        LibrarySystem librarySystem=new LibrarySystem();
        while (true) {
            int option;
            System.out.println("Enter 1 to add a book");
            System.out.println("Enter 2 to search for a book");
            System.out.println("Enter 3 to remove a book");
            System.out.println("Enter 4 to display all books");
            System.out.println("Enter 5 to logout");
            option = sc.nextInt();
            if (option == 1) {
                sc.nextLine();
                System.out.println("Enter title of book");
                String title = sc.nextLine();
                System.out.println("Enter isbn");
                String isbn = sc.nextLine();
                System.out.println("enter author");
                String author = sc.nextLine();
                System.out.println("enter price");
                int price = sc.nextInt();
                System.out.println("enter pages");
                int pages = sc.nextInt();
                System.out.println("enter available books");
                int available = sc.nextInt();
                Book book = new Book(title,author, isbn, price, pages, available);
                librarySystem.addBook(book);
            } else if (option == 2) {
                String query;
                System.out.println("Enter the keyword to search");
                sc.nextLine();
                query = sc.nextLine();
                librarySystem.search(query);
            } else if (option == 3) {
                String title;
                System.out.println("enter the title to remove");
                sc.nextLine();
                title = sc.nextLine();
                librarySystem.removeBook(title);
            } else if (option == 4) {
                librarySystem.displayAll();
            } else if (option == 5) {
                return;
            }
        }

    }
}
