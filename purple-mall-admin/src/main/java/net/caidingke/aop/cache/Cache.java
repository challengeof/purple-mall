package net.caidingke.aop.cache;

import lombok.extern.slf4j.Slf4j;
import net.caidingke.common.Constants;

/**
 * @author bowen
 */
@Slf4j
public class Cache {

    public static String getCacheKey(Class<?> cls, Object id) {
        return String.format("%s_%s_%s", Constants.APPLICATION_NAME, cls.getSimpleName(), id);
    }
}
