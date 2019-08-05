package net.caidingke.config;

import com.esotericsoftware.kryo.serializers.JavaSerializer;
import de.javakaffee.kryoserializers.SynchronizedCollectionsSerializer;
import org.apache.dubbo.common.serialize.kryo.utils.KryoUtils;
import org.apache.dubbo.common.serialize.support.SerializableClassRegistry;
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
