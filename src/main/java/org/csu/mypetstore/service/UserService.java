package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.User;
import org.csu.mypetstore.persistence.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public User findUserByUsername(String username){
        return userMapper.findUserByUsername(username);
    }

    public boolean updateUserByUsername(User user){
        return userMapper.updateUserByUsername(user);
    }

    public User signin(User user){
        return userMapper.findUserByUsernameAndPassword(user);
    }

    public boolean register(User user){
        return userMapper.insertUserByUsernameAndPassword(user);
    }
}
