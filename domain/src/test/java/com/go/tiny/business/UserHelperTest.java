package com.go.tiny.business;

import com.go.tiny.business.model.User;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import static com.go.tiny.business.helper.UserHelper.USER_HELPER;

@RunWith(JUnitPlatform.class)
public class UserHelperTest {

    @Test
    void shouldAddUser(){
        User user = new User("EmailId","Password");
        User responseUser= USER_HELPER.addUser(user);
    }
}
