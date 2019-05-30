package net.caidingke.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author bowen
 */
@ConfigurationProperties(prefix = "purple")
@Configuration
public class PurpleProperties {

    private final Auth auth = new Auth();

    public static class Auth {

        private String tokenSecret;
        private long tokenValidityInSeconds;
        private String header;
        private String routePath;

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenValidityInSeconds() {
            return tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
            this.tokenValidityInSeconds = tokenValidityInSeconds;
        }

        public String getHeader() {
            return header;
        }

        public void setHeader(String header) {
            this.header = header;
        }

        public String getRoutePath() {
            return routePath;
        }

        public void setRoutePath(String routePath) {
            this.routePath = routePath;
        }
    }


    public Auth getAuth() {
        return auth;
    }

}
