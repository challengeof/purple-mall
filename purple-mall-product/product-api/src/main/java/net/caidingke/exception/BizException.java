package net.caidingke.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bowen
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 6422357307660524879L;

    private int code;

    private String msg;

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BizException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}
