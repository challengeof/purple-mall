package net.caidingke.base;

import io.ebean.PagedList;
import java.util.List;
import net.caidingke.business.exception.BizException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.common.Constants;
import net.caidingke.common.result.Result;
import net.caidingke.common.result.ResultGenerator;
import net.caidingke.common.result.ResultPage;

/**
 * @author bowen.
 */
public class BasicController {

    protected static Result<String> ok() {
        return ResultGenerator.ok();
    }

    protected static <T> Result<T> ok(T content) {
        return ResultGenerator.ok(content);
    }

    protected static Result<String> ok(String msg) {
        return ResultGenerator.ok(msg);
    }

    protected static <T> ResultPage<T> ok(List<T> content, int page, int pageSize, long total) {
        return ResultGenerator.ok(content, page, pageSize, total);
    }

    protected static <T> Result<T> ok(String msg, T content) {
        return ResultGenerator.ok(msg, content);
    }

    protected static <T> ResultPage<T> ok(
            String msg, List<T> content, int page, int pageSize, long total) {
        return ResultGenerator.ok(msg, content, page, pageSize, total);
    }

    protected static <T> Result<ResultPage<T>> ok(PagedList<T> pagedList) {
        return ok(
                ResultGenerator.ok(
                        pagedList.getList(),
                        pagedList.getPageIndex(),
                        pagedList.getPageSize(),
                        pagedList.getTotalCount()));
    }

    protected static Result<String> error() {
        return ResultGenerator.error();
    }

    protected static <T> Result<T> error(String msg) {
        return ResultGenerator.error(msg);
    }

    protected static <T> Result<T> error(int errorCode, String msg) {
        return ResultGenerator.error(errorCode, msg);
    }

    protected static Result<String> errorThrow() {
        throw new BizException(ErrorCode.ERROR);
    }

    protected static <T> Result<T> errorThrow(String msg) {
        throw new BizException(msg, Constants.ERROR_CODE);
    }

    protected static <T> Result<T> errorThrow(int errorCode, String msg) {
        throw new BizException(msg, errorCode);
    }

    protected static <T> Result<T> errorThrow(ErrorCode errorCode) {
        throw new BizException(errorCode.getMsg(), errorCode.getCode());
    }
}
