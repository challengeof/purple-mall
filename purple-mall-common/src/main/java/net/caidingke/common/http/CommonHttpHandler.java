package net.caidingke.common.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.Builder;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;

/**
 * @author bowen
 */
@Builder
public class CommonHttpHandler extends AbstractHttpHandler {

    private String mainUrl;

    public CommonHttpHandler(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    @Override
    protected URI buildUri(List<NameValuePair> urlParams) throws URISyntaxException {
        if (!mainUrl.contains("://")) {
            mainUrl = "http://" + mainUrl;
        }
        URIBuilder builder = new URIBuilder(mainUrl);
        builder.addParameters(urlParams);
        return builder.build();
    }
}
