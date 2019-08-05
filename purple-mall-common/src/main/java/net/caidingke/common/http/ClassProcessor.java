package net.caidingke.common.http;

import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.common.mapper.JsonMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

/**
 * @author bowen
 */
@Slf4j
public class ClassProcessor<T> implements Processor<T> {

    private Class<T> cls = null;

    public ClassProcessor(Class<T> cls) {
        this.cls = cls;
    }

    @Override
    public T process(HttpResponse response) {
        int httpStatus = response.getStatusLine().getStatusCode();
        if (httpStatus != HttpStatus.SC_OK) {
            throw new RuntimeException(String.format("fetch data error status : %s", httpStatus));
        }
        try (InputStream inputStream = response.getEntity().getContent()) {
            String respContent = IOUtils.toString(inputStream, "UTF-8");
            log.info("response : {}", respContent);
            return JsonMapper.fromJson(respContent, this.cls);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
