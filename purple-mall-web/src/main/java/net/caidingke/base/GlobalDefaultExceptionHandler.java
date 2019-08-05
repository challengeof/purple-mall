package net.caidingke.base;

import static net.caidingke.common.result.ResultGenerator.error;

import net.caidingke.business.exception.BizException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author bowen
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GlobalDefaultExceptionHandler.class);

    private static final Result<String> SERVER_ERROR =
            error(ErrorCode.ERROR.getCode(), ErrorCode.ERROR.getMsg());

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BizException.class)
    public Result handlerBusinessException(BizException exception) {
        LOGGER.error(exception.getMsg(), exception);
        return error(exception.getCode(), exception.getMsg());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        return SERVER_ERROR;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AuthenticationException.class})
    public Result handleAuthenticationException(AuthenticationException e) {
        LOGGER.error(e.getMessage(), e);
        return error(ErrorCode._10001.getCode(), ErrorCode._10001.getMsg());
    }
}
