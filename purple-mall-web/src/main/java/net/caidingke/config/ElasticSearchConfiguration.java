package net.caidingke.config;

import net.caidingke.common.config.PurpleProperties;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bowen
 */
@Configuration
public class ElasticSearchConfiguration {

    private final PurpleProperties purpleProperties;

    @Autowired
    public ElasticSearchConfiguration(PurpleProperties purpleProperties) {
        this.purpleProperties = purpleProperties;
    }

    @Bean(destroyMethod = "close")
    public Client client() {

        PurpleProperties.Es es = purpleProperties.getEs();
        if (CollectionUtils.isEmpty(es.getNodes())) {
            throw new RuntimeException("es cluster nodes is must");
        }
        return EsClientBuilder.builder()
                .maxConnectTotal(es.getMaxConnectTotal())
                .maxConnectPerRoute(es.getMaxConnectPerRoute())
                .connectionRequestTimeoutMillis(es.getConnectionRequestTimeoutMillis())
                .socketTimeoutMillis(es.getSocketTimeoutMillis())
                .connectTimeoutMillis(es.getConnectTimeoutMillis())
                .nodes(es.getNodes())
                .clusterName(es.getClusterName())
                .build()
                .create();
    }

}