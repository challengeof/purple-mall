package net.caidingke.common.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.Getter;

/**
 * @author bowen
 */
@Getter
public class Response implements Serializable {

    private static final long serialVersionUID = -7443567025391054307L;
    protected static final int OK = 200;
    protected static final int ERR = 500;

    private int status;

    private String message;

    {
        status = OK;
    }

    public Response setStatus(int status) {
        this.status = status;
        return this;
    }

    public Response setMessage(String message) {
        this.message = message;
        return this;
    }

    @JsonIgnore
    public boolean isOk() {
        return this.status == OK;
    }
}
