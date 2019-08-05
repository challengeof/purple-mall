package net.caidingke;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author bowen
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableMethodCache(basePackages = "net.caidingke")
@EnableCreateCacheAnnotation
public class ProductProvider {

    public static void main(String[] args) {
        ApplicationContext context = new SpringApplicationBuilder(ProductProvider.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }
}
