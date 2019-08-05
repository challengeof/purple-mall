package net.caidingke.common.http;

import org.apache.http.HttpResponse;

/**
 * @author bowen
 */
public class DefaultProcessor implements Processor {

    private static final DefaultProcessor INSTANCE = new DefaultProcessor();

    public static DefaultProcessor getInstance() {
        return INSTANCE;
    }

    private DefaultProcessor() {

    }

    @Override
    public HttpResponse process(HttpResponse response) {
        return response;
    }
}
