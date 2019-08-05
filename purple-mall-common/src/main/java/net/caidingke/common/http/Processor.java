package net.caidingke.common.http;

import org.apache.http.HttpResponse;

/**
 * @author bowen
 */
@FunctionalInterface
public interface Processor<T> {

    T process(HttpResponse response);
}
