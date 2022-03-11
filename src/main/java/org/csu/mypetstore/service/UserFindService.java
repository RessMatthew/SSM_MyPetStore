package org.csu.mypetstore.service;


import org.csu.mypetstore.persistence.UserFindMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFindService {

    @Autowired
    private UserFindMapper userFindMapper;



    public void deleteByUserNameAndLoginTime(String username, String loginTime){
        userFindMapper.deleteByUserNameAndLoginTime(username, loginTime);
    }

    public void insertUserFindByUserNameAndLoginTimeAndUrl(String username, String loginTime, String url){
        userFindMapper.insertUserFindByUserNameAndLoginTimeAndUrl(username, loginTime, url);
    }
}
