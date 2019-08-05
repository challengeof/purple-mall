package net.caidingke.config;

import com.google.common.net.HostAndPort;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * @author bowen
 */
@Builder
public class EsClientBuilder {

    private int connectTimeoutMillis;
    private int socketTimeoutMillis;
    private int connectionRequestTimeoutMillis;
    private int maxConnectPerRoute;
    private int maxConnectTotal;
    private List<String> nodes;
    private String clusterName;


    public Client create() {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        List<TransportAddress> transportAddresses = new ArrayList<>();
        for (String ipPort : nodes) {
            HostAndPort hostAndPort = HostAndPort.fromString(ipPort);
            transportAddresses
                    .add(new TransportAddress(
                            new InetSocketAddress(hostAndPort.getHost(), hostAndPort.getPort())));
        }

        return new PreBuiltTransportClient(settings).addTransportAddresses(
                transportAddresses.toArray(
                        new TransportAddress[0]));
    }
}
