package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.User;
import org.csu.mypetstore.persistence.UserMapper;
import org.csu.mypetstore.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    //MD5 加密所用的盐
    private static final String salt="1a2b3c4d";

    public User findUserByUsername(String username){
        return userMapper.findUserByUsername(username);
    }

    public boolean updateUserByUsername(User user){
        String inputPassword = user.getPassword();
        String MD5Password = MD5Util.inputPassToDBPass(inputPassword,salt);
        System.out.println("*******************************************");
        System.out.println("输入密码： "+inputPassword+"  MD5加密后： "+MD5Password);
        System.out.println("*******************************************");

        user.setPassword(MD5Password);
        return userMapper.updateUserByUsername(user);
    }

    /**
     * @Description //TODO 登录获取输入密码，使用MD%加密后与数据库中存储密码匹配查询
     * @Date 8:28 下午 2022/3/14
     * @Param [user]
     * @return org.csu.mypetstore.domain.User
     **/
    public User signin(User user){
        String inputPassword = user.getPassword();
        String MD5Password = MD5Util.inputPassToDBPass(inputPassword,salt);
        System.out.println("*******************************************");
        System.out.println("输入密码： "+inputPassword+"  MD5加密后： "+MD5Password);
        System.out.println("*******************************************");

        user.setPassword(MD5Password);


        return userMapper.findUserByUsernameAndPassword(user);
    }

    /**
     * @Description //TODO 注册获取输入密码，使用MD%加密后存入数据库
     * @Date 8:28 下午 2022/3/14
     * @Param [user]
     * @return org.csu.mypetstore.domain.User
     **/
    public boolean register(User user){
        String inputPassword = user.getPassword();
        String MD5Password = MD5Util.inputPassToDBPass(inputPassword,salt);
        System.out.println("*******************************************");
        System.out.println("输入密码： "+inputPassword+"  MD5加密后： "+MD5Password);
        System.out.println("*******************************************");

        user.setPassword(MD5Password);
        return userMapper.insertUserByUsernameAndPassword(user);
    }
}
