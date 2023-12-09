package com.app.lms.security;

import com.app.lms.features.login.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public User findUserByEmail(String email){
        User user = new User(email, "123456","FirstName", "LastName");
        return user;
    }
}
