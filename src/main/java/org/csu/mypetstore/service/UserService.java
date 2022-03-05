package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.User;
import org.csu.mypetstore.persistence.UserDAO;
import org.csu.mypetstore.persistence.impl.UserDAOImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAOImpl();
    }

    public User findUserByUsername(String username){
        return userDAO.findUserByUsername(username);
    }

    public int updateUserByUsername(User user){
        return userDAO.updateUserByUsername(user);
    }

    public User signin(User user){
        return userDAO.findUserByUsernameAndPassword(user);
    }

    public int register(User user){
        return userDAO.insertUserByUsernameAndPassword(user);
    }
}
