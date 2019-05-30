package net.caidingke.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * @author bowen
 * @create 2015-08-11 10:23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class Result<T> extends Response {

    private static final long serialVersionUID = 7133152428901216030L;
    private T data;

    static <T> Result<T> newInstance() {
        return new Result<>();
    }

    @Override
    public Result setStatus(int status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public Result setMessage(String message) {
        super.setMessage(message);
        return this;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }
}
