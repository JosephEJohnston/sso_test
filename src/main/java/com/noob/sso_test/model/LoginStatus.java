package com.noob.sso_test.model;

/**
 * @author Zeyuan Wang[wangzeyuan@nowcoder.com]
 * @since 2021/12/10
 */
public enum LoginStatus {
    SUCCESS(1, "登录成功"),
    ING(0, "登录中"),
    FAIL(-1, "登录失败"),
    ERROR(-2, "登录异常"),
    CALLBACK_ERROR(-3, "登录回调异常"),
    ACCOUNT_LOCK(-4, "账户被锁定"),
    EXPIRE(-5, "登录用户已过期"),
    ;

    private final int code;
    private final String message;

    LoginStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
