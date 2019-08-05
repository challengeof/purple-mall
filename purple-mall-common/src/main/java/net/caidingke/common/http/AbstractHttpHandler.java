package net.caidingke.common.http;

import com.google.common.base.Strings;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 * @author bowen
 */
@Slf4j
public abstract class AbstractHttpHandler implements HttpHandler {

    private String body = null;
    private URI uri;
    private List<NameValuePair> params = new ArrayList<>();
    private List<NameValuePair> urlParams = new ArrayList<>();
    private List<Header> headers = new ArrayList<>();
    private HttpResponse response = null;
    private File file = null;
    private HttpEntity httpEntity;
    private ContentType contentType;
    private HttpContext httpContext;
    private HostnameVerifier hostnameVerifier;
    private SSLContext sslContext;
    private HttpHost proxy;
    private int retryCount = 3;
    private RetryConditionWithResponse retryConditionWithResponse = IGNORE_RETRY_WITH_ANY_RESPONSE;
    private static RetryConditionWithResponse IGNORE_RETRY_WITH_ANY_RESPONSE = response -> false;
    private boolean usePool = false;
    private int connectTimeout = 1000;
    private int socketTimeout = 1000;
    private CredentialsProvider credentialsProvider;

    @Override
    public HttpHandler useSSL(SSLContext sslContext, HostnameVerifier hostnameVerifier) {
        this.sslContext = sslContext;
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    @Override
    public HttpHandler useCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
        return this;
    }

    @Override
    public HttpHandler addParam(String name, String value) {
        this.params.add(new BasicNameValuePair(name, value));
        return this;
    }

    @Override
    public HttpHandler addParams(List<NameValuePair> params) {
        this.params.addAll(params);
        return this;
    }

    @Override
    public HttpHandler addHeader(Header header) {
        this.headers.add(header);
        return this;
    }

    @Override
    public List<NameValuePair> getParams() {
        return params;
    }

    @Override
    public List<NameValuePair> getUrlParams() {
        return urlParams;
    }

    @Override
    public HttpHandler buildPostBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public <T> T process(Processor<T> processor) {
        try {
            return processor.process(response);
        } finally {
            if (!(processor instanceof DefaultProcessor)) {
                EntityUtils.consumeQuietly(response.getEntity());
            }
        }
    }

