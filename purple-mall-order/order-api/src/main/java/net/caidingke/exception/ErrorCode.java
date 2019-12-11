package net.caidingke.exception;

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
    _30001(30001, "分类名称已存在"),
    _30002(30002, "分类不可以环形依赖")
    ;
    private final int code;
    private final String msg;
}
