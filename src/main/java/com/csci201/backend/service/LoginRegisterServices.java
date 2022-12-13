package com.csci201.backend.service;

import com.csci201.backend.dao.LoginRegisterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class LoginRegisterServices {

    LoginRegisterDao dao = new LoginRegisterDao();

    public int LoginService(String username, String password){
        try {
            return dao.LoginDao(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int RegisterService(String username, String password, String email){
        try {
            return dao.RegisterDao(username, password, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
