package net.caidingke.config;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author bowen
 */
@Configuration
public class EbeanConfig {

    private final DataSource dataSource;

    @Autowired
    public EbeanConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public Database ebeanServer() {
        DatabaseConfig config = new DatabaseConfig();
        config.setDataSource(dataSource);
        config.loadFromProperties();
        return DatabaseFactory.create(config);
    }
}
