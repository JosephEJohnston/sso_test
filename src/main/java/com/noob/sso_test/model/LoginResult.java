package com.noob.sso_test.model;

import lombok.Data;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
@Data
public class LoginResult {
    private LoginUser loginUser;

    private UserToken userToken;

    private LoginStatus loginStatus;

    private LoginTypes loginTypes;

    public LoginResult() {
    }

    public LoginResult(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }
}
