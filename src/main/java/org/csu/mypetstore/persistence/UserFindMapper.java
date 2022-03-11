package org.csu.mypetstore.persistence;

import org.springframework.stereotype.Repository;

@Repository
public interface UserFindMapper {
    void deleteByUserNameAndLoginTime(String username,String loginTime);
    void insertUserFindByUserNameAndLoginTimeAndUrl(String username, String loginTime, String url);
}
