package com.noob.sso_test.model;

import lombok.Data;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
@Data
public class LoginUser {
    private String account;

    private String password;

    private String loginTime;

    public LoginUser(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public LoginUser() {
    }
}
