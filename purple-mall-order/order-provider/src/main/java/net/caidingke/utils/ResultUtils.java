package net.caidingke.utils;

import io.ebean.PagedList;
import net.caidingke.common.result.Result;
import net.caidingke.common.result.ResultGenerator;
import net.caidingke.common.result.ResultPage;

import java.util.List;

/**
 * @author bowen
 */
public final class ResultUtils<T> {

    private ResultUtils() {

    }

    public static <T> ResultPage<T> ok(PagedList<T> pagedList) {
        return ResultGenerator.ok(
                pagedList.getList(),
                pagedList.getPageIndex(),
                pagedList.getPageSize(),
                pagedList.getTotalCount());

    }

    public static Result<String> ok() {
        return ResultGenerator.ok();
    }

    public static <T> Result<T> ok(T content) {
        return ResultGenerator.ok(content);
    }

    public static Result<String> ok(String msg) {
        return ResultGenerator.ok(msg);
    }

    public static <T> ResultPage<T> ok(List<T> content, int page, int pageSize, long total) {
        return ResultGenerator.ok(content, page, pageSize, total);
    }

    public static <T> Result<T> ok(String msg, T content) {
        return ResultGenerator.ok(msg, content);
    }

    public static <T> ResultPage<T> ok(
            String msg, List<T> content, int page, int pageSize, long total) {
        return ResultGenerator.ok(msg, content, page, pageSize, total);
    }

    public static Result<String> error() {
        return ResultGenerator.error();
    }

    public static <T> Result<T> error(String msg) {
        return ResultGenerator.error(msg);
    }

    public static <T> Result<T> error(int errorCode, String msg) {
        return ResultGenerator.error(errorCode, msg);
    }

}
