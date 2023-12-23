package com.book;
import com.sqlite.SQLite;
import org.sqlite.jdbc4.JDBC4Connection;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {
    private SQLite sqLite;
    public LibrarySystem(){
        sqLite=new SQLite();
    }
    public void search(String keyword) throws SQLException{
        //starts with
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("select * from books");
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            String title=resultSet.getString("title");
            if(title.startsWith(keyword)){
                StringBuilder sb=new StringBuilder();
                sb.append("title :"+title);
                sb.append("author :"+resultSet.getString("author"));
                System.out.println(sb.toString());
            }
        }
    }
    public void displayAll() throws SQLException {
        //retrieve all the books and display 10 books
        //there can be a million books
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("select * from books");
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            StringBuilder sb=new StringBuilder();
            sb.append("title :"+resultSet.getString("title"));
            sb.append("author :"+resultSet.getString("author"));
            System.out.println(sb.toString());
        }
        sqLite.closeConnection();
    }
    public void removeBook(String title) throws SQLException{
        //remove the book from the db
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        //title text primary key,author text, isbn text,price INT, pages INT,available INT
        PreparedStatement preparedStatement=connection.prepareStatement("delete from books where title=?");
        preparedStatement.setString(1,title);
        preparedStatement.executeUpdate();
        sqLite.closeConnection();
    }
    public void addBook(Book book) throws SQLException{
        //insert the book into the db
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        //title text primary key,author text, isbn text,price INT, pages INT,available INT
        PreparedStatement preparedStatement=connection.prepareStatement("insert into books (title,author,isbn,price,pages,available) values (?,?,?,?,?,?)");
        preparedStatement.setString(1,book.getTitle());
        preparedStatement.setString(2,book.getAuthor());
        preparedStatement.setString(3, book.getIsbn());
        preparedStatement.setInt(4,book.getPrice());
        preparedStatement.setInt(5,book.getPages());
        preparedStatement.setInt(6,book.getAvailable());
        preparedStatement.executeUpdate();
        sqLite.closeConnection();
    }
    public void addBooks(List<Book> books) throws SQLException {
        for(Book book: books)
            addBook(book);
    }
    public void borrowBook(String title,String borrower) throws SQLException{
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        //title text primary key,author text, isbn text,price INT, pages INT,available INT
        PreparedStatement preparedStatement=connection.prepareStatement("select * from books where title=?");
        preparedStatement.setString(1,title);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            int val=resultSet.getInt("available");
            if(val-1>=0){
                val--;
                PreparedStatement preparedStatement1=connection.prepareStatement("update books set available=? where title=?");
                preparedStatement1.setInt(1,val);
                preparedStatement1.setString(2,title);
                preparedStatement1.executeUpdate();

                //also push it in the borrow table
                //(borrower text,title text, author text, isbn text primary key)
                PreparedStatement preparedStatement2=connection.prepareStatement("insert into borrow (borrower,title,author,isbn) values (?,?,?,?)");
                preparedStatement2.setString(1,borrower);
                preparedStatement2.setString(2,title);
                preparedStatement2.setString(3,resultSet.getString("author"));
                preparedStatement2.setString(4,resultSet.getString("isbn"));
                preparedStatement2.executeUpdate();
                break;
            }
            else {
                System.out.println("not available");
            }
        }
        sqLite.closeConnection();
    }
    public void removeEntryFromBorrow(String isbn) throws SQLException {
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("delete from borrow where isbn=?");
        preparedStatement.setString(1,isbn);
        preparedStatement.executeUpdate();
        sqLite.closeConnection();
    }
    public void incrementAvailability(String isbn) throws SQLException{
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("select availabile from books where isbn=?");
        preparedStatement.setString(1,isbn);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            int available=resultSet.getInt("available");
            available++;
            PreparedStatement preparedStatement1=connection.prepareStatement("update books set available=? where isbn=?");
            preparedStatement1.setInt(1,available);
            preparedStatement1.setString(2,isbn);
            preparedStatement1.executeUpdate();
        }
        sqLite.closeConnection();
    }
    public void returnBook(String username,String title) throws SQLException {
        //delete that single entry from the table
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("select * from borrow where borrower=? and title=?");
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,title);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            String isbn=resultSet.getString("isbn");
//            removeEntryFromBorrow(isbn);
            PreparedStatement preparedStatement1=connection.prepareStatement("delete from borrow where isbn=?");
            preparedStatement1.setString(1,isbn);
            preparedStatement1.executeUpdate();
            //increment the availability in books
            //incrementAvailability(isbn);
            PreparedStatement preparedStatement2=connection.prepareStatement("select available from books where isbn=?");
            preparedStatement2.setString(1,isbn);
            ResultSet resultSet2=preparedStatement.executeQuery();
            while (resultSet2.next()){
                int available=resultSet.getInt("available");
                available++;
                PreparedStatement preparedStatement3=connection.prepareStatement("update books set available=? where isbn=?");
                preparedStatement3.setInt(1,available);
                preparedStatement3.setString(2,isbn);
                preparedStatement3.executeUpdate();
            }
        }
        sqLite.closeConnection();
    }

    public void displayBorrowedBooks(String username) throws SQLException {
        Statement statement=sqLite.getStatement("Books.db");
        Connection connection=sqLite.getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("select * from borrow where borrower=?");
        preparedStatement.setString(1,username);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()){
            StringBuilder sb=new StringBuilder();
            sb.append(resultSet.getString("title")+" ");
            sb.append(resultSet.getString("author")+" ");
            sb.append(resultSet.getString("isbn")+" ");
            System.out.println(sb.toString());
        }
        sqLite.closeConnection();
    }
}
