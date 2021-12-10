package com.noob.sso_test.model;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomAdaptor;

import java.util.Calendar;
import java.util.Random;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
@Data
public class UserToken {

    private static final Random random = RandomAdaptor
            .createAdaptor(new JDKRandomGenerator());

    private String token;

    private String expireTime;

    public UserToken() {
    }

    public UserToken(String token, String expireTime) {
        this.token = token;
        this.expireTime = expireTime;

    }

    public static UserToken getUserToken() {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 30);

        return new UserToken(DigestUtils.md5Hex(String.valueOf(random.nextInt(32))),
                String.valueOf(nowTime.getTimeInMillis()));
    }

    private String generateToken() {
        return DigestUtils.md5Hex(String.valueOf(random.nextInt(32)));
    }
}
