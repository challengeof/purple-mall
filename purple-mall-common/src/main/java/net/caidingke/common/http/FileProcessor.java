package net.caidingke.common.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

/**
 * @author bowen
 */
@Slf4j
public class FileProcessor implements Processor<File> {

    private String path;

    public FileProcessor(String path) {
        this.path = path;
    }

    @Override
    public File process(HttpResponse response) {
        int httpStatus = response.getStatusLine().getStatusCode();
        if (httpStatus >= HttpStatus.SC_BAD_REQUEST) {
            throw new RuntimeException(String.format("fetch data error! status : %s", httpStatus));
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try (InputStream inputStream = entity
                    .getContent(); FileOutputStream output = new FileOutputStream(path)) {

                int bufferSize = 1024;
                byte[] buffer = new byte[bufferSize];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }

            } catch (IOException e) {
                log.error("Process file from HttpResponse failed.", e);
                throw new RuntimeException(e);
            }
        }
        return new File(path);
    }
}
