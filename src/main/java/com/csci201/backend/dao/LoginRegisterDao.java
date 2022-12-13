package com.csci201.backend.dao;

import com.csci201.backend.model.JobModel;
import com.csci201.backend.model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginRegisterDao {

    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    public int LoginDao(String username, String userpassword) throws SQLException {
        // Set up the connection base
        String endpoint = "localhost";
        String port = "3306";
        String user = "root";
        
        // replace with your own password
        String password = "wdmzsljh2015A-2024";

        String connectionAddress = "jdbc:mysql://" + endpoint + ":" + port + "/joblistings?user=" + user + "&password=" + password;

        // Set up the Connection with the Database
        conn = DriverManager.getConnection(connectionAddress);
        st = conn.createStatement();
        // String statement = String.format("Select * from joblist where companyName = \'%s\'", name);
        
        String statement = String.format("Select * from userInfo where userName = \'%s\' and Password = \'%s\'", username, userpassword);
        rs = st.executeQuery(statement);
        if (rs.first()){
            return rs.getInt("userId");
        }
        else{
            return -1;
        }
    }

    public int RegisterDao(String username, String userpassword, String email) throws SQLException {

        if (CheckExsist(username)){
            return -1;
        }
        else{
            String endpoint = "localhost";
            String port = "3306";
            String user = "root";

            // replace with your own password
            String password = "wdmzsljh2015A-2024";


            String connectionAddress = "jdbc:mysql://" + endpoint + ":" + port + "/joblistings?user=" + user + "&password=" + password;

            conn = DriverManager.getConnection(connectionAddress);
            st = conn.createStatement();
            int i = st.executeUpdate("insert into userInfo ( userName, Password, emailAddress) values ( '" + username + "', '" + userpassword + "', '" + email + "')");
        }
        return this.LoginDao(username, userpassword);
    }

    public boolean CheckExsist(String username) throws SQLException {
        List<UserModel> list = new ArrayList<>();
        String endpoint = "localhost";
        String port = "3306";
        String user = "root";

        // replace with your own password
        String password = "wdmzsljh2015A-2024";

        String connectionAddress = "jdbc:mysql://" + endpoint + ":" + port + "/joblistings?user=" + user + "&password=" + password;
    
        conn = DriverManager.getConnection(connectionAddress);
        st = conn.createStatement();
        rs = st.executeQuery("select * from userInfo where userName like '%" + username + "%'");
        if (rs.first()){
            return true;
        }
        return false;
    }

}
