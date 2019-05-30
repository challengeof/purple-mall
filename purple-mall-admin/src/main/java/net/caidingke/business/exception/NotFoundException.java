package net.caidingke.business.exception;

/**
 * optional orElseThrow exception
 *
 * @author bowen
 */
public class NotFoundException extends BusinessException {

    private static final long serialVersionUID = 8781855461307205973L;

    public NotFoundException() {
        super(ErrorCode._10004);
    }
}
