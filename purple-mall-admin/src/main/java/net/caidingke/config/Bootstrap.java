package net.caidingke.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bowen
 */
@Configuration
public class Bootstrap implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
    }
}
