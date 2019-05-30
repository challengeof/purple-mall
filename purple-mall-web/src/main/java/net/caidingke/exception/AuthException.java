package net.caidingke.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author bowen
 */
public class AuthException extends AuthenticationException {

    private static final long serialVersionUID = -8422524616404549216L;

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthException(String msg) {
        super(msg);
    }
}
