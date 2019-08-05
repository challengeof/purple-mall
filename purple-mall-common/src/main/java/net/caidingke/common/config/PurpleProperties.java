package net.caidingke.common.config;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author bowen
 */
@ConfigurationProperties(prefix = "purple")
@Configuration
public class PurpleProperties {

    @Getter
    private final Auth auth = new Auth();
    @Getter
    private final Es es = new Es();

    @Getter
    @Setter
    public static class Auth {

        private String tokenSecret;
        private long tokenValidityInSeconds;
        private String header;
        private String routePath;

    }

    @Getter
    @Setter
    public static class Es {

        private List<String> nodes;
        private String schema;
        private Integer maxConnectTotal;
        private Integer maxConnectPerRoute;
        private Integer connectionRequestTimeoutMillis;
        private Integer socketTimeoutMillis;
        private Integer connectTimeoutMillis;
        private String clusterName;
    }

}
