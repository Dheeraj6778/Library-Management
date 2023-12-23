package com.privileged;
import com.sqlite.SQLite;
import java.sql.*;
public class Authentication {
    public boolean login(String username,String password) throws SQLException {
        //verify credentials from db and return true or false accordingly
        try{
            SQLite sqLite=new SQLite();
            Statement statement= sqLite.getStatement("UserInfo.db");
            Connection connection =sqLite.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement("select * from users");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                String uname=resultSet.getString("username");
                String pw=resultSet.getString("password");
                if(username.equals(username) && password.equals(pw)){
                    sqLite.closeConnection();
                    return true;
                }
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean register(User user) throws SQLException {
        //check if the user id is present in the db or not
        //first just push it to the db
        try{
            SQLite sqLite=new SQLite();
            Statement statement=sqLite.getStatement("UserInfo.db");

            String sql="insert into users (username,password,email,role) values (?,?,?,?)";
            PreparedStatement preparedStatement=sqLite.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4,user.getRole());
            // Execute the query
            preparedStatement.executeUpdate();
            System.out.println("data inserted successfully!");
            sqLite.closeConnection();
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
