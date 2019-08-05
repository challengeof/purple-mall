package net.caidingke.common.http;

import com.fasterxml.jackson.databind.JsonNode;
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
public class JsonProcessor implements Processor {

    private static final JsonProcessor INSTANCE = new JsonProcessor();

    public static Processor<JsonNode> getInstance() {
        return INSTANCE;
    }

    @Override
    public JsonNode process(HttpResponse response) {
        int httpStatus = response.getStatusLine().getStatusCode();
        if (httpStatus >= HttpStatus.SC_BAD_REQUEST) {
            throw new RuntimeException(String.format("fetch data error! status : %s", httpStatus));
        }
        try (InputStream inputStream = response.getEntity().getContent()) {
            String respContent = IOUtils.toString(inputStream, "UTF-8");
            log.info("response : " + respContent);
            return JsonMapper.fromJson(respContent, JsonNode.class);
        } catch (Exception e) {
            throw new RuntimeException("parse response result error! ", e);
        }
    }
}
