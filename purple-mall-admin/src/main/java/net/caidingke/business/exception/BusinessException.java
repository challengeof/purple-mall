package net.caidingke.business.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bowen
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6422357307660524879L;

    private int code;

    private String msg;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public BusinessException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }
}
