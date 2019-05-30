package net.caidingke.common.mapper;

/**
 * @author bowen
 */
@FunctionalInterface
public interface TypeConverter<S, T> {
    /**
     * Convert a source object to a target object.
     *
     * @param source the source object
     *
     * @return the target object converted from the source object
     */
    T convert(S source);
}
