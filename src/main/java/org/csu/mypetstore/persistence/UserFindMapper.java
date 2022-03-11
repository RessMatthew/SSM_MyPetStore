package org.csu.mypetstore.persistence;

import org.springframework.stereotype.Repository;

@Repository
public interface UserFindMapper {
    public void deleteByUserNameAndLoginTime(String username,String loginTime);
    public void insertUserFindByUserNameAndLoginTimeAndUrl(String username, String loginTime, String url);
}
