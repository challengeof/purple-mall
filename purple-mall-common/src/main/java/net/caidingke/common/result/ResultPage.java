package net.caidingke.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author bowen
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResultPage<T> extends Response{

    private static final long serialVersionUID = 6769973984136886424L;
    public List<T> rows = new ArrayList<>();

    public long total;

    public int page = 0;

    public int pageSize = 100;

    public Object extra = null;

    public ResultPage setRows(List<T> rows) {
        this.rows = rows;
        return this;
    }

    ResultPage setTotal(long total) {
        this.total = total;
        return this;
    }

    ResultPage setPage(int page) {
        this.page = page;
        return this;
    }

    ResultPage setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public ResultPage setExtra(Object extra) {
        this.extra = extra;
        return this;
    }

    static <T> ResultPage<T> newInstance() {
        return new ResultPage<>();
    }
}
