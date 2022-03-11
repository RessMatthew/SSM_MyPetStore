package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public User findUserByUsername(String username);
    public boolean updateUserByUsername(User user);
    public User findUserByUsernameAndPassword(User user);
    public boolean insertUserByUsernameAndPassword(User user);
}
