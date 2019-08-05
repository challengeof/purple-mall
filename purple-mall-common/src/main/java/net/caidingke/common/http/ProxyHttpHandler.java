package net.caidingke.common.http;

import com.google.common.base.Strings;
import org.apache.http.HttpHost;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author bowen
 */
public class ProxyHttpHandler extends CommonHttpHandler {

    private String proxyHost;
    private int proxyPort;
    private String scheme;

    private CredentialsProvider credentialsProvider;

    @Override
    protected HttpClientBuilder getHttpClientBuilder() {
        HttpClientBuilder builder = super.getHttpClientBuilder();
        addProxyToHttpClientBuilder(builder);
        return builder;
    }

    @Override
    protected HttpClientBuilder getHttpClientBuilderWithPooling() {
        HttpClientBuilder builder = super.getHttpClientBuilderWithPooling();
        addProxyToHttpClientBuilder(builder);
        return builder;
    }

    private void addProxyToHttpClientBuilder(HttpClientBuilder builder) {
        if (credentialsProvider != null) {
            builder.setDefaultCredentialsProvider(credentialsProvider);
        }

        RequestConfig.Builder requestConfigBuilder = getDefaultRequestConfigBuilder();
        HttpHost httpHost;
        if (Strings.isNullOrEmpty(scheme)) {
            httpHost = new HttpHost(proxyHost, proxyPort);
        } else {
            httpHost = new HttpHost(proxyHost, proxyPort, scheme);
        }
        requestConfigBuilder.setProxy(httpHost);
        builder.setDefaultRequestConfig(requestConfigBuilder.build());
    }

    public ProxyHttpHandler(String mainUrl, String proxyHost, int proxyPort) {
        super(mainUrl);
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
    }

    public ProxyHttpHandler(String mainUrl, String proxyHost, int proxyPort,
            CredentialsProvider credentialsProvider) {
        super(mainUrl);
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.credentialsProvider = credentialsProvider;
    }

    public ProxyHttpHandler(String mainUrl, String proxyHost, int proxyPort, String scheme) {
        super(mainUrl);
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.scheme = scheme;
    }

    public ProxyHttpHandler(String mainUrl, String proxyHost, int proxyPort, String scheme,
            CredentialsProvider credentialsProvider) {
        super(mainUrl);
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.scheme = scheme;
        this.credentialsProvider = credentialsProvider;
    }

}
