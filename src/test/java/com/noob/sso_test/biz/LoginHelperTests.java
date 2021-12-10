package com.noob.sso_test.biz;

import com.noob.sso_test.model.LoginResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
@SpringBootTest
public class LoginHelperTests {

    @Autowired
    private LoginHelper loginHelper;

    @Test
    public void testLogin() {
        String account = "test";
        String password = "password";
        String callbackUrl = "http://localhost:8080/login";

        LoginResult loginResult = loginHelper.login(account, password, callbackUrl);
        System.out.println("Login result: " + loginResult);
    }
}
