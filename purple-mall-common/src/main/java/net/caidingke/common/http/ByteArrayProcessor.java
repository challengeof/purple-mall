package net.caidingke.common.http;

import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;


/**
 * @author bowen
 */
@Slf4j
public class ByteArrayProcessor implements Processor<byte[]> {

    private static final Processor<byte[]> INSTANCE = new ByteArrayProcessor();

    public static Processor<byte[]> getInstance() {
        return INSTANCE;
    }

    protected ByteArrayProcessor() {

    }

    @Override
    public byte[] process(HttpResponse response) {
        int httpStatus = response.getStatusLine().getStatusCode();
        if (httpStatus >= HttpStatus.SC_BAD_REQUEST) {
            throw new RuntimeException(String.format("fetch data error! status : %s", httpStatus));
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try (InputStream inputStream = entity.getContent()) {
                return IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                log.error("Process file from HttpResponse failed.", e);
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
