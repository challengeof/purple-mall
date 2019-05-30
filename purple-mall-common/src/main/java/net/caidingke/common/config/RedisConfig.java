package net.caidingke.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Strings;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * @author bowen
 */
@SuppressWarnings("unchecked")
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private final RedisProperties properties;

    @Autowired
    public RedisConfig(RedisProperties redisProperties) {
        this.properties = redisProperties;
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration configuration =
                new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());
        if (!Strings.isNullOrEmpty(properties.getPassword())) {
            configuration.setPassword(RedisPassword.of(properties.getPassword()));
        }
        return configuration;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(properties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMaxTotal(properties.getLettuce().getPool().getMaxActive());
        poolConfig.setMinIdle(properties.getLettuce().getPool().getMinIdle());
        poolConfig.setMaxWaitMillis(properties.getLettuce().getPool().getMaxWait().toMillis());

        return new LettuceConnectionFactory(
                redisStandaloneConfiguration(),
                LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build());
    }

    //    @Bean
    //    public LettuceConnectionFactory lettuceConnectionFactory() {
    //        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
    //        poolConfig.setMaxIdle(properties.getLettuce().getPool().getMaxIdle());
    //        poolConfig.setMaxTotal(properties.getLettuce().getPool().getMaxActive());
    //        poolConfig.setMinIdle(properties.getLettuce().getPool().getMinIdle());
    //        poolConfig.setMaxWaitMillis(properties.getLettuce().getPool().getMaxWait().toMillis());
    //
    //        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
    //        List<String> clusterNodes = properties.getCluster().getNodes();
    //        Set<RedisNode> nodes = new HashSet<>();
    //        for (String ipPort : clusterNodes) {
    //            HostAndPort hostAndPort = HostAndPort.fromString(ipPort);
    //            nodes.add(new RedisNode(hostAndPort.getHost(), hostAndPort.getPort()));
    //        }
    //        redisClusterConfiguration.setPassword(RedisPassword.of(properties.getPassword()));
    //        redisClusterConfiguration.setClusterNodes(nodes);
    //
    //        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
    //                .commandTimeout(properties.getTimeout())
    //                .poolConfig(poolConfig)
    //                .build();
    //
    //        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
    //
    //    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory());
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(lettuceConnectionFactory());
    }

    private void setSerializer(RedisTemplate template) {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enableDefaultTyping(
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
    }

    @Bean
    public RedisCacheManager getRedisCacheManager(RedisConnectionFactory lettuceConnectionFactory) {
        RedisCacheWriter cacheWriter =
                RedisCacheWriter.lockingRedisCacheWriter(lettuceConnectionFactory);
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
        RedisSerializationContext.SerializationPair<Object> pair =
                RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
        RedisCacheConfiguration cacheConfig =
                RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        return new RedisCacheManager(cacheWriter, cacheConfig);
    }
}
