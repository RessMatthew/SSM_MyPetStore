package org.csu.mypetstore.persistence;

public interface UserFindDAO {
    public void deleteByUserNameAndLoginTime(String username,String loginTime);
    public void insertUserFindByUserNameAndLoginTimeAndUrl(String username, String loginTime, String url);
}
