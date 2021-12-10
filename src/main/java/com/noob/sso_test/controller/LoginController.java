package com.noob.sso_test.controller;

import com.noob.sso_test.model.LoginResult;
import com.noob.sso_test.model.LoginStatus;
import com.noob.sso_test.model.LoginUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
@RestController
public class LoginController {

    @PostMapping("login")
    public LoginResult login(@RequestBody LoginUser loginUser) {
        // 直接模拟返回登录成功
        LoginResult loginResult = new LoginResult(LoginStatus.SUCCESS);
        loginResult.setLoginUser(loginUser);

        return loginResult;
    }
}
