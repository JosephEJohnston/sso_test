package com.noob.sso_test.biz;

import com.alibaba.fastjson.JSON;
import com.noob.sso_test.model.LoginResult;
import com.noob.sso_test.model.LoginStatus;
import com.noob.sso_test.model.LoginUser;
import com.noob.sso_test.model.UserToken;
import com.noob.sso_test.serivce.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
@Service
public class LoginHelper {

    private static final Logger LOG = LoggerFactory.getLogger(LoginHelper.class);

    private final String LOGIN_USER_KEY = "login:user:";

    private final String LOGIN_TOKEN_KEY = "login:token:";

    private final String LOGIN_FAIL_COUNT_KEY = "login:fail:count";

    private final long MAX_FAIL_COUNT = 5;

    @Autowired
    private LoginService loginService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 登录
    public LoginResult login(String account, String password, String callbackUrl) {
        // 判断账户是否多次登录失败被锁定
        String value = stringRedisTemplate.opsForValue().get(LOGIN_FAIL_COUNT_KEY + account);

        if (StringUtils.isNotBlank(value)) {
            long loginFailCount = Long.parseLong(value);

            if (loginFailCount >= MAX_FAIL_COUNT) {
                return new LoginResult(LoginStatus.ACCOUNT_LOCK);
            }
        }

        // 登录操作
        LoginResult loginResult = loginService.login(account, password, callbackUrl);
        switch (loginResult.getLoginStatus()) {
            case SUCCESS:
                loginSuccess(loginResult);
                break;
            case FAIL:
                loginFail(loginResult);
                break;
            case ERROR:
                loginError(loginResult);
                break;
            default:
                break;
        }

        return loginResult;
    }

    // 注销
    public void logout(String account, String token) {
        removeKey(account, token);
    }

    public void logout(String token) {
        removeKey(token);
    }

    public LoginUser getLoginUser(String token) {
        String value = stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY + token);

        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value, LoginUser.class);
        }

        return null;
    }

    private void removeKey(String account, String token) {
        stringRedisTemplate.delete(LOGIN_FAIL_COUNT_KEY + account);
        stringRedisTemplate.delete(LOGIN_USER_KEY + account);
        stringRedisTemplate.delete(LOGIN_USER_KEY + token);
    }

    private void removeKey(String token) {
        stringRedisTemplate.delete(LOGIN_USER_KEY + token);
    }

    private void loginError(LoginResult loginResult) {
        LOG.error("User: {} login error.",
                loginResult.getLoginUser().getAccount());
    }

    private void loginFail(LoginResult loginResult) {
        stringRedisTemplate.opsForValue().increment(
                LOGIN_FAIL_COUNT_KEY + loginResult.getLoginUser(), 30 * 60 * 1000);
    }

    private void loginSuccess(LoginResult loginResult) {
        LoginUser loginUser = loginResult.getLoginUser();

        loginUser.setLoginTime(String.valueOf(new Date().getTime()));

        UserToken userToken = UserToken.getUserToken();

        stringRedisTemplate.opsForValue().set(
                LOGIN_TOKEN_KEY + loginResult.getLoginUser().getAccount(),
                JSON.toJSONString(userToken), 1, TimeUnit.MINUTES);

        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY + userToken.getToken(),
                JSON.toJSONString(loginUser), 30, TimeUnit.MINUTES);

        stringRedisTemplate.delete(LOGIN_FAIL_COUNT_KEY + loginResult.getLoginUser());
    }
}
