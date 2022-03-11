package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
     User findUserByUsername(String username);
     boolean updateUserByUsername(User user);
     User findUserByUsernameAndPassword(User user);
     boolean insertUserByUsernameAndPassword(User user);
}
