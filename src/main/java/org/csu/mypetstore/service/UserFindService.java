package org.csu.mypetstore.service;

import org.csu.mypetstore.persistence.UserFindDAO;
import org.csu.mypetstore.persistence.impl.UserFindDAOImpl;

public class UserFindService {
    private UserFindDAO userFindDAO;

    public UserFindService(){
        userFindDAO = new UserFindDAOImpl();
    }

    public void deleteByUserNameAndLoginTime(String username, String loginTime){
        userFindDAO.deleteByUserNameAndLoginTime(username, loginTime);
    }

    public void insertUserFindByUserNameAndLoginTimeAndUrl(String username, String loginTime, String url){
        userFindDAO.insertUserFindByUserNameAndLoginTimeAndUrl(username, loginTime, url);
    }
}
