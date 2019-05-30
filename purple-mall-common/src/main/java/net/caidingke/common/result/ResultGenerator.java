package net.caidingke.common.result;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bowen
 * @create 2015-08-11 10:42
 */
@Slf4j
@SuppressWarnings("unchecked")
public class ResultGenerator {

    private static final String SUCCESS_MSG = "successful";

    private static final String ERROR_MSG = "failed";

    private static final Result<String> OK = new Result<>();

    private static final Result<String> ERROR = new Result<>();

    static {
        ERROR.setMessage(ERROR_MSG).setStatus(Result.ERR);
        OK.setMessage(SUCCESS_MSG).setStatus(Result.OK);
    }

    private static <T> Result<T> generatorResult(int status, T content, String msg) {
        return (Result<T>) Result.newInstance().setStatus(status).setData(content).setMessage(msg);
    }

    private static <T> ResultPage<T> generatorResultPage(
            List<T> content, String msg, int page, int pageSize, long total) {
        return (ResultPage<T>)
                ResultPage.newInstance()
                        .setPage(page)
                        .setRows(content)
                        .setPageSize(pageSize)
                        .setTotal(total);
    }

    public static Result<String> ok() {
        return OK;
    }

    public static <T> Result<T> ok(T content) {
        return ok(SUCCESS_MSG, content);
    }

    public static <T> Result<T> ok(String msg) {
        return generatorResult(Result.OK, null, msg);
    }

    public static <T> ResultPage<T> ok(List<T> content, int page, int pageSize, long total) {
        return ok(SUCCESS_MSG, content, page, pageSize, total);
    }

    public static <T> Result<T> ok(String msg, T content) {
        return generatorResult(Response.OK, content, msg);
    }

    public static <T> ResultPage<T> ok(
            String msg, List<T> content, int page, int pageSize, long total) {
        return generatorResultPage(content, msg, page, pageSize, total);
    }

    public static Result<String> error() {
        return ERROR;
    }

    public static <T> Result<T> error(String msg) {
        return generatorResult(Result.ERR, null, msg);
    }

    public static <T> Result<T> error(int errorCode, String msg) {
        return generatorResult(errorCode, null, msg);
    }
}
