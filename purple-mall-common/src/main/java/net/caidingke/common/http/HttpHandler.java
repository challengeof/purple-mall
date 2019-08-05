package net.caidingke.common.http;

import java.io.File;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HttpContext;

/**
 * @author bowen
 */
public interface HttpHandler {

    /**
     * user ssl
     */
    HttpHandler useSSL(SSLContext sslContext, HostnameVerifier hostnameVerifier);

    /**
     * use credentials
     */
    HttpHandler useCredentialsProvider(CredentialsProvider credentialsProvider);

    HttpHandler addParam(String name, String value);

    HttpHandler addParams(List<NameValuePair> params);

    HttpHandler addHeader(Header header);

    List<NameValuePair> getParams();

    List<NameValuePair> getUrlParams();

    HttpHandler buildPostBody(String body);

    <T> T process(Processor<T> processor);

    HttpHandler post();

    HttpHandler put();

    HttpHandler postAsync();

    HttpHandler get();

    HttpHandler getAsync();

    void file(File file);

    HttpHandler addUrlParams(Object... params);

    HttpHandler retry(int count);

    HttpHandler retry(int count,
            AbstractHttpHandler.RetryConditionWithResponse retryConditionWithResponse);

    HttpHandler usePool(boolean usePool);

    HttpHandler setProxy(HttpHost proxy);

    HttpHandler setEntity(HttpEntity httpEntity);

    HttpHandler setParams(Map<String, Object> params);

    HttpHandler setContentType(ContentType contentType);

    HttpHandler setConnectTimeout(int timeout);

    HttpHandler setSocketTimeout(int timeout);

    HttpHandler setHttpContext(HttpContext httpContext);
}
