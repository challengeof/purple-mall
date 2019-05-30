package net.caidingke.business.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author bowen.
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * Internal Server Error
     */
    ERROR(-1, "服务器异常"),
    _10001(10001, "未认证"),
    _10002(10002, "未激活"),
    _10003(10003, "用户名已注册"),
    _10004(10004, "找不到"),
    _10005(10005, "原密码不匹配"),
    ;
    private final int code;
    private final String msg;
}
