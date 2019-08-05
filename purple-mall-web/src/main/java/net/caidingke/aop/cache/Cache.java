package net.caidingke.aop.cache;

import net.caidingke.common.Constants;

/**
 * @author bowen
 */
public class Cache {

    public static String getCacheKey(Class<?> cls, Object id) {
        return String.format("%s_%s_%s", Constants.APPLICATION_NAME, cls.getSimpleName(), id);
    }
}
