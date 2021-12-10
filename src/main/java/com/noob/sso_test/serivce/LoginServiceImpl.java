package com.noob.sso_test.serivce;

import com.alibaba.fastjson.JSON;
import com.noob.sso_test.model.LoginResult;
import com.noob.sso_test.model.LoginStatus;
import com.noob.sso_test.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    private static final RestTemplate restTemplate = new RestTemplate();

    @Override
    public LoginResult login(String account, String password, String callbackUrl) {
        LoginResult loginResult;
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            // 设置请求媒体数据类型
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            // 设置返回媒体数据类型
            httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());

            HttpEntity<String> formEntity = new HttpEntity<>(
                    JSON.toJSONString(new LoginUser(account, password)), httpHeaders);

            loginResult = restTemplate.postForObject(callbackUrl, formEntity, LoginResult.class);
        } catch (Exception e) {
            LOG.error("Login valid callback error: ", e);
            return new LoginResult(LoginStatus.CALLBACK_ERROR);
        }

        return loginResult == null ? new LoginResult(LoginStatus.ERROR) : loginResult;
    }
}
