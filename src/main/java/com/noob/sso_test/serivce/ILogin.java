package com.noob.sso_test.serivce;

import com.noob.sso_test.model.LoginResult;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
public interface ILogin {
    LoginResult login(String account, String password, String callbackUrl);
}