    @Override
    public HttpHandler post() {
        try {
            HttpPost request = postForm();
            executeWithRetry(request);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpHandler put() {
        try {
            HttpPut request = putForm();
            executeWithRetry(request);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpHandler postAsync() {
        return null;
    }

    @Override
    public HttpHandler get() {
        try {
            HttpGet httpGet = getForm();
            executeWithRetry(httpGet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public HttpHandler getAsync() {
        return null;
    }

    @Override
    public void file(File file) {
        this.file = file;
    }

    @Override
    public HttpHandler addUrlParams(Object... params) {
        if (params.length % 2 != 0) {
            throw new RuntimeException("parameters not match!");
        }
        for (int i = 0; i < params.length; i = i + 2) {
            this.urlParams
                    .add(new BasicNameValuePair(params[i].toString(), params[i + 1].toString()));
        }
        return this;
    }

    @Override
    public HttpHandler retry(int retryCount) {
        if (retryCount > -1) {
            this.retryCount = retryCount;
        }
        return this;
    }

    @Override
    public HttpHandler retry(int retryCount,
            RetryConditionWithResponse retryConditionWithResponse) {
        if (retryCount > -1) {
            this.retryCount = retryCount;
            this.retryConditionWithResponse = retryConditionWithResponse;
        }
        return this;
    }

    @Override
    public HttpHandler usePool(boolean usePool) {
        this.usePool = usePool;
        return this;
    }

    @Override
    public HttpHandler setProxy(HttpHost proxy) {
        this.proxy = proxy;
        return this;
    }

    @Override
    public HttpHandler setEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
        return this;
    }

    @Override
    public HttpHandler setParams(Map<String, Object> params) {
        if (MapUtils.isNotEmpty(params)) {
            this.params.addAll(params.keySet().stream()
                    .map(key -> new BasicNameValuePair(key, String.valueOf(params.get(key))))
                    .collect(Collectors
                            .toList()));
        }
        return this;
    }

    @Override
    public HttpHandler setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }


    /**
     * Determines the timeout in milliseconds until a connection is established. A timeout value of
     * zero is interpreted as an infinite timeout.
     * <p/>
     * A timeout value of zero is interpreted as an infinite timeout. A negative value is
     * interpreted as undefined (system default).
     * <p/>
     */
    @Override
    public HttpHandler setConnectTimeout(int timeout) {
        this.connectTimeout = timeout;
        return this;
    }

    /**
     * Defines the socket timeout (<id>SO_TIMEOUT</id>) in milliseconds, which is the timeout for
     * waiting for data  or, put differently, a maximum period inactivity between two consecutive
     * data packets).
     * <p/>
     * A timeout value of zero is interpreted as an infinite timeout. A negative value is
     * interpreted as undefined (system default).
     * <p/>
     * Default: <id>-1</id>
     */
    @Override
    public HttpHandler setSocketTimeout(int timeout) {
        this.socketTimeout = timeout;
        return this;
    }

    @Override
    public HttpHandler setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
        return this;
    }

    protected RequestConfig.Builder getDefaultRequestConfigBuilder() {
        return RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout).setConnectionRequestTimeout(1000);
    }

    abstract protected URI buildUri(List<NameValuePair> urlParams) throws URISyntaxException;

    private HttpGet getForm() throws Exception {
        HttpGet httpGet = new HttpGet();
        if (CollectionUtils.isNotEmpty(this.params)) {
            this.urlParams.addAll(this.params);
        }
        this.uri = buildUri(this.urlParams);
        httpGet.setURI(this.uri);
        return httpGet;
    }

    /**
     * 同时有params, body和file时，只能发送一种参数，优先级是file < body < params
     */
    private HttpPost postForm() throws Exception {
        this.uri = buildUri(this.urlParams);
        HttpPost httpPost = new HttpPost(this.uri);
        if (httpEntity != null) {
            httpPost.setEntity(httpEntity);
        } else if (CollectionUtils.isNotEmpty(this.params)) {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(this.params,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(encodedFormEntity);
        } else if (!Strings.isNullOrEmpty(this.body)) {
            StringEntity entity = new StringEntity(this.body);
            if (contentType == null) {
                contentType = ContentType.APPLICATION_FORM_URLENCODED;
            }
            entity.setContentType(contentType.getMimeType());
            httpPost.setEntity(entity);
        } else if (this.file != null) {
            httpPost.setEntity(buildFileEntity());
        }

        return httpPost;
    }

    private FileEntity buildFileEntity() {
        FileEntity entity;
        if (contentType == null) {
            entity = new FileEntity(this.file);
        } else {
            entity = new FileEntity(this.file, contentType);
        }
        return entity;
    }

    private HttpPut putForm() throws Exception {
        this.uri = buildUri(this.urlParams);
        HttpPut httpPut = new HttpPut(this.uri);
        if (httpEntity != null) {
            httpPut.setEntity(httpEntity);
        } else if (this.params != null && !this.params.isEmpty()) {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(this.params,
                    Charset.forName("UTF-8"));
            httpPut.setEntity(encodedFormEntity);
        } else if (this.body != null) {
            StringEntity entity = new StringEntity(this.body);
            if (contentType == null) {
                contentType = ContentType.APPLICATION_FORM_URLENCODED;
            }
            entity.setContentType(contentType.getMimeType());
            httpPut.setEntity(entity);
        } else if (this.file != null) {
            httpPut.setEntity(buildFileEntity());
        }

        return httpPut;
    }

    private void executeWithRetry(HttpRequestBase requestBase) throws Exception {
        executeWithRetry(requestBase, 0);
    }

    /**
     * retry after execute failed
     */
    private void executeWithRetry(HttpRequestBase requestBase, int alreadyRetryTimes)
            throws Exception {
        try {
            HttpClientBuilder httpClientBuilder;
            if (usePool) {
                httpClientBuilder = getHttpClientBuilderWithPooling();
            } else {
                httpClientBuilder = getHttpClientBuilder();
            }
            if (credentialsProvider != null) {
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            if (proxy != null) {
                httpClientBuilder.setProxy(proxy);
            }
            HttpClient httpClient = httpClientBuilder.build();
            requestBase.setHeaders(headers.toArray(new Header[0]));
            if (contentType != null) {
                requestBase.setHeader(HttpHeaders.CONTENT_TYPE, contentType.getMimeType());
            }
            this.response = httpClient.execute(requestBase, httpContext);
            if (this.retryConditionWithResponse != null) {
                if (this.retryConditionWithResponse.condition(this.response)) {
                    throw new RuntimeException();
                }
            }
            if (this.response != null) {
                EntityUtils.consumeQuietly(this.response.getEntity());
            }
        } catch (Exception e) {
            if (alreadyRetryTimes < retryCount) {
                log.warn("execute error with url {} {} times, and i will try again",
                        requestBase.getURI(), alreadyRetryTimes);
                Thread.sleep(100);
                requestBase.reset();
                executeWithRetry(requestBase, alreadyRetryTimes + 1);
            } else {
                String causeMsg = "";
                try {
                    causeMsg = IOUtils.toString(this.response.getEntity().getContent(),
                            Charset.defaultCharset());
                    //release conn when error
                    EntityUtils.consume(this.response.getEntity());
                } catch (Exception ine) {
                    //NOOP
                }
                throw new RuntimeException(causeMsg, e);
            }
        }
    }

    protected HttpClientBuilder getHttpClientBuilder() {
        HttpClientBuilder builder;
        if (sslContext != null) {
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext, hostnameVerifier);
            builder = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory);
        } else {
            builder = HttpClientBuilder.create();
        }
        RequestConfig defaultRequestConfig = getDefaultRequestConfigBuilder().build();
        return builder.setDefaultRequestConfig(defaultRequestConfig);
    }

    protected final static Map<String, PoolingHttpClientConnectionManager> cachedHttpsConnectionManagerMap = new ConcurrentHashMap<>();
    protected static PoolingHttpClientConnectionManager httpConnectionManager;


    protected HttpClientBuilder getHttpClientBuilderWithPooling() {
        HttpClientBuilder builder = HttpClientBuilder.create();
        if (sslContext == null) {
            if (httpConnectionManager == null) {
                httpConnectionManager = new PoolingHttpClientConnectionManager();
                httpConnectionManager.setMaxTotal(65);
                httpConnectionManager.setDefaultMaxPerRoute(10);
            }
            builder.setConnectionManager(httpConnectionManager);
        } else {
            String cacheKey = sslContext.hashCode() + "|" + (hostnameVerifier == null ? 0
                    : hostnameVerifier.hashCode());
            if (cachedHttpsConnectionManagerMap.get(cacheKey) == null) {
                SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                        sslContext, hostnameVerifier);
                Registry<ConnectionSocketFactory> registry = RegistryBuilder
                        .<ConnectionSocketFactory>create()
                        .register("https", sslConnectionSocketFactory).build();
                PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                        registry);
                connectionManager.setMaxTotal(65);
                connectionManager.setDefaultMaxPerRoute(5);
                cachedHttpsConnectionManagerMap.put(cacheKey, connectionManager);
            }
            builder.setConnectionManager(cachedHttpsConnectionManagerMap.get(cacheKey));
        }
        RequestConfig defaultRequestConfig = getDefaultRequestConfigBuilder().build();
        builder.setDefaultRequestConfig(defaultRequestConfig);
        return builder;
    }


    /**
     * retry condition with response
     */
    @FunctionalInterface
    public interface RetryConditionWithResponse {

        /**
         * retry condition
         */
        boolean condition(HttpResponse response);
    }

}
