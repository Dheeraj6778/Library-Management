package com.sqlite;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {
    private Connection connection=null;
    private Statement statement=null;
    public Statement getStatement(String db) throws SQLException {
        String url = "jdbc:sqlite:database/"+db;
        connection = DriverManager.getConnection(url);
        statement=connection.createStatement();
        return statement;
    }
    public void closeConnection() throws SQLException {
        if(connection!=null)
            this.connection.close();
    }
    public Connection getConnection(){
        return connection;
    }
    public String fetchQuery(String db,String query) throws SQLException{
        StringBuilder sb=new StringBuilder();
        try{
            Statement statement=getStatement(db);
            var resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                sb.append(resultSet.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}
